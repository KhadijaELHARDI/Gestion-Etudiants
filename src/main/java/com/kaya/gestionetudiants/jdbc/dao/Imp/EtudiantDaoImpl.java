package com.kaya.gestionetudiants.jdbc.dao.Imp;

import com.kaya.gestionetudiants.jdbc.dao.EtudiantDao;
import com.kaya.gestionetudiants.jdbc.entities.Etudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDaoImpl implements EtudiantDao {
    private Connection conn= DB.getConnection();
    @Override
    public void ajouterEtudiant(Etudiant etudiant) {
        try {
            // Préparer la requête d'insertion
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO etudiant(nom, prenom, age) VALUES (?, ?, ?)");

            // Définir les paramètres de la requête d'insertion
            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setInt(3, etudiant.getAge());


            // Exécuter la requête d'insertion
            preparedStatement.executeUpdate();

            // Fermer la requête préparée
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void modifierEtudiant(Etudiant etudiant) {
        try {
            // Préparer la requête de mise à jour
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE etudiant SET nom=?, prenom=?, age=? WHERE matricule=?");

            // Définir les paramètres de la requête de mise à jour
            preparedStatement.setString(1, etudiant.getNom());
            preparedStatement.setString(2, etudiant.getPrenom());
            preparedStatement.setInt(3, etudiant.getAge());
            preparedStatement.setInt(4, etudiant.getMatricule());


            // Exécuter la requête de mise à jour
            preparedStatement.executeUpdate();

            // Fermer la requête préparée
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void supprimerEtudiant(int matricule) {
        try {
            // Préparer la requête de suppression
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM etudiant WHERE matricule=?");

            // Définir les paramètres de la requête de suppression
            preparedStatement.setInt(1, matricule);

            // Exécuter la requête de suppression
            preparedStatement.executeUpdate();

            // Fermer la requête préparée
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Etudiant trouverEtudiantParId(int matricule) {
        Etudiant etudiant = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM etudiant WHERE matricule = ?");
            preparedStatement.setInt(1, matricule);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                etudiant = new Etudiant();
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setAge(resultSet.getInt("age"));
                etudiant.setMatricule(resultSet.getInt("matricule"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiant;


    }

    @Override
    public List<Etudiant> trouverTousLesEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM etudiant");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Etudiant etudiant = new Etudiant();
                etudiant.setNom(resultSet.getString("nom"));
                etudiant.setPrenom(resultSet.getString("prenom"));
                etudiant.setAge(resultSet.getInt("age"));
                etudiant.setMatricule(resultSet.getInt("matricule"));
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }





}
