package com.ades.pulsaku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Transaksi extends AppCompatActivity {

    ListView listView;
    dbcon d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        d=new dbcon(this);
        final Context c=this;
        load();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                Cursor cursor=d.selectedBarang(id);
                final String sendnama=cursor.getString(1);
                String sendharga=cursor.getString(2);
                final String sendid=cursor.getString(0);
                String sendstock=cursor.getString(3);
                LayoutInflater layoutInflater=LayoutInflater.from(c);
                View mView =layoutInflater.inflate(R.layout.layouttransaksi,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final TextView nama=(TextView) mView.findViewById(R.id.txttr1);
                nama.setText(sendnama);
                final TextView harga=(TextView) mView.findViewById(R.id.txttr2);
                harga.setText(sendharga);
                final EditText jumlah=(EditText)mView.findViewById(R.id.edtjumlah);

                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       d.insertDatatransaksi(nama.getText().toString(),Integer.parseInt(harga.getText().toString()),
                               Integer.parseInt(jumlah.getText().toString()),Integer.parseInt(jumlah.getText().toString())*Integer.parseInt(harga.getText().toString()));
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Tambah");
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
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(Transaksi.this, R.layout.layoutbarang, cursor, from, to);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.listTransaksi2);
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bayar) {
            Intent intent=new Intent(getApplicationContext(),listTransaksi.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
