package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

import java.util.Observable;

public class ControllerMain {

    private double thrustValue;


    ChangeListener listener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observableValue, Object o, Object t1) {
            thrustValue = sliderThrust.getValue();
            value.setText(String.format("val = %.2f", thrustValue));
        }
    };




    @FXML
    public void thrust() {
        sliderThrust.valueProperty().addListener(listener);

    }

    @FXML
    private Slider sliderThrust;


    @FXML
    private Text txtCiag;

    @FXML
    private LineChart<Number, Number> chartVH;

    @FXML
    private Button btnReset;

    @FXML
    private Text value;

    @FXML
    void restart(ActionEvent event) {
        // nowe okno z nformacją po zakończonej grze
        // restart od początku

    }


}
