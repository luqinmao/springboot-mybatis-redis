package com.youjiang.springboot.utils.page;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

//从签名里可以看出，要拦截的目标类型是StatementHandler（注意：type只能配置成接口类型），
//拦截的方法是名称为prepare参数为Connection类型的方法。
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class QueryInterceptor implements Interceptor, Serializable {

    private static final long serialVersionUID = 1L;

    protected Dialect DIALECT = new MySqlDialect();

    /**
     * 对ID做正则匹配，只对query开头的方法进行处理
     */
    protected String _SQL_PATTERN = ".*page.*";

    /**
     * 真正实现拦截器业务逻辑的方法
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // StatementHandler的默认实现类是RoutingStatementHandler，因此拦截的实际对象是它。
        // RoutingStatementHandler的主要功能是分发，它根据配置Statement类型创建真正执行数据库操作的StatementHandler，
        // 并将其保存到delegate属性里。
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) getField(RoutingStatementHandler.class, "delegate")
                    .get(statementHandler);
            MappedStatement mappedStatement = (MappedStatement) getField(BaseStatementHandler.class, "mappedStatement")
                    .get(delegate);
            // 重新需要分页的SQL
            if (mappedStatement.getId().matches(_SQL_PATTERN)) {
                BoundSql boundSql = delegate.getBoundSql();
                String originalSql = boundSql.getSql();
                if (StringUtils.isBlank(originalSql)) {
                    return invocation.proceed();
                }
                try {
                    Map<?, ?> parameterObject = (Map<?, ?>) boundSql.getParameterObject();
                    // 查询参数--上下文传参
                    Pager<?> pager = null;

                    pager = getPager(parameterObject.get(Pager.PAGER));
                    if (pager == null) {
                        Set<?> set = parameterObject.keySet();
                        Iterator<?> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            Object object = parameterObject.get(iterator.next());
                            if (object instanceof Pager) {
                                pager = (Pager<?>) object;
                                break;
                            }
                        }
                    }

                    if (pager != null) {
                        Connection connection = (Connection) invocation.getArgs()[0];
                        if (pager.getCurrentPage() <= 1) {
                            setPageParameter(originalSql, connection, mappedStatement, boundSql, pager);
                        }
                        // 处理排序
                        originalSql = generateOrderSql(originalSql, pager, DIALECT);
                        // 处理分页
                        String pageSql = generatePageSql(originalSql, pager, DIALECT);
                        // 赋值，将新的SQL覆盖原SQL
                        setFieldValue(boundSql, "sql", pageSql);
                    }
                } catch (Exception e) {
                }
            }
        }
        // 交给下一个拦截器
        return invocation.proceed();
    }

    /**
     * 获取总记录数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql,
            Pager<?> page) {
        String sqlCapital = sql.toUpperCase();
        sql = sql.substring(sqlCapital.indexOf("FROM") + 4);
        // 记录总记录数
        String countSql = "SELECT count(0) FROM " + sql;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
            // int totalPage = totalCount / page.getPageSize() + ((totalCount %
            // page.getPageSize() == 0) ? 0 : 1);
            // page.setPageCount(totalPage);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 代入参数值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /** 这几个私有方法可以单独提取出去，在SQL处理类里面，这里放在一个类里是为了方便查看 */

    private Field getField(Class<?> clazz, String name) {
        Field field = ReflectionUtils.findField(clazz, name);
        field.setAccessible(true);
        return field;
    }

    private Pager<?> getPager(Object object) {
        if (object instanceof Pager) {
            return (Pager<?>) object;
        }
        return null;
    }

    private String generateOrderSql(String sql, Pager<?> pager, Dialect dialect) {
        if (StringUtils.isBlank(pager.getOrderColumns())) {
            return sql;
        }
        return dialect.getOrderString(sql, pager.getOrderColumns(), pager.getOrderType());
    }

    private String generatePageSql(String sql, Pager<?> pager, Dialect dialect) {
        int pageSize = pager.getPageSize();
        int index = (pager.getCurrentPage() - 1) * pageSize;
        int start = index < 0 ? 0 : index;
        return dialect.getLimitString(sql, start, pageSize);
    }

    private void setFieldValue(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 这里不可能抛出异常
            e.printStackTrace();
        }
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            // 目标属于StatementHandler时才包装该类
            return Plugin.wrap(target, this);
        } else {
            // 否则，直接返回目标类，减少代理次数
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {}
}
