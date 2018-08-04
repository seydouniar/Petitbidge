package com.budgetemprunt.seydou.petitbidge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;

public class MainLogin extends FragmentActivity implements LoginDialog.OnButtonClickedListener{
    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment;
    FragmentTransaction ft;
    SessionManager session;

    static final String LOGIN = "login";
    static final String PASS = "pass";
    static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        session= new SessionManager(getApplicationContext());

        HashMap<String,String> user = session.getUserDetails();
        String login = user.get(SessionManager.KEY_LOGIN);
        String strId = user.get(SessionManager.KEY_ID);
        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.fragment_login);

        if(fragment == null){
            ft = fm.beginTransaction();
            ft.add(R.id.fragment_login,new LoginDialog());
            ft.commit();
        }
    }

    @Override
    public void onButtonClicked() {
        if(fragment == null){
            ft = fm.beginTransaction();
            ft.replace(R.id.fragment_login, new SubscribDialog());
            ft.addToBackStack(null);
            ft.commit();
        }
    }


}
