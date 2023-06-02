package com.faas.service;

import cn.hutool.json.JSONObject;

public interface OpService {
    public int add(JSONObject o);
    public int update(JSONObject o);
    public int delete(JSONObject o);
    public Object get(JSONObject o);
}
