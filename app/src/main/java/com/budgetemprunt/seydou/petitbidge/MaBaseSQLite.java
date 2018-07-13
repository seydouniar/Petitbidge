package com.budgetemprunt.seydou.petitbidge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class MaBaseSQLite extends SQLiteOpenHelper {

    //argent colonnes
    private static final String TABLE_ARGENT = "table_argent";
    private static final String COL_ID_USER = "userid";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "nom";
    private static final String COL_MONTANT = "montant";
    private static final String COL_DATE = "date";

    //user colone
    private static final String TABLE_USER= "user_table";
    private static final String COL_USER_ID = "ID";

    private static final String USER_MAIL= "mail";
    private static final String USER_PASS= "pass";







    private static final String CREATE_BDD = "CREATE TABLE "
            + TABLE_ARGENT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_ID_USER + " INTEGER, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_MONTANT + " REAL , "
            + COL_DATE+ " TEXT NOT NULL);";

    private static final String CREATE_USERDB = "CREATE TABLE "
            + TABLE_USER + "("
            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "

            + USER_MAIL+ " TEXT NOT NULL, "
            + USER_PASS+ " TEXT NOT NULL);";


    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
        db.execSQL(CREATE_USERDB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARGENT + ";");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER+";");
        onCreate(db);
    }
}
