package SecretDesign.controller;

import SecretDesign.Launch.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Platform.exit;

public class Pane_CrackingMethod_Con {
    Pane pane;
    @FXML
    MenuBar menubar;

    public void getStage(String name, ActionEvent event) throws IOException {
        Stage stage=(Stage) menubar.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource(name));
        pane = loader.load();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
    }

    public void clickPrevious(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_Describe.fxml",event);

    }

    public void clickNext(ActionEvent event) throws IOException{
        getStage("SecretDesign/view/Pane_EnandDe.fxml",event);
    }

    public void clickExit() {
        exit();
    }

    public void click_main(ActionEvent event) throws IOException {
        Stage stage=(Stage) menubar.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getClassLoader().getResource("SecretDesign/view/Pane_main.fxml"));
        SplitPane splitPane= loader.load();
        Scene scene = new Scene(splitPane);
        stage.setScene(scene);
    }

    public void click_History(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_History.fxml",event);
    }

    public void click_Describe(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_Describe.fxml",event);
    }

    public void click_CrackingMethod(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_CrackingMethod.fxml",event);
    }

    public void click_EnandDe(ActionEvent event) throws IOException{
        getStage("SecretDesign/view/Pane_EnandDe.fxml",event);
    }

    public void click_Instruction(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_Instruction.fxml",event);
    }

    public void click_FurtherStudy(ActionEvent event) throws IOException {
        getStage("SecretDesign/view/Pane_FurtherStudy.fxml",event);
    }
}
