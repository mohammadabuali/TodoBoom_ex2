package com.example.TodoBoom_ex2;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TodoBoom_ex2.todo.TodoAdaptor;


public class MainActivity extends AppCompatActivity {
    public TodoAdaptor adapter = new TodoAdaptor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApp app = (MyApp) getApplicationContext();
        adapter.setArray(app);
        Button btn = (Button)findViewById(R.id.btn);
        EditText edt = (EditText) findViewById(R.id.edtTxt);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        btn.setOnClickListener(new SimpleListner((TodoAdaptor)adapter, app, edt));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }
}

class SimpleListner implements View.OnClickListener{
    private EditText edt;
    private Context context;
    private TodoAdaptor adapter;

    public SimpleListner(TodoAdaptor adapter, Context context, EditText edt){
        this.adapter = adapter;
        this.edt = edt;
        this.context = context;
    }
    public void onClick(View view){
        String txt = edt.getText().toString();
        if(txt==null || txt.isEmpty())
        {
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "You can't create an empty task", duration).show();
            return;
        }

        adapter.addTask(txt, context);
        edt.getText().clear();
    }
}
