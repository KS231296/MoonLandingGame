package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    @FXML
    private TextField txtNick;

    @FXML
    private Button btnStart;


    @FXML
    void startApp(ActionEvent event) {

        btnStart.setText("started");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("main.fxml"));
           // Slider sliderThrust = new Slider();



            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();

            stage.setTitle("Moon Game");
            stage.setScene(scene);

            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }

    }

}
