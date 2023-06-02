package com.faas.functions;

import cn.hutool.json.JSONObject;
import com.faas.model.OperationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faas.database.ConfigLoader;
import com.faas.database.DbExcutor;
import com.faas.enums.OperationEnum;

import java.util.function.Function;
import spark.Request;
import spark.route.HttpMethod;


public class MainFunction implements Function<Request, Object> {

    private Logger logger = LoggerFactory.getLogger(MainFunction.class);

    public MainFunction() {

       System.out.println("my33数据库连接测试：{}"+ DbExcutor.query("select 1"));
        //logger.info("my2表配置加载数目：{}", ConfigLoader.OPERATION_CONFIG_MAP.size());
        //測試查詢
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table where score = 30"));
        //增加
        System.out.println("add{}"+ DbExcutor.execute("insert into test_table (name, comment) values('com', '帥哥');"));
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
        //修改
        System.out.println("update{}"+ DbExcutor.execute("update test_table set comment = '帥哥++' where name = 'com';"));
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
        //刪除
        System.out.println("add{}"+ DbExcutor.execute("insert into test_table (name, comment) values('suchangshengde', '帥哥susususu');"));
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
        //logger.info("delete{}", DbExcutor.execute("delete from test_table where name = 'suchangshengde'"));
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));
        //logger.info("delete{}", DbExcutor.execute("delete from test_table"));
        System.out.println("select：{}"+ DbExcutor.query("select * from test_table"));

        System.out.println("add{}"+ DbExcutor.execute("insert into test_table (name, comment) values('suchangshengde', '帥哥susususu');"));
        System.out.println("end");
        System.out.println("表配置加载数目：{}"+ ConfigLoader.OPERATION_CONFIG_MAP.size());
        for (String key : ConfigLoader.OPERATION_CONFIG_MAP.keySet()) {
            OperationConfig op = ConfigLoader.OPERATION_CONFIG_MAP.get(key);
            System.out.println(op);
        }
    }

    @Override
    public Object apply(Request request) {
        // 限制为POST请求
        logger.info("mysuchangshenglog*********************");
        logger.info(request.requestMethod());
        logger.info(request.uri());
        logger.info(request.queryParams("test"));
        logger.info(request.body());
        logger.info("mysuchangshenglog*********************");

        if (HttpMethod.post.name().equalsIgnoreCase(request.requestMethod())) {
            // 校验body为json
            JSONObject jsonBody = new JSONObject(request.body());
            logger.info("请求体：{}", jsonBody);
            // 根据路径区分不同的sql操作
            OperationEnum operation = OperationEnum.buildFromPath(request.uri().substring(1));
            switch (operation) {
                case ADD:
                    return DbExcutor.execute("insert into test_table (name,comment) values ('张三','他人不错')");
                case DELETE:
                    return DbExcutor.execute("delete from test_table where `name`='张三'");
                case UPDATE:
                    return DbExcutor.execute("update test_table set `comment` = '他比较幽默' where `name`='张三'");
                case GET:
                    return DbExcutor.query("select * from test_table where `name`='张三'");
            }
        }
        return "suchangsheng2";
    }
}