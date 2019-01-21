package sample;

import com.google.gson.Gson;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;

/**
 * klasa do przechowywania danych
 */
public class ScoresData {

    static Gson gson = new Gson();
    private ArrayList<Scores> scores;

    public ScoresData() {
        this.scores = new ArrayList<>();
    }

    public ScoresData(ArrayList<Scores> scores) {
        this.scores = scores;
    }

    /**
     * Metoda zapisu danych do pliku z wykorzystaniem JSON
     * @param data zmienna przechowujaca dane
     * @param dataFile plik zapisu
     */
    public static void saveJSON(ScoresData data, File dataFile) {

        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save data");
        }
    }

    /**
     * metoda odczytu z pliku z wykorzystaniem JSON
     * @param dataFile plik odczytu
     * @return obiekt klasy danych
     */
    public static ScoresData readJSON(File dataFile) {
        ScoresData data = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dataFile))) {
            data = gson.fromJson(bufferedReader, ScoresData.class);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load data").showAndWait();
        }
        return data;
    }

    /**
     * aktualizowanie listy winików
     * @param score pojedynczy wynik (nick + ilosc punktów)
     */
    public void addScores(Scores score) {
        this.scores.add(score);
    }

    public ArrayList<Scores> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Scores> scores) {
        this.scores = scores;
    }


}
