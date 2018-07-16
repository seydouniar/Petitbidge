package com.budgetemprunt.seydou.petitbidge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArgentBD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "petitbuget.db";

    //argent table
    private static final String TABLE_ARGENT= "table_argent";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ID_USER = "userid";
    private static final String COL_NOM = "nom";
    private static final int NUM_COL_NOM = 1;
    private static final String COL_MONTANT = "montant";
    private static final int NUM_COL_MONTANT = 2;
    private static final String COL_DATE = "date";
    private static final int NUM_COL_DATE = 3;

    //user table
    private static final String TABLE_USER= "user_table";
    private static final String COL_USER_ID = "ID";
    private static final int NUM_COL_USER_ID = 0;

    private static final String USER_MAIL= "mail";
    private static final int NUM_USER_MAIL = 1;
    private static final String USER_PASS= "pass";
    private static final int NUM_USER_PASS = 2;

    //historique
    private static final String TAB_HIST = "historiques";
    private static final String ID_HIST = "ID";
    private static final String ID_USER_HIST = "id_user";
    private static final String ACTION = "action_v";


    private SQLiteDatabase bdd;

    private MaBaseSQLite maBaseSQLite;

    public ArgentBD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public long insertHist(int id, String action){
        ContentValues values = new ContentValues();
        values.put(ID_USER_HIST,id);
        values.put(ACTION,action);
        return bdd.insert(TAB_HIST,null,values);
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
        values.put(COL_ID_USER,0);
        values.put(COL_NOM, argent.getNom());
        values.put(COL_MONTANT,argent.getMontant());
        values.put(COL_DATE,argent.getDate());

        return bdd.insert(TABLE_ARGENT,null,values);
    }

    public long insertArgent(Argent argent,int id){
        ContentValues values = new ContentValues();
        values.put(COL_ID_USER,id);
        values.put(COL_NOM, argent.getNom());
        values.put(COL_MONTANT,argent.getMontant());
        values.put(COL_DATE,argent.getDate());

        return bdd.insert(TABLE_ARGENT,null,values);
    }

    //insert user
    public long insertUser(User user){
        ContentValues values = new ContentValues();

        values.put(USER_MAIL, user.getMail());
        values.put(USER_PASS, user.getPass());

        return bdd.insert(TABLE_USER,null,values);
    }

    public List<Historique> getHistoriques(int id){
        String rows = TABLE_ARGENT+"."+COL_NOM+","
                + TABLE_ARGENT+"."+COL_MONTANT+","
                + TABLE_ARGENT+"."+COL_DATE+","
                +TAB_HIST+"."+ACTION;
        String req = "select "+rows+" from "+ TABLE_ARGENT+ ","+TAB_HIST+ " where "
                + TABLE_ARGENT+"."+COL_ID_USER+" = "+id+" and "
                + TAB_HIST+"."+ID_USER_HIST+" = "+ id;
        Cursor c = bdd.rawQuery(req,null);
        return cursorToListHist(c);
    }

    public long deleteUser(int id){
        return bdd.delete(TABLE_USER,COL_USER_ID + "=" +id,null);
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


    public List getArgentAll(int id){
        Cursor c = bdd.query(TABLE_ARGENT,new String[] {COL_ID,COL_NOM,COL_MONTANT,COL_DATE },COL_ID_USER+" = ?",
                new String[]{String.valueOf(id)},null,null,null,null);
        return cursorToListArgent(c);
    }

    public List getUsers(){
        Cursor c = bdd.query(TABLE_USER,new String[]{COL_USER_ID,USER_MAIL,USER_PASS},null,null,null,null,null);
        return cursotoUsers(c);
    }



    private  List cursotoUsers(Cursor c){
        List listusers = new ArrayList<User>();
        if (c.getCount() <0)
            return Collections.<User>emptyList();
        c.moveToFirst();
        while (!c.isAfterLast()){
            User user = new User();
            user.setId(c.getInt(NUM_COL_USER_ID));
            user.setMail(c.getString(NUM_USER_MAIL));
            user.setPass(c.getString(NUM_USER_PASS));
            listusers.add(user);

            c.moveToNext();
        }
        return listusers;
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
            c.moveToNext();
        }
        c.close();
        return listArgent;
    }
    private List<Historique> cursorToListHist(Cursor c){
        List listHists = new ArrayList<Historique>();
        if (c.getCount() <0)
            return Collections.<Historique>emptyList();
        c.moveToFirst();

        while (!c.isAfterLast()){
            Historique a = new Historique();
            a.setNom(c.getString(0));
            a.setMontant(c.getDouble(1));
            a.setDate(c.getString(2));
            a.setAction(c.getString(3));
            Log.i("histinbd",a.toString());
            listHists.add(a);
            c.moveToNext();
        }
        c.close();
        return listHists;
    }
}
