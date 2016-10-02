package com.example.lenovo.savingdataa3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    final String str="database helper";
    public static class TableDef{
            static final String TABLE_NAME="Student";
            static final String ROLL_NO="roll_no";
            static final String NAME="name";
            static final String MARKS="marks";
            static final String CURR_SEM="semester";

    }
    static final String TEXT_TYPE="text",INT_TYPE="integer";
    //static final String COMMA=",";
    static final String CREATE_SQL="CREATE TABLE "+TableDef.TABLE_NAME+" ("
            +TableDef.ROLL_NO+" "+INT_TYPE+" PRIMARY KEY,"
            +TableDef.NAME+" "+TEXT_TYPE+","
            +TableDef.MARKS+" "+INT_TYPE+","
            +TableDef.CURR_SEM+" "+TEXT_TYPE+");";

    static final String DELETE_DB="DROP TABLE IF EXISTS "+TableDef.TABLE_NAME+";";
    static final int VERSION=1;

    public DatabaseHelper(Context context) {
        super(context, TableDef.TABLE_NAME, null, VERSION);  //creates db

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DELETE_DB);
        onCreate(sqLiteDatabase);
    }

    public boolean insert(int roll,String name,int marks, String sem){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableDef.ROLL_NO,roll);
        cv.put(TableDef.NAME,name);
        cv.put(TableDef.MARKS,marks);
        cv.put(TableDef.CURR_SEM,sem);
        long r=db.insert(TableDef.TABLE_NAME,null,cv);
        if (r==-1)
            return false;
        else
            return true;
    }

    public boolean update(int roll,String name,int marks,String sem){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableDef.NAME,name);
        String selection=TableDef.ROLL_NO+"=?";
        String[] selection_arg={Integer.toString(roll)};
        long r1=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        cv=new ContentValues();
        cv.put(TableDef.MARKS,marks);
        long r2=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        cv=new ContentValues();
        cv.put(TableDef.CURR_SEM,sem);
        long r3=db.update(TableDef.TABLE_NAME,cv,selection,selection_arg);

        if(r1>0&&r2>0&&r3>0)
            return true;
        else
            return false;
    }

    public Cursor retrieve(){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] projection={TableDef.ROLL_NO,TableDef.NAME,TableDef.MARKS,TableDef.CURR_SEM};
        Cursor c=db.query(TableDef.TABLE_NAME,projection,null,null,null,null,TableDef.ROLL_NO);
        return c;
    }

    public Cursor retrieveRec(int roll){
        Cursor c=null;
        SQLiteDatabase db=this.getReadableDatabase();
        String[] projection={TableDef.NAME,TableDef.MARKS,TableDef.CURR_SEM};
        Log.d(str, "After projection statement");
        String selection=TableDef.ROLL_NO+"= ? ";
        String[] selection_arg={Integer.toString(roll)};
         c=db.query(TableDef.TABLE_NAME,projection,selection,selection_arg,null,null,null,null);
        Log.d(str, "After query statement");
        return c;
    }

    public boolean delete(int roll){
        SQLiteDatabase db=this.getReadableDatabase();
        String selection=TableDef.ROLL_NO+"= ? ";
        String[] selection_arg={Integer.toString(roll)};
        long r1=db.delete(TableDef.TABLE_NAME, selection, selection_arg);
        if(r1>0)
            return true;
        else
            return  false;
    }


}
