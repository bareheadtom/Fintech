package com.faas.functions;

import com.faas.database.ConfigLoader;
import com.faas.model.OperationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName MainSu
 * @Description TODO
 * @Author 86130
 * @Date 2023/6/2 20:32
 * @Version 1.0
 **/
public class MainSu {
    private static Logger logger = LoggerFactory.getLogger(MainSu.class);
    public static void main(String[] args) {
        //System.out.println("hello");
        //System.out.println(DbExcutor.query("select * from test_table"));
//        System.out.println("my33数据库连接测试：{}"+ DbExcutor.query("select 1"));
//        //logger.info("my2表配置加载数目：{}", ConfigLoader.OPERATION_CONFIG_MAP.size());
//        //測試查詢
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table where score = 30"));
//        //增加
//        System.out.println("add{}"+ DbExcutor.execute("insert into test_table (name, comment) values('com', '帥哥');"));
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
//        //修改
//        System.out.println("update{}"+ DbExcutor.execute("update test_table set comment = '帥哥++' where name = 'com';"));
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
//        //刪除
//        System.out.println("add{}"+ DbExcutor.execute("insert into test_table (name, comment) values('suchangshengde', '帥哥susususu');"));
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
//        //logger.info("delete{}", DbExcutor.execute("delete from test_table where name = 'suchangshengde'"));
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
//        //logger.info("delete{}", DbExcutor.execute("delete from test_table"));
//        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
//
//        System.out.println("add{}"+ DbExcutor.execute("insert into test_table (name, comment) values('suchangshengde', '帥哥susususu');"));
//        System.out.println("end");
        System.out.println("表配置加载数目：{}"+ ConfigLoader.OPERATION_CONFIG_MAP.size());
        for (String key : ConfigLoader.OPERATION_CONFIG_MAP.keySet()) {
            OperationConfig op = ConfigLoader.OPERATION_CONFIG_MAP.get(key);
            System.out.println(op);
        }
    }

}
