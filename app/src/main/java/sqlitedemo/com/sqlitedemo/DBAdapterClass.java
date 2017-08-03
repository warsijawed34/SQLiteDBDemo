package sqlitedemo.com.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by JAWED on 03-07-2017.
 */

public class DBAdapterClass {
    DBOpenHelperClass dbOpenHelperClass;

    public DBAdapterClass(Context mContext){
       dbOpenHelperClass=new DBOpenHelperClass(mContext);
    }
    //insert single data
    public long insertData(String name, String phone, String address, String email,  String password){
        SQLiteDatabase sqLiteDatabase=dbOpenHelperClass.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBOpenHelperClass.NAME, name);
        contentValues.put(DBOpenHelperClass.PHONE, phone);
        contentValues.put(DBOpenHelperClass.ADDRESS, address);
        contentValues.put(DBOpenHelperClass.EMAIL, email);
        contentValues.put(DBOpenHelperClass.PASSWORD, password);
        long id=  sqLiteDatabase.insert(DBOpenHelperClass.TABLE_NAME, null, contentValues);
        return id;
    }
    //get all details
    public ArrayList<UserDetails>  getAllData(){
        SQLiteDatabase sqLiteDatabase=dbOpenHelperClass.getWritableDatabase();
        String[] columns={DBOpenHelperClass.UID, DBOpenHelperClass.NAME, DBOpenHelperClass.PHONE, DBOpenHelperClass.ADDRESS,DBOpenHelperClass.EMAIL, DBOpenHelperClass.PASSWORD};
        Cursor cursor=sqLiteDatabase.query(DBOpenHelperClass.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<UserDetails> arrayList=new ArrayList<>();
        UserDetails model;
        while (cursor.moveToNext()){
            int cid=cursor.getInt(0);
            String name=cursor.getString(1);
            String phone=cursor.getString(2);
            String address=cursor.getString(3);
            String email=cursor.getString(4);
            String password=cursor.getString(5);
            model=new UserDetails();
            model.setID(String.valueOf(cid));
            model.setName(name);
            model.setPhone(phone);
            model.setAddress(address);
            model.setEmail(email);
            model.setPassword(password);
            arrayList.add(model);
        }
        return arrayList;
    }
    //pass user name and get some details
    public String getData(String name){
        SQLiteDatabase sqLiteDatabase=dbOpenHelperClass.getWritableDatabase();
        String[] columns={DBOpenHelperClass.NAME, DBOpenHelperClass.PASSWORD};
        Cursor cursor=sqLiteDatabase.query(DBOpenHelperClass.TABLE_NAME, columns, DBOpenHelperClass.NAME+" = '"+name+"'", null, null, null, null);
        StringBuffer buffer=new StringBuffer();
        while (cursor.moveToNext()){
           int index1=cursor.getColumnIndex(DBOpenHelperClass.NAME);
            int index2=cursor.getColumnIndex(DBOpenHelperClass.PASSWORD);
            String personName=cursor.getString(index1);
            String password=cursor.getString(index2);
            buffer.append(name+" "+password+"\n");
        }
        return buffer.toString();
    }
    //pass username, pass and get id here..
    public String getID(String email, String password){
        SQLiteDatabase sqLiteDatabase=dbOpenHelperClass.getWritableDatabase();
        String[] columns={DBOpenHelperClass.UID};
        String[] selectionArgs={email, password};
        Cursor cursor=sqLiteDatabase.query(DBOpenHelperClass.TABLE_NAME, columns,
                DBOpenHelperClass.EMAIL+" =? AND " +DBOpenHelperClass.PASSWORD+" =?", selectionArgs, null, null, null);
        StringBuffer buffer=new StringBuffer();
        while (cursor.moveToNext()){
            int index=cursor.getColumnIndex(DBOpenHelperClass.UID);
            int personId=cursor.getInt(index);
            buffer.append(personId +"\n");
        }
        return buffer.toString();
    }
     //update oldname to new name here...
    public int updateName(String oldNama, String newName){
        SQLiteDatabase sqLiteDatabase=dbOpenHelperClass.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBOpenHelperClass.NAME, newName);
        String[] whereArgs={oldNama};
        int count=sqLiteDatabase.update(DBOpenHelperClass.TABLE_NAME, contentValues, DBOpenHelperClass.NAME+" =?", whereArgs);
        return count;
    }
    //delete row by passing name here..
    public int deleteRow(String name){
        SQLiteDatabase sqLiteDatabase=dbOpenHelperClass.getWritableDatabase();
        String[] whereArgs={name};
        int count=sqLiteDatabase.delete(DBOpenHelperClass.TABLE_NAME, DBOpenHelperClass.NAME+" =?", whereArgs);
        return count;
    }

   static class DBOpenHelperClass extends SQLiteOpenHelper{
       private static final String DATATBASE_NAME="demoDatBase.db";
       private static final String TABLE_NAME="DEMOTABLE";
       private static final int DATABASE_VERSION=9;
       private static final String UID="_id";
       private static final String NAME="name";
       private static final String PHONE="phone";
       private static final String ADDRESS="address";
       private static final String EMAIL="email";
       private static final String PASSWORD ="password";
       private static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME
               +" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255),"
               +PHONE+" VARCHAR(255), "
               +ADDRESS+" VARCHAR(255), "+EMAIL+" VARCHAR(255), "+PASSWORD+" VARCHAR(255));";
       private Context mContext;
       private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
       public DBOpenHelperClass(Context mContext){
           super(mContext, DATATBASE_NAME, null, DATABASE_VERSION);
           this.mContext=mContext;
           Message.message(mContext,"Contructor called");
       }

       @Override
       public void onCreate(SQLiteDatabase db) {

           try {
               db.execSQL(CREATE_TABLE);
               Message.message(mContext,"onCreate called");
           }catch (SQLException e){
               Message.message(mContext,""+e);
           }
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           try {
               Message.message(mContext,"onUpgrade called");
               db.execSQL(DROP_TABLE);
               onCreate(db);
           }catch (SQLException e){
               Message.message(mContext,""+e);
           }
       }
   }

}
