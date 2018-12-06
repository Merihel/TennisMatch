package fr.lp.iem.tennismatch;

import java.util.HashMap;

public class Player {

    private String name;
    private int sets;
    private int score;
    private int games;

    public Player(String name) {
        this.name = name;
    }

    public void updateScore(int points) {
        this.setScore(this.getScore() + points);
    }

    public void updateSets(int sets) {
        this.setSets(this.getSets() + sets);
    }

    public void updateGames(int games) {
        this.setGames(this.getGames() + games);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public String statement() {
        return "Joueur "+getName()+": S"+getScore()
                + ", G"+getGames()
                + ", S"+getSets();
    }
}
