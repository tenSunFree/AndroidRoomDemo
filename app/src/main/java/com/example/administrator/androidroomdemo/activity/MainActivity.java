package com.example.administrator.androidroomdemo.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.administrator.androidroomdemo.R;
import com.example.administrator.androidroomdemo.adapter.NoteAdapter;
import com.example.administrator.androidroomdemo.database.AppDatabase;
import com.example.administrator.androidroomdemo.domain.CategoryNote;
import com.example.administrator.androidroomdemo.domain.Note;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NoteAdapter.ActionCallback, OnClickListener {


    public static boolean isHasBeenRecreate = false;
    public static boolean isTheNotificationRecreateFromEditNoteActivity = true;
    public static int isOpenApplicationForTheFirstTime = 0;
    public static int isOpenApplicationForTheFirstTime2 = 0;
    private NoteAdapter adapter;
    private AppDatabase db;
    private FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isOpenApplicationForTheFirstTime2 == 0) {
            isTheNotificationRecreateFromEditNoteActivity = true;
            isOpenApplicationForTheFirstTime2 = 1;
        }

        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();     // 获取AppDatabase实例
        add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(this);

        adapter = new NoteAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (MainActivity.isOpenApplicationForTheFirstTime == 1) {
            MainActivity.isOpenApplicationForTheFirstTime = 2;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    recreate();
                }
            }, 200);
        }

        /** 使RecyclerView 可以滑动删除和拖拽 */
        ItemTouchHelper.SimpleCallback callback = new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            /**
             * 拖拽到新位置时候的回调方法
             * @param viewHolder: 拖动的ViewHolder
             * @param target: 目标位置的ViewHolder
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                return false;
            }

            /**
             * 滑动时的回调方法
             * @param viewHolder: 滑动的ViewHolder
             * @param direction: 滑动的方向
             */
            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                deleteNote(adapter.getNote(viewHolder.getAdapterPosition()));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        loadNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();

        /**  為了解決自定義 RecyclerView的Item的layout_weight 衍生的問題 */
        if (MainActivity.isTheNotificationRecreateFromEditNoteActivity == true) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    recreate();
                    MainActivity.isHasBeenRecreate = false;
                }
            }, 500);
            MainActivity.isHasBeenRecreate = true;
            MainActivity.isTheNotificationRecreateFromEditNoteActivity = false;
        }
    }

    @Override
    public void onEdit(CategoryNote note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtras(EditNoteActivity.newInstanceBundle(note.getId()));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                startActivity(new Intent(this, EditNoteActivity.class));
                break;
        }
    }

    private void loadNotes() {

        /** AsyncTask<Params, Progress, Result>: Params 执行后台任务时传入的参数类型, Progress 后台任务执行进度的类型, Result 后台任务执行完毕返回的结果类型 */
        new AsyncTask<Void, Void, List<CategoryNote>>() {

            /** 执行的后台任务, 在子线程执行 */
            @Override
            protected List<CategoryNote> doInBackground(Void... params) {
                return db.getNoteDao().getCategoryNotes();
            }

            /** 在主线程执行, 任务执行结束时候调用 */
            @Override
            protected void onPostExecute(List<CategoryNote> notes) {
                adapter.setNotes(notes);
            }
        }.execute();
    }

    private void deleteNote(Note note) {
        new AsyncTask<Note, Void, Void>() {
            @Override
            protected Void doInBackground(Note... params) {
                db.getNoteDao().deleteAll(params);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                loadNotes();
            }
        }.execute(note);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // isOpenApplicationForTheFirstTime = true;
    }
}
