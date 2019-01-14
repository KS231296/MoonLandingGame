package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMain {

    private boolean win = false;
    private boolean landed = false;
    private double thrustValue;
    private String nick;
    private boolean started = false;
    private Thread calculations = new Thread();
    private Scores score;
    private double fuel;
    private double h;
    private double v;
    private File scoresFile = new File("scoresData");
    private Thread mainGame = new Thread();
    CalcThread calcThread = new CalcThread(1000);


    ChangeListener listener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            thrustValue = sliderThrust.getValue();
            changeRocket();
            value.setText(String.format("val = %.2f", thrustValue));
        }
    };


    public void thrust() {
    }

    @FXML
    private Slider sliderThrust;

    @FXML
    private Text txtCiag;

    @FXML
    private LineChart<Number, Number> chartVH;

    private XYChart.Series<Number, Number> vh = new XYChart.Series<>();

    public void chartUpdate() {
        Platform.runLater(() -> {
            chartVH.getData().removeAll(chartVH.getData());
            vh.getData().add(new XYChart.Data<>(h, v));
            chartVH.getData().add(vh);

        });
    }

    @FXML
    private Button btnReset;

    @FXML
    private Text value;

    @FXML
    private ImageView imgRocket;

    @FXML
    void restart(ActionEvent event) {
        if (!started) {
            btnReset.setText("RESTART");
            sliderThrust.valueProperty().addListener(listener);
            sliderThrust.setDisable(false);
            started = true;
            mainGame.start();
            //  calcThread.run();
        } else {
            //    calcThread.stop();
            //  calcThread.run();

        }

    }

    public void changeRocket() {
        Image img;
        if (landed) {
            if (win) {
                img = new Image("sample/images/happyRocket.png");

            } else {
                img = new Image("sample/images/sadRocket.png");
            }
        } else {
            if (thrustValue == 0) {
                img = new Image("sample/images/fallingRocket.png");
            } else {
                img = new Image("sample/images/flamingRocket.png");

            }

        }


        imgRocket.imageProperty().setValue(img);
    }

    public void endGame(ActionEvent event) {
        sliderThrust.setDisable(true);
        score.setScore((int) fuel * 100);
        saveScore();
        new Alert(Alert.AlertType.INFORMATION, "Your score: " + score.getScore(), new ButtonType("OK", ButtonBar.ButtonData.OK_DONE)).showAndWait();
        started = false;
        newScene(event, "sample.fxml", 400, 200);
    }

    @FXML
    private TextField txtNick;

    @FXML
    private Button btnStart;


    @FXML
    void startApp(ActionEvent event) {
        nick = txtNick.getText();
        if (nick.equals("")) {
            nick = "no name";
        }
        score = new Scores(nick);
        newScene(event, "main.fxml", 1066, 600);

    }

    private void newScene(ActionEvent event, String fxmlName, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxmlName));

            Scene scene = new Scene(fxmlLoader.load(), width, height);
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

    private void saveScore() {
        ScoresData scores = ScoresData.readJSON(scoresFile);
        ScoresData.saveJSON(scores, scoresFile);
    }
}
