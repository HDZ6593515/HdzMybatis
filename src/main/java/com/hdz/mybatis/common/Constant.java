package com.hdz.mybatis.common;

/**
 * @EnumName Constant
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 12:41
 * @Version 1.0
 **/
public interface Constant {

    /******** 在properties文件中配置信息 **************/
    String MAPPER_LOCATION = "mapper.location";

    String DB_DRIVER_CONF = "db.driver";

    String DB_URL_CONF = "db.url";

    String DB_USERNAME_CONF = "db.username";

    String db_PASSWORD = "db.password";


    /** UTF-8编码 */
    String CHARSET_UTF8 = "UTF-8";

    /************ mapper xml  ****************/
    /** mapper文件后缀 */
    String MAPPER_FILE_SUFFIX = ".xml";

    String XML_ROOT_LABEL = "mapper";

    String XML_ELEMENT_ID = "id";

    String XML_SELECT_NAMESPACE = "namespace";

    String XML_SELECT_RESULTTYPE = "resultType";

    /** sqlType相关常量 */
    String SELECT = "select";

    String DELETE = "delete";

    String UPDATE = "update";

    String INSERT = "insert";

    String DEFAULT = "default";

}
