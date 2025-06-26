package com.fiap.foodcore.application.port.in;

public class PagedResult {

    private Integer page;
    private Integer size;

    public void PagedRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public PagedResult(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}