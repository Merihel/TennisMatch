package fr.lp.iem.tennismatch;
// 1 set = 6 jeux
// Si égalité au dernier set (avant match donc), il faut 2 sets d'avance, on active donc le tiebreak
public class TennisMatch {

    private Player player1;
    private Player player2;
    private MatchType matchType;
    private boolean tieBreakInLastSet;
    private int currentSet;
    private int currentGame;

    public TennisMatch(Player player1, Player player2, MatchType matchType, boolean tieBreakInLastSet) {
        this.player1 = player1;
        this.player2 = player2;
        this.matchType = matchType;
        this.tieBreakInLastSet = tieBreakInLastSet;
        this.currentGame = 1;
        this.currentSet = 1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

    public boolean isTieBreakInLastSet() {
        return tieBreakInLastSet;
    }

    public Player getOtherPlayer(Player player) {
        if (player.equals(getPlayer1())) {
            return getPlayer2();
        } else if (player.equals(getPlayer2())){
            return getPlayer1();
        } else {
            throw new Error("Joueur inconnu...");
        }

    }

    public void setTieBreakInLastSet(boolean tieBreakInLastSet) {
        this.tieBreakInLastSet = tieBreakInLastSet;
    }

    public void updateWithPointWonBy(Player player) {
        player.updateScore(1);
        if (pointsForPlayer(player) == "A") {
            if (pointsForPlayer(getOtherPlayer(player)) == "A") {
                getOtherPlayer(player).setScore(3);
            }
        }
        if (pointsForPlayer(player) == "GAME") {
            currentGame += 1;
            player.updateGames(1);
            player.setScore(0);
            getOtherPlayer(player).setScore(0);
            if (player.getGames() == 6) {
                currentSet += 1;
                player.updateSets(1);
            }
        }

        //Check for winner here
    }

    public String pointsForPlayer(Player player) {
        String score = "0";
        switch (player.getScore()) {
            case 1:
                score = "15";
                break;
            case 2:
                score = "30";
                break;
            case 3:
                score = "40";
                break;
            case 4:
                score = "A";
                break;
            case 5:
                score = "GAME";
                break;
        }

        return score;
    }

    public int currentSetNumber() {
        return 0;
    }

    public int gamesInCurrentSetForPlayer(Player player) {
        return 0;
    }

    public int gamesInSetForPlayer(int setNumber, Player player) {
        return player.getGames();
    }

    public boolean isFinished() {
        int setsToWin = getMatchType().numberOfSetsToWin();

        if (getPlayer1().getSets() == setsToWin) {
            return true;
        }

        if (getPlayer2().getSets() == setsToWin) {
            return true;
        }

        return false;
    }

}
