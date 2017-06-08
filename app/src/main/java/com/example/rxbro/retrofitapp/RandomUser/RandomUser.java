package com.example.rxbro.retrofitapp.RandomUser;

import java.util.List;

/**
 * Created by rxbro on 6/8/2017.
 */

public class RandomUser {
    private List<Result> results = null;
    private Info info;


    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
    @Override
    public String toString() {
        return "RandomUser{" +
                "results=" + results +
                ", info=" + info +
                '}';
    }

}
