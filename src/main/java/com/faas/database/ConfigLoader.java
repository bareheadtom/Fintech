package com.faas.database;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.faas.model.OperationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigLoader.class);
    public static final Map<String, OperationConfig> OPERATION_CONFIG_MAP;
    static {
        FileResource fr = new FileResource("/config.json");
        String configString = fr.read();
        JSONObject jsonObject = new JSONObject(configString);
        JSONArray array = jsonObject.getJSONArray("OperationConfig");
        List<OperationConfig> operationConfigList = array.toList(OperationConfig.class);
        Map<String, OperationConfig> map = new HashMap<>(operationConfigList.size());
        operationConfigList.forEach(e -> {
            map.put(e.getName(), e);
        });
        OPERATION_CONFIG_MAP = map;
        LOGGER.info("--------------配置文件读取结束-----------------");
    }
}
