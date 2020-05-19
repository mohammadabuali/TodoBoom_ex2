package com.example.TodoBoom_ex2.todo;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.TodoBoom_ex2.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TodoAdaptor extends RecyclerView.Adapter<TodoAdaptor.TodoViewHolder> {
    public static class TodoViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private ImageView img;



        TodoViewHolder(View view){
            super(view);
            text = itemView.findViewById(R.id.todo_description);
            img = itemView.findViewById(R.id.todo_icon);

        }
    }
    private static List<Todo> items = new ArrayList<Todo>();

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.task_line, parent, false);
        final TodoViewHolder holder = new TodoViewHolder(view);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position = holder.getLayoutPosition();
                Todo tr = (Todo) items.get(position);
                if(!tr.isDone){
                    tr.isDone = true;
                    CharSequence text = "Task "+tr.description+" has been cleared!";
                    int duration = Toast.LENGTH_SHORT;
                    holder.img.setImageResource(R.drawable.done_icon);
                    holder.itemView.setBackgroundResource(R.drawable.back_done);
                    Toast.makeText(context, text, duration).show();
                    saveData(context);
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.pop_up);
                Button yesBtn = myDialog.findViewById(R.id.yesBtn);
                Button noBtn = myDialog.findViewById(R.id.noBtn);
                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.remove(items.get(holder.getLayoutPosition()));
                        saveData(context);
                        myDialog.dismiss();
                    }
                });
                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo item = (Todo) items.get(position);
        holder.text.setText(item.description);
        if (item.isDone){
            holder.img.setImageResource(R.drawable.done_icon);
            holder.itemView.setBackgroundResource(R.drawable.back_done);
        }
        else{
            holder.img.setImageResource(0);
            holder.itemView.setBackgroundResource(0);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addTask(String description, Context context) {
        Todo tr = new Todo(description, false);
        items.add(tr);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = gson.toJson(items);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("kkk", json).apply();
        notifyDataSetChanged();
    }
    public void removeTask(TodoViewHolder holder, Context context){
        return;
    }
    public void setArray(Context context)
    {
        Gson gson = new Gson();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sp.getString("kkk", null);
        Type type = new TypeToken<ArrayList<Todo>>(){}.getType();
        items = gson.fromJson(json, type);
        if(items == null){
            items = new ArrayList<Todo>();
        }
//        items = arr;
        notifyDataSetChanged();
    }
    public void saveData(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = gson.toJson(items);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("kkk", json).apply();
        notifyDataSetChanged();
    }
}
