package com.budgetemprunt.seydou.petitbidge;

public class Historique {
    private String nom;
    private double montant;
    private String date;
    private String action;

    public Historique() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Historique{" +
                "nom='" + nom + '\'' +
                ", montant=" + montant +
                ", date='" + date + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}
