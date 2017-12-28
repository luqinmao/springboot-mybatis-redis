package com.youjiang.springboot.utils.page;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Pager<E> implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2933250704062589443L;
    
    public static final String PAGER = "pager";
    /**
     * 每页最大记录数限制
     */
    public static final Integer MAX_PAGE_SIZE = 500;

    /**
     * 当前页码
     */
    private Integer currentPage = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;

    /**
     * 总记录数
     */
    private Integer totalCount = 0;

    /**
     * 总页数
     */
    private Integer pageCount = 0;

    /**
     * 数据List
     */
    private List<E> list;

    /**
     * 排序方式，默认为desc
     */
    protected OrderType orderType;
    /**
     * 排序字段
     */
    protected String orderColumns;

    public Pager() {

    }

    /**
     * @param currentPage 当前页码
     * @param pageSize 每页记录数
     */
    public Pager(Integer currentPage, Integer pageSize) {
        if (currentPage != null) {
            this.currentPage = currentPage;
        }
        if (pageSize != null) {
            this.pageSize = pageSize;
        }
    }

    /**
     * @param currentPage 当前页码
     * @param pageSize 每页记录数
     * @param orderColumns 排序字段
     * @param orderType 排序方式
     */
    public Pager(Integer currentPage, Integer pageSize, String orderColumns, OrderType orderType) {
        if (currentPage != null) {
            this.currentPage = currentPage;
        }

        if (pageSize != null) {
            this.pageSize = pageSize;
        }
        if (StringUtils.isNotBlank(orderColumns)) {
            this.orderColumns = orderColumns;
        }

        this.orderType = orderType;
    }

    public boolean hasNext() {
        if (this.pageCount > this.currentPage) {
            return true;
        }
        return false;
    }

    public boolean hasForward() {
        if (this.currentPage <= 1) {
            return false;
        }
        return true;
    }

    /**
     * 当前页码
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 当前页码
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage < 1 ? 0 : currentPage;
    }

    /**
     * 每页记录数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 每页记录数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize < 1 ? 1 : pageSize;
    }

    /**
     * 总记录数
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 总记录数
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        this.pageCount = (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * 总页数
     */
    public Integer getPageCount() {
        return pageCount;
    }

    /**
     * 总页数
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * 排序方式
     * @param orderType
     */
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getOrderColumns() {
        return orderColumns;
    }

    /**
     * 要排序的列 如果sql语句中有排序就不要设置这个属性 要不然就会报错
     * @param orderColumns
     */
    public void setOrderColumns(String orderColumns) {
        this.orderColumns = orderColumns;
    }
}
