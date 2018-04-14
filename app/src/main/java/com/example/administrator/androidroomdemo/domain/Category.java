package com.example.administrator.androidroomdemo.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "category")                                                                     // 用来注解一个实体类, 对应数据库一张表, 使用tableName参数指定数据库的表名
public class Category {

    @PrimaryKey(autoGenerate = true)                                                                // 主键
    @ColumnInfo(name = "id")                                                                         // 对应的数据库的字段名稱
    private long id;

    @ColumnInfo(name = "name")                                                                      // 对应的数据库的字段名稱
    private String name;

    /** 为了保存每一个字段, 每个字段需要有可以访问的gettter/setter方法或者是public的属性 */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
