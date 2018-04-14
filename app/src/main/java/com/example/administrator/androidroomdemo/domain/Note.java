package com.example.administrator.androidroomdemo.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;


@Entity(tableName = "note")                                                                         // 用来注解一个实体类, 对应数据库一张表, 使用tableName参数指定数据库的表名
public class Note {

    @PrimaryKey(autoGenerate = true)                                                                // 主鍵
    @ColumnInfo(name = "id")                                                                        // 对应的数据库的字段名稱
    private long id;

    @ColumnInfo(name = "title")                                                                     // 对应的数据库的字段名稱
    private String title;

    @ColumnInfo(name = "description")                                                               // 对应的数据库的字段名稱
    private String description;

    @ColumnInfo(name = "category_id")                                                               // 对应的数据库的字段名稱
    private Long categoryId;

    /** 为了保存每一个字段, 每个字段需要有可以访问的gettter/setter方法或者是public的属性 */
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @Nullable
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(@Nullable Long categoryId) {
        this.categoryId = categoryId;
    }
}
