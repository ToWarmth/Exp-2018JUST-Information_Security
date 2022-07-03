package SecretDesign.Launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private SplitPane splitPane;

    @Override
    public void start(Stage primaryStage)  {
        try {
            this.primaryStage = primaryStage;
            // Load root layout from fxml file.
            FXMLLoader loader_1 = new FXMLLoader();
            loader_1.setLocation(MainApp.class.getClassLoader().getResource("SecretDesign/view/Pane_main.fxml"));
            splitPane = loader_1.load();

            Scene scene = new Scene(splitPane);
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("应用密码学课程设计");
            this.primaryStage.setResizable(false);
            this.primaryStage.getIcons().add( new Image("SecretDesign/icon/HEY.png"));
            this.primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
            launch(args);
        }
    }

