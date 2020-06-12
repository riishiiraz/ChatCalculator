package com.riishiiraz.chatcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final String DB_NAME="ChatDatabase.db";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="Chats";
    private static final String KEY_ID="ID";
    private static final String KEY_MSG="Messeges";
    private static final String KEY_SENDER="Senders";
    private static final String KEY_Time="Time";

    private final String CREATE_TABLE_CODE="CREATE TABLE "+TABLE_NAME+"( "+KEY_ID+" INTEGER PRIMARY KEY ,"+KEY_MSG+" TEXT ,"+KEY_SENDER+" TEXT ,"+KEY_Time+" TEXT)";



    public DataBaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CODE);
        Toast.makeText(MainActivity.mainActivity ,"Database Created",Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void addMessege(Messege messege)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_MSG ,messege.getText());
        values.put(KEY_SENDER ,messege.getSender());
        values.put(KEY_Time , messege.getTime());
        db.insert(TABLE_NAME , null , values);
        db.close();
    }

    public Messege getMessege(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME , new String[]{KEY_ID , KEY_MSG ,KEY_SENDER ,KEY_Time},
                KEY_ID +"=?",new String[]{String.valueOf(id)},
                null ,
                null,
                null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }


        Messege messege = new Messege(Integer.parseInt(cursor.getString(0)) , cursor.getString(1) , cursor.getString(2) , cursor.getString(3));
        //Log.v("CODE",CREATE_TABLE_CODE);
        Log.e("Got",String.valueOf(id));
        return messege;
    }

    public ArrayList<Messege> getAllMesseges()
    {
        ArrayList<Messege> messeges = new ArrayList<Messege>();

        String SELECT_QUERRY = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERRY,null );

        if(cursor.moveToFirst())
        {
            do{
                messeges.add(new Messege(Integer.parseInt(cursor.getString(0))  ,cursor.getString(1),cursor.getString(2) , cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return messeges;
    }


}
