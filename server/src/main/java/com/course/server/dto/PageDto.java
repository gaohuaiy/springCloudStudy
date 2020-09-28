package com.course.server.dto;

import java.util.List;

/**
 * @author huaiyu.gao
 * @version 1.0
 * @date 2020/9/28 15:10
 */
public class PageDto<T> {
    private int page;
    private int size;
    private long total;
    private List<T> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
