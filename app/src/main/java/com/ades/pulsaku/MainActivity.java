package com.ades.pulsaku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView inputbarang, listbarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputbarang=(ImageView)findViewById(R.id.imageView);
        listbarang=(ImageView)findViewById(R.id.imageView2);
        inputbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),inputDataBarang.class);
                startActivity(intent);

            }
        });
        listbarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),listBarang.class);
                startActivity(intent);

            }
        });
    }

}
