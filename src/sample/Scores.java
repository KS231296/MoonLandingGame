package sample;

public class Scores {
    private final String nick;
    private int score;

    public Scores(String nick) {
        this.nick = nick;
        this.score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNick() {
        return nick;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return nick + ": " + score;
    }
}
