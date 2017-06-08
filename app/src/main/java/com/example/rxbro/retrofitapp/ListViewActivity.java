package com.example.rxbro.retrofitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.rxbro.retrofitapp.RandomUser.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rxbro on 6/7/2017.
 */

public class ListViewActivity extends AppCompatActivity {
    private static final String TAG = "_TAG";
    ListView userListView;
    ArrayList<User> users;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        users = new ArrayList<>();
        users = getIntent().getParcelableArrayListExtra("data");
        userListView = (ListView)findViewById(R.id.list_users);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userAdapter = new UserAdapter(users, this);
        userListView.setAdapter(userAdapter);
    }
}
