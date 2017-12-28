package com.ades.pulsaku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class inputDataBarang extends AppCompatActivity {

    dbcon d;
    EditText nama,harga,stock;
    Button cancel,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_barang);
        nama=(EditText)findViewById(R.id.edtnamabarang);
        harga=(EditText)findViewById(R.id.edthargaBarang);
        stock=(EditText)findViewById(R.id.edtstock);
        cancel=(Button) findViewById(R.id.btnCancel);
        save=(Button) findViewById(R.id.btnSave);

        d=new dbcon(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.insertDataBarang(nama.getText().toString(),Integer.parseInt(harga.getText().toString()),Integer.parseInt(stock.getText().toString()));
                Intent intent=new Intent(getApplicationContext(),home.class);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),home.class);
                startActivity(intent);

            }
        });
    }
}
