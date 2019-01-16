package sample;

import calculations.LandingAcceleration;
import calculations.LandingAnalyzer1;
import interfaces.CalculateAcceleration;
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
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMain implements Observer {

    private boolean win = false;
    private boolean landed = false;
    private double thrustValue;
    private double thrustValue1s;
    private String nick;
    private boolean started = false;
    private Scores score;
    private double fuel;
    private double h;
    private double v;
    private File scoresFile = new File("scoresData");
    private Thread mainGame = new Thread();
    private Thread calculations = new Thread();
    CalcThread calcThread;


    ChangeListener listener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            thrustValue = sliderThrust.getValue();

            calcThread.setU(thrustValue);
            changeRocket();
            //double h = (thrustValue * 50000) / 16.5;
           // moveRocket(h);
            value.setText(String.format("val = %.2f", thrustValue));
            actualizeDataSeries(sliderThrust.getValue(), 3 * sliderThrust.getValue());
            chartUpdate();

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
    void restart(ActionEvent event) {
        LandingAnalyzer1 analyzer = new LandingAnalyzer1();
        LandingAcceleration accaleration = new LandingAcceleration();

        calcThread = new CalcThread(200, thrustValue, accaleration, analyzer);
        calcThread.addObserver(this);

        Platform.runLater(() -> {
            actualizeDataSeries(sliderThrust.getValue(), 3 * sliderThrust.getValue());
            chartUpdate();
        });
        if (!started) {
            btnReset.setText("RESTART");
            sliderThrust.valueProperty().addListener(listener);
            sliderThrust.setDisable(false);
            started = true;
            calculations = new Thread(calcThread);
            calculations.start();

        } else {

            calculations.interrupt();
            System.out.println("interrupt?");
            calcThread.stop();
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
    public void update(Observable o, Object arg) {
        CalcThread calcThread = (CalcThread) o;
        this.h = calcThread.getH0();
        moveRocket(h);
        this.fuel = calcThread.getM0()-1000;
        rotateFuel(fuel);
        

        Platform.runLater(() -> {
            actualizeDataSeries(h, v);
            chartUpdate();
        });


    }
}
