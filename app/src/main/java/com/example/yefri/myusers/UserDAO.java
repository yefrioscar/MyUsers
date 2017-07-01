package com.example.yefri.myusers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by yefri on 29/06/2017.
 */

public class UserDAO {
    private final String TABLE = "USER";
    private final String COL_ID = "id";
    private final String COL_NOMBRE = "nombre";
    private final String COL_APELLIDO = "apellido";
    private final String COL_EDAD = "edad";

    private Context mcontext;

    public UserDAO(Context context) { mcontext = context; }

    public ArrayList<User> listUsers(){
        ArrayList<User> lstUsers = new ArrayList<>();
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(mcontext);
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.openDataBase();
            Cursor cursor = sqLiteDatabase.query(TABLE, null, null, null, null, null, null);
            if(cursor.moveToFirst()){
                do {
                    lstUsers.add(transformarCursorAUser(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstUsers;
    }

    public User transformarCursorAUser(Cursor cursor) {
        User user = new User();

        user.setId(cursor.isNull(cursor.getColumnIndex(COL_ID))? 0 : cursor.getInt(cursor.getColumnIndex(COL_ID)));
        user.setNombre(cursor.isNull(cursor.getColumnIndex(COL_NOMBRE))? "" : cursor.getString(cursor.getColumnIndex(COL_NOMBRE)));
        user.setApellido(cursor.isNull(cursor.getColumnIndex(COL_APELLIDO))? "" : cursor.getString(cursor.getColumnIndex(COL_APELLIDO)));
        user.setEdad(cursor.isNull(cursor.getColumnIndex(COL_EDAD))? 0 : cursor.getInt(cursor.getColumnIndex(COL_EDAD)));

        return user;
    }

    public long addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE,user.getNombre());
        contentValues.put(COL_APELLIDO, user.getApellido());
        contentValues.put(COL_EDAD, user.getEdad());

        return new DataBaseHelper(mcontext)
                .openDataBase()
                .insert(TABLE,null,contentValues);
    }

    public void delete(User user) {
        new DataBaseHelper(mcontext)
                .openDataBase()
                .delete(TABLE,COL_ID + " = ?",
                        new String[]{
                                String.valueOf(user.getId())
                        }
                );
    }

    public void update(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE,user.getNombre());
        contentValues.put(COL_APELLIDO, user.getApellido());
        contentValues.put(COL_EDAD, user.getEdad());

        new DataBaseHelper(mcontext)
                .openDataBase()
                .update(TABLE, contentValues, COL_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }
}
