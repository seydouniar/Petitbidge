package com.budgetemprunt.seydou.petitbidge;


import java.sql.Date;

public class Argent {
    private int id;
    private String nom;
    private double montant;
    private String date;

    public Argent() {
    }

    public Argent(String nom, Double montant, String date) {
        this.nom = nom;
        this.montant = montant;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return ", Nom :" + nom +"  " +
                ", Montant :=" + montant +
                ", Date=" + date ;
    }
}
