package com.kaya.gestionetudiants.jdbc.entities;


import java.io.Serializable;
import java.util.Objects;

public class Etudiant implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom;
    private String prenom;
    private int age;
    private int matricule;

    // Constructeur avec paramètres
    public Etudiant(String nom, String prenom, int age, int matricule) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.matricule = matricule;
    }

    // Constructeur sans paramètres
    public Etudiant() {
        this.nom = "";
        this.prenom = "";
        this.age = 0;
        this.matricule = 0;
    }

    // Accesseurs
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    // Redéfinition de la méthode toString
    @Override
    public String toString() {
        return "Etudiant{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", matricule='" + matricule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etudiant etudiant = (Etudiant) o;
        return age == etudiant.age && matricule == etudiant.matricule && Objects.equals(nom, etudiant.nom) && Objects.equals(prenom, etudiant.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom, prenom, age, matricule);
    }
}
