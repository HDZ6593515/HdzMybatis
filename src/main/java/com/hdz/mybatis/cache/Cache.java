package com.hdz.mybatis.cache;

/**
 * @InterfaceName Cache
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/28 18:25
 * @Version 1.0
 **/
public interface Cache {
    
    int getSize();
    
    
    Object getObject(Object key);
    
    Object putObject(Object key,Object value);
    
    Object removeObject(Object key);

    void clearCache();
}
