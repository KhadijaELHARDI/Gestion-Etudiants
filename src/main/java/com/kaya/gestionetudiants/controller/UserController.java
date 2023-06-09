package com.kaya.gestionetudiants.controller;

import com.kaya.gestionetudiants.App;
import com.kaya.gestionetudiants.jdbc.entities.User;
import com.kaya.gestionetudiants.jdbc.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    UserService userService = new UserService();
   @FXML
    private TextField email;
   @FXML
    private PasswordField password;
   @FXML
   private Button auth;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        auth.setOnMouseClicked(arg->{
            if ( auth(constructionUser())){
                try {

                    Stage stage1 = (Stage) auth.getScene().getWindow();
                    stage1.close();
                    Stage stage= new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("etudiant-view.fxml"));
                    Scene scene = new Scene(fxmlLoader.load(), 734 , 504);
                    stage.setResizable(false);
                    stage.setTitle("Etuadiant App");
                    stage.setScene(scene);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information");
                alert.setHeaderText("Auth erreur");
                alert.setContentText("Veuillez saisir les informations d'authentification .");
                alert.showAndWait();

            }

        });
    }
    public boolean auth(User user){
        if(user!=null) {
            return userService.auth(user);
        }
        else
            return false;
    }
    public User constructionUser(){
        User user = new User();
        if(email.getText().isEmpty() || password.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Auth erreur");
            alert.setContentText("Veuillez saisir les informations d'authentification .");
            alert.showAndWait();

        }
        else {
            user.setPassword(password.getText());
            user.setEmail(email.getText());
        }
        return user;
    }
}
