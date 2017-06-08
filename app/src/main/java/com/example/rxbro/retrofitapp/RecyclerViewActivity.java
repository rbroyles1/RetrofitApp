package com.example.rxbro.retrofitapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.rxbro.retrofitapp.RandomUser.User;

import java.util.ArrayList;

/**
 * Created by rxbro on 6/7/2017.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<User> users;
    UserRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        users = getIntent().getParcelableArrayListExtra("data");
        adapter = new UserRecyclerAdapter(users, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
    }
}
