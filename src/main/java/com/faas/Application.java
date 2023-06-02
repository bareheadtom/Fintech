package com.faas;

import cn.hutool.http.HttpUtil;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import static spark.Spark.awaitInitialization;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

/**
 * 启动类
 *
 * @author FAAS函数计算
 */
public class Application {

    public static ClassLoader FUNCTION_CLASS_LOADER = Application.class.getClassLoader();
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    private static final String DEFAULT_HANDLER = "com.faas.functions.MainFunction";
    private static final int STATUS_REPORT_TIMEOUT = 1000;
    private static final int DEFAULT_FAAS_PORT = 8080;
    private static final int CLASS_LENGTH = 2;

    public static void main(String[] arg) throws Exception {

        System.out.println("main hello");

        URLClassLoader urlClassLoader = null;
        try {
            // 端口
            String port = System.getenv("FAAS_PORT");
            int portAct;
            if (StringUtil.isBlank(port)) {
                portAct = DEFAULT_FAAS_PORT;
            } else {
                portAct = Integer.parseInt(port);
            }
            port(portAct);

            // 默认返回的路径
            regisIgnoredHandler();

            // 用户函数路径
            regisUserHandler();

            // 心跳检测
            reportStatus();
        } catch (Exception e) {
            LOGGER.error("函数启动异常", e);
            throw (e);
        } finally {
            if (urlClassLoader != null) {
                urlClassLoader.close();
            }
        }
    }

    private static void reportStatus() {
        String statusReportUrl = FaaSUtils.getEnv("FAAS_STATUS_REPORT_URL", null);
        String faasHealthCheckInterval = FaaSUtils.getEnv("FAAS_HEALTH_CHECK_INTERVAL", "5000");
        if (statusReportUrl == null) {
            return;
        }
        awaitInitialization();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    HttpUtil.get(statusReportUrl, STATUS_REPORT_TIMEOUT);
                } catch (Exception e) {
                    LOGGER.error("上报状态异常", e);
                }

            }
        }, 0, Integer.parseInt(faasHealthCheckInterval));
    }

    /**
     * 以下请求路径默认返回200
     */
    private static void regisIgnoredHandler() {
        get("/favicon.ico", IgnoredHandler.INSTANCE);
        get("/healthz", IgnoredHandler.INSTANCE);
        get("/healthz/ready", IgnoredHandler.INSTANCE);
    }

    /**
     * 注册用户函数
     *
     * @throws Exception
     */
    private static void regisUserHandler() throws Exception {
        String handler = System.getenv("FAAS_HANDLER");
        if (StringUtil.isBlank(handler)) {
            handler = DEFAULT_HANDLER;
        }
        Class clazz = FUNCTION_CLASS_LOADER.loadClass(handler);
        Type[] types = ((ParameterizedType) clazz.getGenericInterfaces()[0]).getActualTypeArguments();
        if (types.length != CLASS_LENGTH) {
            throw new RuntimeException("handler is not correct");
        }
        final Class requestType = FUNCTION_CLASS_LOADER.loadClass(types[0].getTypeName());

        Object inst = clazz.getDeclaredConstructor().newInstance();
        if (!(inst instanceof Function)) {
            throw new RuntimeException("handler is not correct");
        }

        UserHandler userHandler = new UserHandler(requestType, inst, clazz);
        post("/*", userHandler);
        get("/*", userHandler);
        put("/*", userHandler);
        delete("/*", userHandler);
    }
}

/**
 * 需要忽略的处理函数
 */
class IgnoredHandler implements Route {

    public static final IgnoredHandler INSTANCE = new IgnoredHandler();

    @Override
    public Object handle(Request request, Response response) {
        return "ok";
    }
}