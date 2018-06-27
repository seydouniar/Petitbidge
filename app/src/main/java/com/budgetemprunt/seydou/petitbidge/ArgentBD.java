package com.budgetemprunt.seydou.petitbidge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.budgetemprunt.seydou.petitbidge.Argent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgentBD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "petitbuget.db";

    private static final String TABLE_ARGENT= "table_argent";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NOM = "nom";
    private static final int NUM_COL_NOM = 1;
    private static final String COL_MONTANT = "montant";
    private static final int NUM_COL_MONTANT = 2;
    private static final String COL_DATE = "date";
    private static final int NUM_COL_DATE = 3;

    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public ArgentBD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertArgent(Argent argent){
        ContentValues values = new ContentValues();
        values.put(COL_NOM, argent.getNom());
        values.put(COL_MONTANT,argent.getMontant());
        values.put(COL_DATE,argent.getDate());

        return bdd.insert(TABLE_ARGENT,null,values);
    }

    public long updateArgent(int id,Argent argent){
        ContentValues values = new ContentValues();
        values.put(COL_NOM, argent.getNom());
        values.put(COL_MONTANT,argent.getMontant());
        values.put(COL_DATE,argent.getDate());
        return bdd.update(TABLE_ARGENT,values,COL_ID + " = " +id,null);
    }

    public long deleteArgent(int id){
        return bdd.delete(TABLE_ARGENT,COL_ID + " = " +id,null);
    }

    public List getArgentWithNom(String nom){
        Cursor c = bdd.query(TABLE_ARGENT,new String[] {COL_ID,COL_NOM,COL_MONTANT,COL_DATE },COL_NOM + " LIKE \"" +nom+ "\"",null,null,null,null);
        return cursorToListArgent(c);
    }

    public List getArgentAll(){
        Cursor c = bdd.query(TABLE_ARGENT,new String[] {COL_ID,COL_NOM,COL_MONTANT,COL_DATE },null,null,null,null,null);
        return cursorToListArgent(c);
    }

    private List cursorToListArgent(Cursor c) {
        List listArgent = new ArrayList<Argent>();
        if (c.getCount() <0)
            return Collections.<Argent>emptyList();
        c.moveToFirst();
        while (!c.isAfterLast()){
            Argent a = new Argent();
            a.setId(c.getInt(NUM_COL_ID));
            a.setNom(c.getString(NUM_COL_NOM));
            a.setMontant(c.getDouble(NUM_COL_MONTANT));
            a.setDate(c.getString(NUM_COL_DATE));
            listArgent.add(a);
            Log.i("Argent",a.toString());
            c.moveToNext();
        }
        c.close();
        return listArgent;
    }
}