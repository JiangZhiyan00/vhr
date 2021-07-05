package com.jiangzhiyan.vhr.base;

/**
 * 分页查询query类
 */
public class BaseQuery {

    protected Integer pageNum = 1;
    protected Integer pageSize = 10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
