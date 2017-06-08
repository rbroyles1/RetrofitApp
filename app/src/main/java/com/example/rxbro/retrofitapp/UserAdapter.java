package com.example.rxbro.retrofitapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rxbro.retrofitapp.RandomUser.User;

import java.util.ArrayList;

/**
 * Created by rxbro on 6/7/2017.
 */

public class UserAdapter extends BaseAdapter {
    private ArrayList<User> users;
    private Context context;

    public UserAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;

    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        User currentUser = (User)getItem(position);
        holder.userInfo.setText(currentUser.getName() + " " + currentUser.getAddress() + " " + currentUser.getEmail());
        return convertView;
    }

    private class ViewHolder {
        TextView userInfo;

        public ViewHolder(View view) {
            userInfo = (TextView)view.findViewById(R.id.tv_name);
        }

    }



}
