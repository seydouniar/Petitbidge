package com.budgetemprunt.seydou.petitbidge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Compte extends Fragment {


    public Compte() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compte, container, false);
        // Inflate the layout for this fragment
        TextView textView = (TextView)view.findViewById(R.id.txt_compte);
        String login = getArguments().getString("login");
        textView.setText(login);
        return view;
    }

}
