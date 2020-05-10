package com.example.TodoBoom_ex2.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.TodoBoom_ex2.R;

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
//    private List<Try> items = Arrays.asList(new Try("man", false));
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
                }
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

    public void addTask(String description) {
        Todo tr = new Todo(description, false);
//        System.out.println(items.get(0));
        items.add(tr);
        notifyDataSetChanged();
    }
}
