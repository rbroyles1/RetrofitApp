package com.example.rxbro.retrofitapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxbro.retrofitapp.RandomUser.RandomUser;
import com.example.rxbro.retrofitapp.RandomUser.Result;
import com.example.rxbro.retrofitapp.RandomUser.User;

import java.net.MalformedURLException;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private static final String URL = "https://randomuser.me";
    private static final String RETRO_URL = "https://randomuser.me/spi";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ADDRESS = "USER_ADDRESS";
    private static final String USER_EMAIL = "USER_EMAIL";
    private TextView Nametv;
    private TextView Addresstv;
    private TextView Emailtv;
    private Button btnFetch;
    private Button listViewBtn;
    private Button recyclerViewBtn;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String name = msg.getData().getString(USER_NAME);
            String address = msg.getData().getString(USER_ADDRESS);
            String email = msg.getData().getString(USER_EMAIL);
            postResult(name, address, email);
        }

    };
    private ArrayList<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Nametv = (TextView) findViewById(R.id.Nametv);
        Addresstv = (TextView) findViewById(R.id.Addresstv);
        Emailtv = (TextView) findViewById(R.id.Emailtv);
        btnFetch = (Button) findViewById(R.id.btnFetch);
        btnFetch.setOnClickListener(this);
        listViewBtn = (Button)findViewById(R.id.listViewBtn);
        recyclerViewBtn = (Button)findViewById(R.id.recyclerViewBtn);
        listViewBtn.setOnClickListener(this);
        recyclerViewBtn.setOnClickListener(this);
        userList = new ArrayList<>();
    }

    public void postResult(String fullname, String address, String email) {
       String resultName = String.format(getString(R.string.Nametv), fullname);
        String resultAddress = String.format(getString(R.string.Addresstv), address);
        String resultEmail = String.format(getString(R.string.Emailtv), email);
        Nametv.setText(resultName);
        Addresstv.setText(resultAddress);
        Emailtv.setText(resultEmail);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.btnFetch:
               doRetrofitNetworkCall();
               break;
           case R.id.listViewBtn:
               Intent listViewIntent = new Intent(MainActivity.this, ListViewActivity.class);
               listViewIntent.putParcelableArrayListExtra("data", userList);
               startActivity(listViewIntent);
               break;
           case R.id.recyclerViewBtn:
               Intent recyclerViewIntent = new Intent(MainActivity.this, RecyclerViewActivity.class);
               recyclerViewIntent.putParcelableArrayListExtra("data", userList);
               startActivity(recyclerViewIntent);
               break;
       }

    }


    private void doRetrofitNetworkCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RETRO_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitObject service = retrofit.create(RetrofitObject.class);
        retrofit2.Call<RandomUser> call = service.getRandomUser();
        call.enqueue(new retrofit2.Callback<RandomUser>() {
            @Override
            public void onResponse(retrofit2.Call<RandomUser> call, retrofit2.Response<RandomUser> response) {
                if (response.isSuccessful()) {
                    Message msg = handler.obtainMessage();
                    Bundle data = new Bundle();
                    String currentName = "";
                    String currentAddress = "";
                    String currentEmail = "";
                    RandomUser randomUser = response.body();
                    for (Result result : randomUser.getResults()) {
                        Log.d(TAG, "onResponse: Name is " + result.getName());
                        currentName = result.getName().getTitle() + " " + result.getName().getFirst() + " " + result.getName().getLast();
                        currentAddress = result.getLocation().getStreet() + " " + result.getLocation().getCity() + ", " + result.getLocation().getState() + " " + result.getLocation().getPostcode();
                        currentEmail = result.getEmail();
                        data.putString(USER_NAME, currentName);
                        data.putString(USER_ADDRESS, currentAddress);
                        data.putString(USER_EMAIL, currentEmail);
                        User currentUser = new User(currentName, currentAddress, currentEmail);
                        userList.add(currentUser);
                    }
                    msg.setData(data);
                    handler.sendMessage(msg);
                }

            }
            public void onFailure(retrofit2.Call<RandomUser> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
