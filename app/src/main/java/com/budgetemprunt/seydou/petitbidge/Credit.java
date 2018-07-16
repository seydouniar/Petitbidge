package com.budgetemprunt.seydou.petitbidge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


public class Credit extends Fragment {
    TextView tv ;
    SessionManager session;

    public Credit() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_credit, container, false);
        tv = (TextView)v.findViewById(R.id.texcredit);

        session =new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        String login = user.get(SessionManager.KEY_LOGIN);
        String strId = user.get(SessionManager.KEY_ID);
        tv.setText(login+strId);
        return v;
    }



}
