package com.example.sqlite_1.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sqlite_1.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION =1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE items("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "title TEXT,category TEXT,price TEXT,date TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    public List<Item> getAll(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order ="date DESC";
        Cursor rs = st.query("item",null,null,null,null,null,order);
        while(rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title= rs.getString(1);
            String c= rs.getString(2);
            String p= rs.getString(3);
            String date= rs.getString(4);
            list.add(new Item(id,title,c,p,date));
        }
        return list;
    }

    public long addItem(@NonNull Item i){
        ContentValues values = new ContentValues();
        values.put("title",i.getTitle());
        values.put("category",i.getCategory());
        values.put("price",i.getPrice());
        values.put("date",i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("item",null,values);
    }
    public List<Item>getByDate(String date){
        List<Item> list = new ArrayList<>();
        String whereClause = "date like?";
        String[] whereARGS = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null,whereClause,whereARGS, null, null, null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title= rs.getString(1);
            String c= rs.getString(2);
            String p= rs.getString(3);

            list.add(new Item(id,title,c,p,date));
        }
        return list;
    }

}
