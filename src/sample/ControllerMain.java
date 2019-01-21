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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMain implements PropertyChangeListener {

    private boolean win = false;
    private boolean landed = false;
    private double thrustValue;
    private String nick;
    private boolean started = false;
    private Scores score;
    private double fuel;
    private double h = 50000;
    private double v = -150;
    private File scoresFile = new File("scoresData");
    private Thread calculations = new Thread();
    private CalcThread calcThread;

    ChangeListener listener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            thrustValue = sliderThrust.getValue();

            calcThread.setU(thrustValue);
            changeRocket();

            value.setText(String.format("%.2f", thrustValue));

        }
    };


    public void moveRocket(double distanceM) {
        double land = 400;
        double start = -50;
        double wholeDistancePX = 450;
        double wholeDistanceM = 50000;
        double pxPERmetr = 0.009;

        double distancePX = (int) (distanceM * pxPERmetr);
        double posY = land - distancePX;
        imgRocket.setLayoutY(posY);

    }

    @FXML
    private Text txtV;

    @FXML
    private Text scoreTXT;

    @FXML
    private Text txtStart;

    @FXML
    private Line lineFuel;

    public void rotateFuel(double fuel) {
        double converter = Math.PI / 1730.15;
        double rad = converter * fuel;
        double r = 56;
        double yPos = -r * Math.sin(rad);
        double xPos = -r * Math.cos(rad);
        lineFuel.setEndX(xPos);
        lineFuel.setEndY(yPos);
    }


    @FXML
    private Slider sliderThrust;

    @FXML
    private Text txtCiag;

    @FXML
    private LineChart<Number, Number> chartVH;

    private XYChart.Series<Number, Number> vh = new XYChart.Series<>();

    private void actualizeDataSeries(double h, double v) {
        vh.getData().add(new XYChart.Data<>(h, v));
    }

    public void chartUpdate() {
        // Platform.runLater(() -> {
        chartVH.getData().removeAll(chartVH.getData());

        chartVH.getData().add(vh);

        // });
    }


    @FXML
    private Button btnReset;

    @FXML
    private Text value;

    @FXML
    private ImageView imgRocket;

    @FXML
    void stopGame(ActionEvent event) {
        calculations.stop();
        calcThread.stop();
        started = false;
        btnReset.setText("START");
        txtStart.setText("If you're ready, press here ->");


    }


    @FXML
    void restart(ActionEvent event) {

        vh.getData().clear();
        chartVH.getData().clear();
        chartVH.getData().removeAll();

        h = 50000;
        v = -150;
        fuel = 2730.14 - 1000;
        changeRocket();


        if (!started) {
            btnReset.setText("RESTART");
            txtStart.setText("");
            sliderThrust.valueProperty().addListener(listener);
            calcThread = new CalcThread(thrustValue);
            calcThread.addListener(this);
            sliderThrust.setDisable(false);
            started = true;
            calculations = new Thread(calcThread);
            calculations.start();

        } else {
            calcThread.stop();
            calcThread = new CalcThread(thrustValue);
            calcThread.addListener(this);
            sliderThrust.setDisable(false);
            started = true;
            calculations = new Thread(calcThread);
            calculations.start();
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
    private Text txtH;

    @FXML
    void startApp(ActionEvent event) {
        nick = txtNick.getText();
        if (nick.equals("")) {
            nick = "no name";
        }
        score = new Scores(nick);
        newScene(event, "main.fxml", 950, 650);

    }

    private void newScene(ActionEvent event, String fxmlName, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxmlName));

            Scene scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = new Stage();

            stage.setTitle("Moon Game");
            stage.resizableProperty().setValue(Boolean.FALSE);
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        String newProperty = evt.getPropertyName();
        double newVal = (double) evt.getNewValue();

        switch (newProperty) {
            case "h0": {
                h = newVal;
                txtH.setText(String.format("%.2f", Math.abs(h)));
                break;
            }
            case "m0": {
                fuel = newVal - 1000;
                break;
            }
            case "v0": {

                v = newVal;
                txtV.setText(String.format("%.2f", Math.abs(v)));
                break;
            }

        }
        moveRocket(h);
        rotateFuel(fuel);

        Platform.runLater(() -> {
            actualizeDataSeries(h, Math.abs(v));
            chartUpdate();
        });

        if (fuel == 0) {
            sliderThrust.setDisable(true);
            sliderThrust.setValue(0);
            thrustValue = 0;
        }

        if (h == 0) {
            calcThread.stop();
            calculations.interrupt();
            landed = true;
            if (-0.2 < v && v < 0.2) {
                win = true;
            }
            changeRocket();
            score.setScore((int) (fuel * 100 - Math.abs(v)) + 100);
            if(win){
                scoreTXT.setText("Congratulations, "+nick+"!\n You landed succesfully!"+"\nYour score: " + score);

            }else{
                scoreTXT.setText("You crashed, "+nick+" ;("+"\nYour score: " + score);

            }
        }
    }


} // end class
