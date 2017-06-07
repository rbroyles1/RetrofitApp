package com.example.rxbro.retrofitapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxbro.retrofitapp.RandomUser.Result;
import com.example.rxbro.retrofitapp.RandomUser.User;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "_TAG";
    private static final String URL = "https://randomuser.me";
    private TextView Nametv;
    private TextView Addresstv;
    private TextView Emailtv;
    private Button btnFetch;
    private ArrayList<Result> results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Nametv = (TextView) findViewById(R.id.Nametv);
        Addresstv = (TextView) findViewById(R.id.Addresstv);
        Emailtv = (TextView) findViewById(R.id.Emailtv);
        btnFetch = (Button) findViewById(R.id.btnFetch);
        btnFetch.setOnClickListener(this);
        results = new ArrayList<Result>();
    }

    public void postResult(String fullname, String address, String email) {
        Nametv.setText(fullname);
        Addresstv.setText(address);
        Emailtv.setText(email);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void onClick(View v) {
        doRetrofitNetworkCall();
    }


    private void doRetrofitNetworkCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitObject service = retrofit.create(RetrofitObject.class);
        retrofit2.Call<User> call = service.getUser();
        call.enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(retrofit2.Call<User> call, retrofit2.Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    for (Result r : user.getResults()) {
                        Log.d(TAG, "doRetrofitNetworkCall: Name is " + r.getName());
                        String fullname = r.getName().getFirst() + " " + r.getName().getLast();
                        String address = r.getLocation().getStreet() + "\n" + r.getLocation().getCity() +
                                ", " + r.getLocation().getState() + ", " + r.getLocation().getPostcode();
                        String email = r.getEmail();
                        postResult(fullname, address, email);
                        results.add(r);
                        Log.d(TAG, "onResponse: " + results.toString());
                    }
                }
            }
            public void onFailure(retrofit2.Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
