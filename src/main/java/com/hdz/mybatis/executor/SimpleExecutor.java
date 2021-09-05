package com.hdz.mybatis.executor;

import com.hdz.mybatis.cache.CacheKey;
import com.hdz.mybatis.common.Constant;
import com.hdz.mybatis.config.Configuration;
import com.hdz.mybatis.executor.paramter.DefaultParameterHandler;
import com.hdz.mybatis.executor.paramter.ParameterHandler;
import com.hdz.mybatis.executor.resultset.DefaultResultSetHandler;
import com.hdz.mybatis.executor.resultset.ResultSetHandler;
import com.hdz.mybatis.executor.statement.SimpleStatementHandler;
import com.hdz.mybatis.executor.statement.StatementHandler;
import com.hdz.mybatis.mapping.MappedStatement;

import java.sql.*;
import java.util.List;

/**
 * @ClassName SimpleExecutor
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 12:25
 * @Version 1.0
 **/
public class SimpleExecutor extends BaseExecutor {
    
    private static Connection connection;

    static {    
        initConnection();
    }

    public SimpleExecutor(Configuration configuration){
        super(configuration);
    }


    @Override
    public <E> List<E> doQuery(MappedStatement ms, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取数据库连接
            connection = this.getConnection();

            //获取配置类
            Configuration configuration = ms.getConfiguration();

            //实例化statementHandler对象
            StatementHandler statementHandler = configuration.newStatementHandler(ms);

            //通过statementHandler和connection获取prepareStatement
            preparedStatement = statementHandler.prepare(connection);

            //获取参数处理器parameterHandler
            ParameterHandler parameterHandler = configuration.newParameterHandler(parameter);

            //设置参数
            parameterHandler.setParameters(preparedStatement);

            //执行sql语句
            resultSet = statementHandler.query(preparedStatement);

            //获取结果集处理对象
            ResultSetHandler resultSetHandler = configuration.newDefaultResultSetHandler(ms);

            //返回处理的结果
            return resultSetHandler.handleResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return null;
    }

    @Override
    public int doUpdate(MappedStatement ms, Object parameter) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //获取数据库连接
            connection = this.getConnection();

            //获取配置类
            Configuration configuration = ms.getConfiguration();

            //实例化statementHandler对象
            StatementHandler statementHandler = configuration.newStatementHandler(ms);

            //通过statementHandler和connection获取prepareStatement
            preparedStatement = statementHandler.prepare(connection);

            //获取参数处理器parameterHandler
            ParameterHandler parameterHandler = configuration.newParameterHandler(parameter);

            //设置参数
            parameterHandler.setParameters(preparedStatement);

            return statementHandler.update(preparedStatement);

            //返回处理的结果
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //初始化数据库连接
    public static void initConnection(){
        String url = Configuration.getProperty(Constant.DB_URL_CONF);
        String driver = Configuration.getProperty(Constant.DB_DRIVER_CONF);
        String username = Configuration.getProperty(Constant.DB_USERNAME_CONF);
        String password = Configuration.getProperty(Constant.db_PASSWORD);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取连接
    public Connection getConnection() throws SQLException {
        if(connection!=null){
            System.out.println("数据库连接成功...");
            return connection;
        }
        else {
            throw new SQLException("数据库连接失败！！");
        }
    }

}
