package com.ades.pulsaku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class listBarang extends AppCompatActivity {


    ListView listView;
    dbcon d;
    final Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);
        d=new dbcon(this);
        load();

        //event listview saat diklik akan muncul popup detil barang
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                Cursor cursor=d.selectedBarang(id);
                final String sendnama=cursor.getString(1);
                String sendharga=cursor.getString(2);
                final String sendid=cursor.getString(0);
                String sendstock=cursor.getString(3);

                LayoutInflater layoutInflater=LayoutInflater.from(c);
                View mView =layoutInflater.inflate(R.layout.updatebarang,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final TextView nama=(EditText) mView.findViewById(R.id.upd1);
                nama.setText(sendnama);
                final TextView harga=(EditText) mView.findViewById(R.id.upd2);
                harga.setText(sendharga);
                final TextView stock=(EditText) mView.findViewById(R.id.upd3);
                stock.setText(sendstock);
                final Button delete=(Button) mView.findViewById(R.id.delete);
                //delete button jika di klik maka akan mendelete data yang dipilih
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.deleteBarang(Long.parseLong(sendid));
                        Intent intent=new Intent(getApplicationContext(),listBarang.class);
                        startActivity(intent);
                    }
                });
                //button update digunakan untuk mengupdate data jika admin ingin mengubah data
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        d.update(Long.parseLong(sendid),nama.getText().toString(),Integer.parseInt(harga.getText().toString()),Integer.parseInt(stock.getText().toString()));
                        load();
                        //button back untuk menghilangkan popup detil barang
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Update");
                alertDialog.show();
            }
        });

    }
    public void load(){
        Cursor cursor = null;
        try {
            cursor = d.readBarang();
        } catch (Exception e) {
            Toast.makeText(this,"salah",Toast.LENGTH_LONG).show();
        }
        String[] from = new String[]{"nama", "harga", "stock"};
        int[] to = new int[]{R.id.txtbarang1, R.id.txtbarang2, R.id.txtbarang3};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(listBarang.this, R.layout.layoutbarang, cursor, from, to);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.listBarang);
        listView.setAdapter(adapter);
    }

}
