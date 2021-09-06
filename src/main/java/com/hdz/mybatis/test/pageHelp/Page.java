package com.hdz.mybatis.test.pageHelp;

/**
 * @ClassName HdzPage
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 18:40
 * @Version 1.0
 **/
public class Page {
    
    private long pageNum;
    
    private long pageSize;
    
    private long offset;
    
    private long total;

    public Page(long pageNum, long pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        if(pageNum<0||pageSize<0){
            this.offset = 0;
        }
        else{
            this.offset = (pageNum-1)*pageSize;
        }
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", total=" + total +
                '}';
    }
}
