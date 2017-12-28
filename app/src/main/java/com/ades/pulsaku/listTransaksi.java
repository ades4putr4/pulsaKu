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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class listTransaksi extends AppCompatActivity {

    ListView listView;
    dbcon d;
    Button bayar;
    final Context c=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_transaksi);
        bayar=(Button)findViewById(R.id.btnbayar);
        d=new dbcon(this);
        load();

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater=LayoutInflater.from(c);
                View mView =layoutInflater.inflate(R.layout.activity_pembayaran,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final TextView jumlah=(TextView) mView.findViewById(R.id.txtpj);
                final TextView total=(TextView) mView.findViewById(R.id.txtpt);

                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Bayar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(c,"Terbayar",Toast.LENGTH_LONG).show();
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
            cursor = d.readTransaksi();
        } catch (Exception e) {
            Toast.makeText(this,"salah",Toast.LENGTH_LONG).show();
        }
        String[] from = new String[]{"nama", "harga", "jumlah", "total"};
        int[] to = new int[]{R.id.txtlt1, R.id.txtlt2, R.id.txtlt3,R.id.txtlt4};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(listTransaksi.this, R.layout.listtransaksi, cursor, from, to);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.listTransaksi);
        listView.setAdapter(adapter);
    }
}
