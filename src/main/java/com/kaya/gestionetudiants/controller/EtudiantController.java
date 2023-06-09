package com.kaya.gestionetudiants.controller;

import com.kaya.gestionetudiants.jdbc.dao.Imp.EtudiantDaoImpl;
import com.kaya.gestionetudiants.jdbc.entities.Etudiant;
import com.kaya.gestionetudiants.jdbc.services.EtudiantService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import com.kaya.gestionetudiants.jdbc.dao.EtudiantDao;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class EtudiantController implements Initializable {
    EtudiantService etudiantService= new EtudiantService();
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;

    @FXML
    private TextField age;

    @FXML
    private Button ajouter , vider,modifier,importerExcel , exporterExcel,importerJson,exporterJson,importerTxt,exporterTxt;

    @FXML
    private TableView tabelEtudiant;
    @FXML
    private TextField id_textfiled;
    @FXML
    private Label id_label;


    @FXML
    private TableColumn<Etudiant , Integer> matriculecol;
    @FXML
    private TableColumn<Etudiant , String> nomcol;
    @FXML
    private TableColumn<Etudiant , String> prenomcol;
    @FXML
    private TableColumn<Etudiant , Integer> agecol;

    private List<Etudiant> etudiantList = getEtudiantList();
    TableColumn<Etudiant, Void> colSupprimer = new TableColumn<>("Supprimer");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        disable();
        exporterTxt.setOnMouseClicked(arg->{
            exporterEtudiantTxt();
        });
        importerTxt.setOnMouseClicked(arg->{
            SupprimerTous();
            importerEtudiantTxt();
        });
        exporterJson.setOnMouseClicked(arg->{
            exporterFilmsJson();
        });
        importerJson.setOnMouseClicked(arg->{
            SupprimerTous();
            importerFilmJson();
        });
        importerExcel.setOnMouseClicked(arg->{
            SupprimerTous();
            importerEtudiantExcel();
        });
        exporterExcel.setOnMouseClicked(arg->{
            exporterEtudiantsExcel();
        });
        tabelEtudiant.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tabelEtudiant.getSelectionModel().isEmpty()) {
                Etudiant selectedEtudiant = (Etudiant) tabelEtudiant.getSelectionModel().getSelectedItem();
               enable();
                mettreEtudiantDansFiled(selectedEtudiant);
            }
        });
        modifier.setOnMouseClicked(arg->{
            modifierEtudiant(getEtudiantFromFieldToModifier());
            disable();
            actualiserTableView();
            clear();

        });
        loadTable(getEtudiantList());
        colSupprimer.setCellFactory(param -> new TableCell<Etudiant, Void>() {
            private  Button supprimerButton = new Button("Supprimer");
            {
                supprimerButton.setOnAction(event -> {
                    Etudiant etudiant = getTableView().getItems().get(getIndex());
                    deleteEtudiant(etudiant.getMatricule());
                    actualiserTableView();
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(supprimerButton);
                }
            }
        });
        ajouter.setOnMouseClicked(arg->{
            save(getEtudiantFromField());
            clear();
            actualiserTableView();
        });
        vider.setOnMouseClicked(arg->{
            clear();
        });
       /* actualiser.setOnMouseClicked(arg->{
            actualiserTableView();
        });*/


    }
    public Etudiant getEtudiantFromField(){
        Etudiant etudiant ;
        if(nom.getText().isEmpty() || prenom.getText().isEmpty() || age.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir tous informations .");
            alert.showAndWait();
            return null;
        }else{
            etudiant=new Etudiant();
            etudiant.setNom(nom.getText());
            etudiant.setPrenom(prenom.getText());
            etudiant.setAge(Integer.parseInt(age.getText()));
        }
        return etudiant;
    }
    public void save(Etudiant etudiant){
        if(etudiant!=null)
            etudiantService.ajouterEtudiant(etudiant);
    }
    public List<Etudiant> getEtudiantList(){
       return etudiantService.trouverTousLesEtudiants();
    }
    public void deleteEtudiant(int id){
        etudiantService.supprimerEtudiant(id);
    }
    public void clear(){
        nom.setText("");
        prenom.setText("");
        age.setText("");
    }
    private void loadTable(List<Etudiant> etudiants){
        matriculecol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomcol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        agecol.setCellValueFactory(new PropertyValueFactory<>("age"));
        tabelEtudiant.getColumns().add(colSupprimer);

        tabelEtudiant.getItems().addAll(etudiants);
    }
    private void actualiserTableView() {
        tabelEtudiant.getItems().clear();
        tabelEtudiant.getItems().addAll(getEtudiantList()); }
    private void enable(){
        id_textfiled.setVisible(true);
        id_label.setVisible(true);
        modifier.setVisible(true);
        id_textfiled.setDisable(true);

    }
    private void disable(){
        id_textfiled.setVisible(false);
        id_label.setVisible(false);
        modifier.setVisible(false);
        id_textfiled.setDisable(false);

    }
    public void modifierEtudiant(Etudiant etudiant){
        etudiantService.modifierEtudiant(etudiant);

    }
    public void mettreEtudiantDansFiled(Etudiant etudiant){
        if(etudiant!=null){
        nom.setText(etudiant.getNom());
        prenom.setText(etudiant.getPrenom());
        age.setText(String.valueOf(etudiant.getAge()));

        id_textfiled.setText(String.valueOf(etudiant.getMatricule()));}
    }
    public Etudiant getEtudiantFromFieldToModifier(){
        Etudiant etudiant ;
        if(nom.getText().isEmpty() || prenom.getText().isEmpty() || age.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir tous informations .");
            alert.showAndWait();
            return null;
        }else{
            etudiant=new Etudiant();
            etudiant.setNom(nom.getText());
            etudiant.setPrenom(prenom.getText());
            etudiant.setAge(Integer.parseInt(age.getText()));
            etudiant.setMatricule(Integer.parseInt(id_textfiled.getText()));
        }
        return etudiant;
    }

    public void exporterEtudiantsExcel(){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Mes Etudiants");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Matricule");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Prenom");
        headerRow.createCell(3).setCellValue("Age");

        int rowNum = 1;
        for (Etudiant etudiant : etudiantList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(etudiant.getMatricule());
            row.createCell(1).setCellValue(etudiant.getNom());
            row.createCell(2).setCellValue(etudiant.getPrenom());
            row.createCell(3).setCellValue(etudiant.getAge());



        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Etudiant.xlsx");
        File file = fileChooser.showSaveDialog(exporterExcel.getScene().getWindow());

        if (file != null) {

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Fichier Excel exporté avec succès.");
                alert.showAndWait();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        try {
            workbook.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void importerEtudiantExcel() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Excel", "*.xlsx", "*.xls"));
        File file = fileChooser.showOpenDialog(importerExcel.getScene().getWindow());

        if (file != null) {
            try (FileInputStream fileIn = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(fileIn)) {

                Sheet sheet = workbook.getSheetAt(0);
                List<Etudiant> etudiantsImported = new ArrayList<>();
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);

                    int matricule = (int) row.getCell(0).getNumericCellValue();
                    String nom = row.getCell(1).getStringCellValue();
                    String prenom = row.getCell(2).getStringCellValue();
                    int age = (int) row.getCell(3).getNumericCellValue();


                    Etudiant etudiant= new Etudiant();
                    etudiant.setMatricule(matricule);
                    etudiant.setNom(nom);
                    etudiant.setPrenom(prenom);
                    etudiant.setAge(age);
                    save(etudiant);

                }

                    actualiserTableView();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Importation des etudiants à partir du fichier Excel réussie. ");
                    alert.showAndWait();


            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    public void exporterFilmsJson(){
        List<Etudiant> etudiants = getEtudiantList();

        JSONArray jsonArray = new JSONArray();

        for (Etudiant etudiant : etudiants) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("matricule", etudiant.getMatricule());
            jsonObject.put("nom", etudiant.getNom());
            jsonObject.put("prenom", etudiant.getPrenom());
            jsonObject.put("age", etudiant.getAge());


            jsonArray.put(jsonObject);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File file = fileChooser.showSaveDialog(exporterJson.getScene().getWindow());

        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(jsonArray.toString());
                fileWriter.flush();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Exportation des données des etudiantes en JSON réussie.");
                alert.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void importerFilmJson(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier JSON", "*.json"));
        File file = fileChooser.showOpenDialog(importerJson.getScene().getWindow());

        if (file != null) {
            try (FileReader fileReader = new FileReader(file)) {
                StringBuilder jsonContent = new StringBuilder();
                int character;
                while ((character = fileReader.read()) != -1) {
                    jsonContent.append((char) character);
                }
                List<Etudiant> etudiantsImported = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(jsonContent.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Etudiant etudiant=new Etudiant();
                    etudiant.setMatricule(jsonObject.getInt("matricule"));
                    etudiant.setNom(jsonObject.getString("nom"));
                    etudiant.setPrenom(jsonObject.getString("prenom"));
                    etudiant.setAge(jsonObject.getInt("age"));

                   save(etudiant);
                }

                    actualiserTableView();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Importation des etudiants à partir du fichier Json réussie. ");
                    alert.showAndWait();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void exporterEtudiantTxt(){


        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les données des etudiantes");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));

         Stage stage = (Stage) exporterTxt.getScene().getWindow();
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                for (Etudiant etudiant:getEtudiantList()) {
                    writer.write(etudiant.getMatricule() + "\t" +etudiant.getNom() + "\t" + etudiant.getPrenom()+ "\t" + etudiant.getAge() + "\t");
                    writer.newLine();
                }

                writer.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export terminé");
                alert.setHeaderText(null);
                alert.setContentText("Les données des etudiantes ont été exportées avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'exportation du fichier.");
                alert.showAndWait();
            }
        }

    }
    public void importerEtudiantTxt(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir le fichier à importer");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte", "*.txt"));

        Stage stage = (Stage) importerTxt.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        List<Etudiant> etudiantsImported = new ArrayList<>();

        if (file != null) {
            try {

                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] attributs = line.split("\t");

                    Etudiant etudiant= new Etudiant();
                    etudiant.setMatricule(Integer.parseInt(attributs[0]));
                    etudiant.setNom(attributs[1]);
                    etudiant.setPrenom(attributs[2]);
                    etudiant.setAge(Integer.parseInt(attributs[3]));
                    save(etudiant);
                }

                    actualiserTableView();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Importation des etudiants à partir du fichier txt réussie. ");

                bufferedReader.close();
                reader.close();


            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'importation des données.");
                alert.showAndWait();
            }
        }

    }
    public void supprimerTous(){
        for(Etudiant etudiant: getEtudiantList()){
            deleteEtudiant(etudiant.getMatricule());
        }
    }
    public void SupprimerTous(){
        for(Etudiant etud : getEtudiantList())
            deleteEtudiant(etud.getMatricule());
    }

}
