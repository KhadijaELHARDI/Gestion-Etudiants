package com.kaya.gestionetudiants.jdbc.dao;

import com.kaya.gestionetudiants.jdbc.entities.Etudiant;

import java.util.List;

public interface EtudiantDao  {
    // Méthode pour ajouter un étudiant à la base de données
    public void ajouterEtudiant(Etudiant etudiant);

    // Méthode pour mettre à jour les informations d'un étudiant dans la base de données
    public void modifierEtudiant(Etudiant etudiant);

    // Méthode pour supprimer un étudiant de la base de données
    public void supprimerEtudiant(int matricule);

    // Méthode pour récupérer un étudiant de la base de données par son ID
    public Etudiant trouverEtudiantParId(int id);

    // Méthode pour récupérer tous les étudiants de la base de données
    public List<Etudiant> trouverTousLesEtudiants();

    ;
}
