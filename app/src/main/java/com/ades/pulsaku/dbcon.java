package com.ades.pulsaku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class dbcon extends SQLiteOpenHelper {

    static final private String Db_NAME="pulsaKu";
    static final private String ID="_id";
    static final private int Db_VER=8;

    ////deklarasi nama tabel
    static final private String TB_BARANG="Barang";//tabel barang
    static final private String CREATE_TB_BARANG="create table "+TB_BARANG+"(_id integer primary key autoincrement,nama text,stock integer ,harga  integer);"; //tabel barang
    static final private String TB_TRANSAKSI="Transaksi";//tabel transaksi
    static final private String CREATE_TB_TRANSAKSI="create table "+TB_TRANSAKSI+"(_id integer primary key autoincrement,nama text,harga integer,jumlah integer, total integer);";//tabel transaksi


    Context mycontext;
    SQLiteDatabase myDb;

    public dbcon(Context context) {
        super(context, Db_NAME, null, Db_VER);
        mycontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TB_BARANG);
        db.execSQL(CREATE_TB_TRANSAKSI);
        Log.i("Database","Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+TB_BARANG);
            db.execSQL("drop table if exists "+TB_TRANSAKSI);
            onCreate(db);

    }
    public void insertDataBarang(String s0,int s1,int s2){
        myDb=getWritableDatabase();
        myDb.execSQL("insert into "+TB_BARANG+" (nama,harga,stock) values('"+s0+"','"+s1+"','"+s2+"');");
        Toast.makeText(mycontext,"Data Saved",Toast.LENGTH_LONG).show();
    }
    public Cursor readBarang() {
        myDb=getWritableDatabase();
        String[] columns = new String[]{"_id","nama","harga","stock"};
        Cursor c = myDb.query(TB_BARANG, columns, null, null, null, null, ID + " asc");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public void deleteBarang(long id) {
        myDb=getWritableDatabase();
        myDb.delete(TB_BARANG, ID + "=" + id, null);
        close();
    }
    public void update(long id, String s0,int s1,int s2) {
        myDb=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", s0);
        values.put("harga", s1);
        values.put("stock", s2);
        myDb.update(TB_BARANG, values, ID + "=" + id, null);
        close();
    }
    public Cursor selectedBarang(long id) {
        myDb=getWritableDatabase();
        String[] columns = new String[]{"_id","nama", "harga", "stock" };
        Cursor c = myDb.query(TB_BARANG, columns, ID + "=" + id, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void insertDatatransaksi(String d0,int d1,int d2,int d3){
        myDb=getWritableDatabase();
        myDb.execSQL("insert into "+TB_TRANSAKSI+" (nama,harga,jumlah, total) values('"+d0+"','"+d1+"','"+d2+"','"+d3+"');");
        Toast.makeText(mycontext,"Data Saved",Toast.LENGTH_LONG).show();
    }
    public Cursor readTransaksi() {
        myDb=getWritableDatabase();
        String[] columns = new String[]{"_id","nama","harga","jumlah","total"};
        Cursor c = myDb.query(TB_TRANSAKSI, columns, null, null, null, null, ID + " asc");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
}