package com.example.tp6;

import java.util.Comparator;

public class POJO {
    private String nom;

    public POJO(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "POJO{" +
                "nom='" + nom + '\'' +
                '}';
    }

    public static Comparator<POJO> PojoNameComparator = new Comparator<POJO>() {
        @Override
        public int compare(POJO o1, POJO o2) {
            return o1.getNom().compareTo(o2.getNom());
        }
    };
}
