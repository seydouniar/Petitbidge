package com.budgetemprunt.seydou.petitbidge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class MaBaseSQLite extends SQLiteOpenHelper {

    private static final String TABLE_ARGENT = "table_argent";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "nom";
    private static final String COL_MONTANT = "montant";
    private static final String COL_DATE = "date";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ARGENT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NOM + " TEXT NOT NULL, "
            + COL_MONTANT + " REAL , "+COL_DATE+ " TEXT NOT NULL);";

    public MaBaseSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_ARGENT + ";");
        onCreate(db);
    }
}
