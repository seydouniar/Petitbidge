package com.budgetemprunt.seydou.petitbidge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME= "testPref";
    private static final String IS_LOGIN = "isLoggedIn";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_ID = "id";

    public SessionManager(Context context) {
        this._context=context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * @param login
     * @param id
     */
    public void createLoginSession(String login,int id){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_LOGIN,login);
        editor.putString(KEY_ID,String.valueOf(id));
        editor.commit();
    }

    /**
     * get Stored session
     */
    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String, String>();
        user.put(KEY_LOGIN,pref.getString(KEY_LOGIN,null));
        user.put(KEY_ID,pref.getString(KEY_ID,null));
        return user;
    }

    /**
     * check login method
     */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context,LoginDialog.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    /**
     * clear session
     */
    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context,LoginDialog.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);

    }
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN,false);
    }
}
