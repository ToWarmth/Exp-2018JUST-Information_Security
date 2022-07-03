package SecretDesign.controller;

import SecretDesign.Launch.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class Pane_main_Con {
    @FXML
    private Pane pane;

    @FXML
    public void handle(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource("SecretDesign/view/Pane_Instruction.fxml"));
        pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }
}
