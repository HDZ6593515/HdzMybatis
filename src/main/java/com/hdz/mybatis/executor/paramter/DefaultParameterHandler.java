package com.hdz.mybatis.executor.paramter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassName DefaultParameterHandler
 * @Description TODO
 * @Author 华达州
 * @Date 2021/8/19 19:18
 * @Version 1.0
 **/
public class DefaultParameterHandler implements ParameterHandler {

    private Object parameter;

    public DefaultParameterHandler(Object parameter){
        this.parameter = parameter;
    }

    @Override
    public void setParameters(PreparedStatement preparedStatement) {
        try {
            if(parameter!=null){
                if(parameter.getClass().isArray()){
                    Object[] params = (Object[]) parameter;
                    System.out.print("====> Parameters：");
                    for (int i=0; i<params.length; i++) {
                        if(params[i] instanceof String){
                            System.out.print(params[i]+"(String)   ");
                        }
                        else if(params[i] instanceof Integer){
                            System.out.print(params[i]+"(Integer)   ");
                        }
                        preparedStatement.setObject(i+1,params[i]);
                    }
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
