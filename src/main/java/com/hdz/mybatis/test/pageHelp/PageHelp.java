package com.hdz.mybatis.test.pageHelp;

/**
 * @ClassName PageHelp
 * @Description TODO
 * @Author 华达州
 * @Date 2021/9/5 18:50
 * @Version 1.0
 **/
public class PageHelp {
    
    private static ThreadLocal<Page> localPage = new ThreadLocal();
    
    public static void startPage(int pageNum,int pageSize){
        Page page = new Page(pageNum, pageSize);
        localPage.set(page);
    }
    
    
    public static void removePage(){
        localPage.remove();
    }
    
    public static Page getPage(){
        return localPage.get();
    }
    
}
