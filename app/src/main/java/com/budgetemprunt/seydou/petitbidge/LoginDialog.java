package com.budgetemprunt.seydou.petitbidge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class LoginDialog extends DialogFragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog,null,false);
        final EditText Editlogin = (EditText)view.findViewById(R.id.login);
        final EditText Editpass = (EditText)view.findViewById(R.id.login);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Login")
                .setView(view)
                .setPositiveButton("Connecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String login = Editlogin.getText().toString();
                        String pass = Editpass.getText().toString();
                    }
                })
                .setNegativeButton("s'inscrire", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
