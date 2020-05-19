package com.example.TodoBoom_ex2;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.TodoBoom_ex2.todo.Todo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyApp extends Application {
    public ArrayList<Todo> lst;
    @Override

    public void onCreate() {
        super.onCreate();
        Gson gson = new Gson();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String json = sp.getString("kkk", null);
        Type type = new TypeToken<ArrayList<Todo>>(){}.getType();
        lst = gson.fromJson(json, type);
        if(lst == null){
            lst= new ArrayList<Todo>();
        }
        Log.d("some_key", String.format("there are %d todo items", lst.size()));

    }
}
