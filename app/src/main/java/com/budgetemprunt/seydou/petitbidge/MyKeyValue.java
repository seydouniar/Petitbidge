package com.budgetemprunt.seydou.petitbidge;

import android.graphics.Bitmap;

public class MyKeyValue {

    private int valeur;
    private String StrValue;
    private Bitmap icon;

    public MyKeyValue(){
        this.valeur = 0;
        this.StrValue = null;
    }
    public MyKeyValue(int valeur){
        this.valeur = valeur;
        this.StrValue = String.valueOf(this.valeur);
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
    public Bitmap getIcon() {
        return icon;
    }
}
