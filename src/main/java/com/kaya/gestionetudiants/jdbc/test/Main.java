package com.kaya.gestionetudiants.jdbc.test;

import com.kaya.gestionetudiants.jdbc.services.EtudiantService;

public class Main {
    public static void main(String[] args) {
        EtudiantService etudiantService = new EtudiantService();
        String textFilePath = "src/main/resources/inputData.txt";
        etudiantService.importFromText(textFilePath);



    }
}
