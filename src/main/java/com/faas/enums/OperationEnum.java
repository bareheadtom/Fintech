package com.faas.enums;

import java.util.Arrays;
import java.util.Objects;

public enum OperationEnum {
    // 增
    ADD("add", "增"),

    // 删
    DELETE("delete", "删"),

    // 改
    UPDATE("update", "改"),

    // 查
    GET("get", "查");

    private String path;
    private String description;

    OperationEnum(String path, String description) {
        this.path = path;
        this.description = description;
    }

    public String getPath() {
        return this.path;
    }

    public String getDescription() {
        return this.description;
    }

    public static OperationEnum buildFromPath(String path) {
        return Arrays.stream(values())
                .filter(val -> Objects.equals(val.getPath(), path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不支持的操作方法！"));
    }
}
