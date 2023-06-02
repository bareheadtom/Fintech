package com.faas.functions;

import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faas.database.ConfigLoader;
import com.faas.database.DbExcutor;
import com.faas.enums.OperationEnum;
import com.faas.service.OpService;
import com.faas.service.OpServiceImpl;

import java.util.function.Function;
import spark.Request;
import spark.route.HttpMethod;


public class MainFunction implements Function<Request, Object> {

    private Logger logger = LoggerFactory.getLogger(MainFunction.class);

    private OpService opService = new OpServiceImpl();

    public MainFunction() {
        logger.info("数据库连接测试：{}", DbExcutor.query("select * from test_table"));
        logger.info("表配置加载数目：{}", ConfigLoader.OPERATION_CONFIG_MAP.size());
    }

    @Override
    public Object apply(Request request) {
        logger.info("method: {}", request.requestMethod());
        // 限制为POST请求
        if (HttpMethod.post.name().equalsIgnoreCase(request.requestMethod())) {
            // 校验body为json
            JSONObject jsonBody = new JSONObject(request.body());
            logger.info("请求体：{}", jsonBody);
            // 根据路径区分不同的sql操作
            OperationEnum operation = OperationEnum.buildFromPath(request.uri().substring(1));
            switch (operation) {
                case ADD:
                    return opService.add(jsonBody);
                case DELETE:
                    return opService.delete(jsonBody);
                case UPDATE:
                    return opService.update(jsonBody);
                case GET:
                    return opService.get(jsonBody);
                // case ADD:
                //     logger.info("add");
                //     return opService.get(jsonBody)
                //     return DbExcutor.execute("insert into test_table (name,comment) values ('张三','他人不错')");
                // case DELETE:
                //     return DbExcutor.execute("delete from test_table where `name`='张三'");
                // case UPDATE:
                //     return DbExcutor.execute("update test_table set `comment` = '他比较幽默' where `name`='张三'");
                // case GET:
                //     return DbExcutor.query("select * from test_table where `name`='张三'");
            }
        }
        return "WHY FAILED";
    }
}