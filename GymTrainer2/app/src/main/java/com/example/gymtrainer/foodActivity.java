package com.example.gymtrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class foodActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        String[]tstory=getResources().getStringArray(R.array.title_story);
        final String[]dstory=getResources().getStringArray(R.array.details_story);


        listView=findViewById(R.id.list);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.row,R.id.rowtext,tstory);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String t = dstory[position];
                Intent intent=new Intent(foodActivity.this, foodActivityDetails.class);
                intent.putExtra("story",t);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void foodgoback(View view) {

        Intent intent=new Intent(foodActivity.this,homepage.class);
        startActivity(intent);
        finish();

    }

}
