package com.budgetemprunt.seydou.petitbidge;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


@SuppressLint("ValidFragment")
public class DialogPayer extends DialogFragment {

    EditText editNom;
    EditText editMontant;
    EditText editMontantValue;
    EditText editDate;
    CheckBox toutpaye;
    Button annule;
    Button payer;
    private Argent argent;
    OnValidePayement mlistner;


    public DialogPayer(Argent argent) {
        this.argent=argent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistner= (OnValidePayement)getTargetFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(getTargetFragment().toString()+"must be implement OnValidePayement");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialog_payer,null,false);
        editDate = (EditText)v.findViewById(R.id.pdate);
        editMontant= (EditText)v.findViewById(R.id.Pmontant);
        editMontantValue = (EditText)v.findViewById(R.id.PEditmontant);
        editNom = (EditText)v.findViewById(R.id.Pname);
        toutpaye = (CheckBox)v.findViewById(R.id.Ptout);
        editNom.setText(argent.getNom());
        editMontant.setText(argent.getMontant().toString());
        editDate.setText(argent.getDate());

        toutpaye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(toutpaye.isChecked()){
                    editMontantValue.setEnabled(false);
                    editMontantValue.setText(String.valueOf(argent.getMontant()));
                    editMontant.setText("0");
                }else {
                    editMontantValue.setEnabled(true);
                }
            }
        });

        editMontantValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    double apaye = argent.getMontant()>Double.parseDouble(s.toString())?argent.getMontant() - Double.parseDouble(s.toString()):0;
                    editMontant.setText(String.valueOf(apaye));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        payer = (Button)v.findViewById(R.id.btn_payer);
        annule = (Button)v.findViewById(R.id.Pannule);
        payer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String montant = editMontantValue.getText().toString();
                String nom = editNom.getText().toString();
                String montantOld = editMontant.getText().toString();
                mlistner.onValid(argent,nom,montant,montantOld);
                dismiss();
            }
        });
        annule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    public interface OnValidePayement {
        // TODO: Update argument type and name
        void onValid(Argent argent,String nom,String montant,String montantOld);
    }
}
