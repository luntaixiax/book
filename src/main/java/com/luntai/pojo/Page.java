package com.luntai.pojo;

import java.util.List;

public class Page<T> {

    public static final Integer PAGE_SIZE = 4; // how many items(rows) to show per page

    private Integer pageNo;     // current page number
    private Integer pageTotal;  // total number of pages
    private Integer pageSize = PAGE_SIZE;   // how many items(rows) to show per page
    private Integer pageTotalCount; // total number of entries(rows) across all pages
    private List<T> items;  // items on current page
    private String url;     // request url for this page

    public Page() {
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        // check if page number overflow or underflow
        if (pageNo < 1) {
            pageNo = 1;
        }
        if (pageNo > this.getPageTotal()) {
            pageNo = this.getPageTotal();
        }

        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                ", pageTotalCount=" + pageTotalCount +
                ", items=" + items +
                ", url='" + url + '\'' +
                '}';
    }
}
