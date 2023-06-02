package com.faas.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faas.database.DbExcutor;
import com.faas.database.ConfigLoader;

import com.faas.model.OperationConfig;

public class OpServiceImpl implements OpService {

    private Logger logger = LoggerFactory.getLogger(OpServiceImpl.class);

    @Override
    public int add(JSONObject o) {

        StringBuilder sqlString = new StringBuilder();
        String sqlFinal = "";

        for (String key : o.keySet()) {
            System.out.println(o.get(key).getClass());
            if (o.get(key) instanceof JSONObject) {
                JSONObject ob = (JSONObject)o.get(key);
                OperationConfig table = ConfigLoader.OPERATION_CONFIG_MAP.get(key);
                String tableName = table.getTable();

                sqlString.append("insert into ").append(tableName).append(" (");
                StringBuilder valSb = new StringBuilder();
                valSb.append(" values (");

                for (String key2 : ob.keySet()) {
                    Object value = ob.get(key2);

                    sqlString.append(key2).append(",");
                    if (value instanceof String) {
                        valSb.append("'").append(value).append("'").append(",");
                    } else {
                        valSb.append(value).append(",");
                    }
                }
                sqlString.deleteCharAt(sqlString.length() - 1);
                sqlString.append(")");
                valSb.deleteCharAt(valSb.length() - 1);
                valSb.append(")");
                sqlString.append(valSb.toString());
                sqlFinal = sqlString.toString();
                logger.info("sql[{}]", sqlFinal);
            } else if (o.get(key) instanceof JSONArray) {
                JSONArray list = (JSONArray) o.get(key);
                StringBuilder valSb = new StringBuilder();

                JSONObject ob = (JSONObject)list.get(0);
                OperationConfig table = ConfigLoader.OPERATION_CONFIG_MAP.get(key.substring(0, key.length() - 2));
                String tableName = table.getTable();

                sqlString.append("insert into ").append(tableName).append(" (");
                StringBuilder valFirstSb = new StringBuilder();
                valFirstSb.append(" values (");

                for (String key2 : ob.keySet()) {
                    Object value = ob.get(key2);

                    sqlString.append(key2).append(",");
                    if (value instanceof String) {
                        valFirstSb.append("'").append(value).append("'").append(",");
                    } else {
                        valFirstSb.append(value).append(",");
                    }
                }
                sqlString.deleteCharAt(sqlString.length() - 1);
                sqlString.append(")");
                valFirstSb.deleteCharAt(valFirstSb.length() - 1);
                valFirstSb.append(")");
                sqlString.append(valFirstSb.toString());

                for (int i = 1; i < list.size(); i++) {
                    JSONObject tmpVal = (JSONObject)list.get(i);
                    String subVal = getInsertOneSqlString(tmpVal);
                    valSb.append(",").append(subVal);
                }

                sqlString.append(valSb);
                sqlFinal = sqlString.toString();
                logger.info("sql[{}]", sqlFinal);
            }

        }
        return DbExcutor.execute(sqlFinal);
    }

    @Override
    public int update(JSONObject o) {
        // TODO Auto-generated method stub
        return DbExcutor.execute("update test_table set `comment` = '他比较幽默' where `name`='张三'");
    }

    @Override
    public int delete(JSONObject o) {
        // TODO Auto-generated method stub
        return DbExcutor.execute("delete from test_table where `name`='张三'");
    }

    @Override
    public Object get(JSONObject o) {
        // TODO Auto-generated method stub
        return DbExcutor.query("select * from test_table where `name`='张三'");
    }

    private String getInsertOneSqlString(JSONObject o) {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("(");
        for (String key : o.keySet()) {
            Object value = o.get(key);

            if (value instanceof String) {
                sqlString.append("'").append(value).append("'").append(",");
            } else {
                sqlString.append(value).append(",");
            }
        }
        sqlString.deleteCharAt(sqlString.length() - 1);
        sqlString.append(")");
        return sqlString.toString();
    }

}
