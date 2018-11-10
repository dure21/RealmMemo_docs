package com.yeoga.realmmemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    Button button;
    EditText editText;
    public static Realm realm;
    public static RealmResults<dbModel> realmResults;
    public static dbModel dbmodel;
    public static Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager mStaggeredGridManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredGridManager);
        realmResults = realm.where(dbModel.class).findAll();
        adapter = new Adapter(realmResults, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        editText = (EditText) findViewById(R.id.editText);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    realm.beginTransaction();
                    dbmodel = realm.createObject(dbModel.class);
                    dbmodel.setText(editText.getText().toString());
                    realm.commitTransaction();
                    editText.setText("");
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);


//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                // 드래그앤드롭 시
//                adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                // 아이템 스와이프 시
//                realm.beginTransaction();
//                Model model = models.get(viewHolder.getAdapterPosition());
//                model.deleteFromRealm();
//                realm.commitTransaction();
//            }
//        }).attachToRecyclerView(recyclerView);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                realm.beginTransaction();
                dbmodel = realm.createObject(dbModel.class);
                dbmodel.setText(editText.getText().toString());
                realm.commitTransaction();
                editText.setText("");
                adapter.notifyDataSetChanged();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.setAdapter(null);
        realm.close();
    }
}
