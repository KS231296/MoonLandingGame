package sample;

import com.google.gson.Gson;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

/**
 * klasa do przechowywania danych
 */
public class ScoresData {

    private ArrayList<Scores> scores;
    static Gson gson = new Gson();

    public ArrayList<Scores> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Scores> scores) {
        this.scores = scores;
    }

    public ScoresData(ArrayList<Scores> scores) {
        this.scores = scores;
    }

    public static void saveJSON(ScoresData data, File dataFile) {

        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save data");
        }
    }

    public static ScoresData readJSON(File dataFile) {
        ScoresData data = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile))) {
            data = gson.fromJson(bufferedReader, ScoresData.class);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data");
        }
        return data;
    }


}
