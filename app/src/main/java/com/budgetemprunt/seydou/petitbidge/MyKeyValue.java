package com.budgetemprunt.seydou.petitbidge;

import android.content.ClipData;
import android.graphics.Bitmap;

public class MyKeyValue {

    private int valeur;
    private String StrValue;


    public MyKeyValue(){
        this.valeur = 0;
        this.StrValue = null;
    }
    public MyKeyValue(int valeur){
        this.valeur = valeur;
        this.StrValue = String.valueOf(this.valeur);

    }

    public MyKeyValue(String valeur){
        this.StrValue = valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public String getStrValue() {
        return StrValue;
    }

    public void setStrValue(String strValue) {
        StrValue = strValue;
    }

    public int getValeur() {
        return valeur;
    }

}
