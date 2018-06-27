package com.budgetemprunt.seydou.petitbidge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * TODO: document your custom view class.
 */
@SuppressLint("ValidFragment")
public class MyDialog extends DialogFragment {
    EditText edtNom;
    EditText edtMontant ;
    EditText edtDate ;
    CallBackDialog listner;

    public Argent getArgent() {
        return argent;
    }

    public boolean isEditOpion() {
        return editOpion;
    }

    private Argent argent;
    private boolean editOpion;

    public MyDialog(){

    }

    public MyDialog(boolean editOpion){
        this.editOpion = editOpion;
    }
    public MyDialog(Argent argent,boolean editOpion){
        this.argent=argent;
        this.editOpion = editOpion;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listner = (CallBackDialog)getTargetFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(getTargetFragment().toString()+ " doit implementé Callbackdialog");
        }

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog,null,false);
        edtNom = view.findViewById(R.id.name);
        edtMontant=view.findViewById(R.id.montant);
        edtDate= view.findViewById(R.id.date);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePicker datepiker = new DatePicker(getDialog().getContext());
                datepiker.setId(R.id.datepicker);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getDialog().getContext());
                builder.setCancelable(false).setView(datepiker);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int jour = datepiker.getDayOfMonth();
                        int mois = datepiker.getMonth() + 1;
                        int annee = datepiker.getYear();
                        if (jour < 10 && mois < 10)
                            edtDate.setText("0" + jour + "/0" + mois + "/" + annee);
                        else if (jour < 10)
                            edtDate.setText("0" + jour + "/" + mois + "/" + annee);
                        else if (mois < 10)
                            edtDate.setText(jour + "/0" + mois + "/" + annee);
                        else edtDate.setText(jour + "/" + mois + "/" + annee);
                        dialog.cancel();
                    }
                }).setNegativeButton("Annule", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });
        builder.setView(view).setPositiveButton("valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                edtNom.setError(null);
                edtDate.setError(null);
                edtMontant.setError(null);

                boolean cancel = false;
                View focusView = null;

                String nom = edtNom.getText().toString();
                String montant = edtMontant.getText().toString();
                String date = edtDate.getText().toString();

                if(TextUtils.isEmpty(nom)){
                    edtNom.setError("Nom est vide!");
                    focusView = edtNom;
                    cancel = true;
                }else if(!isValidNom(nom)){
                    edtNom.setError("Nom invalide!");
                    focusView = edtNom;
                    cancel = true;
                }

                if(TextUtils.isEmpty(montant)){
                    edtMontant.setError("Montant est vide!");
                    focusView = edtMontant;
                    cancel = true;
                }else if(!isValidMontant(montant)){
                    edtMontant.setError("montant invalide!");
                    focusView = edtMontant;
                    cancel = true;
                }

                if(TextUtils.isEmpty(date)){
                    edtDate.setError("date est vide!");
                    focusView = edtDate;
                    cancel = true;
                }else if(!isValidDate(date)){
                    edtDate.setError("date invalide!");
                    focusView = edtDate;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    if(editOpion){
                        listner.editValue(argent,nom,montant,date);
                    }else {
                        listner.getValues(nom,montant,date);
                    }

                }

            }


        })
                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });



        return builder.create();
    }

    public interface CallBackDialog{
        public void getValues(String nom,String montant,String date);
        public void editValue(Argent argent,String nom,String montant,String date);
    }


    public boolean isValidNom(String nom){
        return nom.length()>3;
    }
    public boolean isValidMontant(String montant){
        return Double.parseDouble(montant)!=0;
    }
    public boolean isValidDate(String date){
        if (date.contains("/")){
            String[] values = date.split("/");
            if(values.length!=3) return false;

            Log.i("dateValues",values[0]+"/"+values[1]+"/"+values[2]);
            if((Integer.parseInt(values[2])<2010)||(Integer.parseInt(values[2])>2060)){
                return false;
            }
            if((Integer.parseInt(values[1])>12)||(Integer.parseInt(values[1])<1)){
                return false;
            }
            if((Integer.parseInt(values[0])>31)||(Integer.parseInt(values[0])<=0)){
                return false;
            }

        }
        return true;
    }

    public EditText getEdtNom() {
        return edtNom;
    }

    public void setEdtNom(EditText edtNom) {
        this.edtNom = edtNom;
    }

    public EditText getEdtMontant() {
        return edtMontant;
    }

    public void setEdtMontant(EditText edtMontant) {
        this.edtMontant = edtMontant;
    }

    public EditText getEdtDate() {
        return edtDate;
    }

    public void setEdtDate(EditText edtDate) {
        this.edtDate = edtDate;
    }



}
