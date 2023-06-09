package com.kaya.gestionetudiants.jdbc.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.GsonBuilder;
import com.kaya.gestionetudiants.jdbc.dao.Imp.EtudiantDaoImpl;
import com.kaya.gestionetudiants.jdbc.entities.Etudiant;

import java.io.*;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;




public class EtudiantService {
     EtudiantDaoImpl etudiantDao=new EtudiantDaoImpl();

  public void ajouterEtudiant(Etudiant etudiant){
      etudiantDao.ajouterEtudiant(etudiant);
  }
  public void supprimerEtudiant(int matricule){
      etudiantDao.supprimerEtudiant(matricule);  }
    public void modifierEtudiant(Etudiant etudiant){
      etudiantDao.modifierEtudiant(etudiant);

    }
    public Etudiant trouverEtudiantParId(int id){
     return etudiantDao.trouverEtudiantParId(id);

    }
    public List<Etudiant> trouverTousLesEtudiants() {
        return etudiantDao.trouverTousLesEtudiants();
    }

    public void importFromText(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    String[] values = line.split("-");
                    int matricule = Integer.parseInt(values[0]);
                    System.out.println(matricule);
                    String nom = values[1];
                    System.out.println(nom);
                    String prenom = values[2];
                    System.out.println(prenom);
                    int age = Integer.parseInt(values[3]);
                    System.out.println(age);
                    Etudiant etudiant = new Etudiant(nom ,prenom ,age ,matricule);
                    System.out.println(etudiant);
                    if(this.trouverEtudiantParId(matricule)==null)
                    this.ajouterEtudiant(etudiant);
                    else this.modifierEtudiant(etudiant);
                }
                catch (NumberFormatException e){
                    //System.err.println("err");
                }


               // etudiantDao.ajouterEtudiant(etudiant);
            }

            reader.close();

            System.out.println("Data imported from text file successfully.");
        } catch (IOException e) {
            System.err.println("Error importing data from text file: " + e.getMessage());
        }
    }
    public void exportToText(String filePath) {
        try {
            List<Etudiant> etudiantes = etudiantDao.trouverTousLesEtudiants();

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

            for (Etudiant etudiant : etudiantes) {
                String sb = etudiant.getMatricule() + "," +
                             etudiant.getNom() + "," +
                             etudiant.getPrenom() + "," +
                             etudiant.getAge() + "\n";
                writer.write(sb);
            }

            writer.close();

            System.out.println("Data exported to text file successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting data to text file: " + e.getMessage());
        }
    }

    public void importFromExcel(String filePath)

    {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                int matricule = (int) row.getCell(0).getNumericCellValue();
                String nom = row.getCell(1).getStringCellValue();
                String prenom = row.getCell(2).getStringCellValue();
                int age = (int) row.getCell(3).getNumericCellValue();


                Etudiant etudiant = new Etudiant(nom,prenom,age,matricule);

                etudiantDao.ajouterEtudiant(etudiant);
            }

            fis.close();

            System.out.println("Data imported from Excel successfully.");
        } catch (IOException e) {
            System.err.println("Error importing data from Excel: " + e.getMessage());
        }
    }
    public void exportToExcel(String filePath) {
        try {
            List<Etudiant> etudiantes = etudiantDao.trouverTousLesEtudiants();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Etudiantes");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("matricule");
            headerRow.createCell(1).setCellValue("nom");
            headerRow.createCell(2).setCellValue("prenom");
            headerRow.createCell(3).setCellValue("age");


            int rowNum = 1;
            for (Etudiant etudiant : etudiantes) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(etudiant.getMatricule());
                dataRow.createCell(1).setCellValue(etudiant.getNom());
                dataRow.createCell(2).setCellValue(etudiant.getPrenom());
                dataRow.createCell(3).setCellValue(etudiant.getAge());

            }

            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);

            fos.close();

            System.out.println("Data exported to Excel successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting data to Excel: " + e.getMessage());
        }
    }
    public void importFromJson(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Etudiant> etudiants = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Etudiant>>() {});

            for (Etudiant etudiant : etudiants) {
               etudiantDao.ajouterEtudiant(etudiant);
            }

            System.out.println("Data imported from JSON: " + jsonFilePath);
        } catch (IOException e) {
            System.err.println("Error importing data from JSON: " + e.getMessage());
        }
    }
    public void exportToJson(String filePath) {
        try {
            List<Etudiant> etudiants = etudiantDao.trouverTousLesEtudiants();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(etudiants);

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(json);
            writer.close();

            System.out.println("Data exported to JSON file successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting data to JSON file: " + e.getMessage());
        }
    }

  }






