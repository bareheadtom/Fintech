package com.faas.model;
import java.lang.reflect.Field;
public class OperationConfig {
    String name;
    String table;
    Boolean add;
    Boolean delete;
    Boolean update;
    Boolean get;
    String detail;

    public String getName() {
        return name;
    }

    public String getTable() {
        return table;
    }

    public Boolean getAdd() {
        return add;
    }

    public Boolean getDelete() {
        return delete;
    }

    public Boolean getUpdate() {
        return update;
    }

    public Boolean getGet() {
        return get;
    }

    public String getDetail() {
        return detail;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTable(final String table) {
        this.table = table;
    }

    public void setAdd(final Boolean add) {
        this.add = add;
    }

    public void setDelete(final Boolean delete) {
        this.delete = delete;
    }

    public void setUpdate(final Boolean update) {
        this.update = update;
    }

    public void setGet(final Boolean get) {
        this.get = get;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        try{
            Field[] fields = this.getClass().getDeclaredFields();
            sb.append("\r\n");
            for(Field field : fields){
                sb.append(field.getName().substring(field.getName().lastIndexOf(".")+1)+" : ")
                        .append(this.getClass().getMethod("get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1)).invoke(this))
                        .append("\r\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
