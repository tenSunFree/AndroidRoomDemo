package com.example.administrator.androidroomdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.administrator.androidroomdemo.dao.CategoryDao;
import com.example.administrator.androidroomdemo.dao.NoteDao;
import com.example.administrator.androidroomdemo.domain.Category;
import com.example.administrator.androidroomdemo.domain.Note;


/**
 * @author acampbell
 */
@Database(entities = {Note.class, Category.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "app_db";

    public abstract NoteDao getNoteDao();

    public abstract CategoryDao getCategoryDao();

}
