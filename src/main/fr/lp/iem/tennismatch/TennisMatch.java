package fr.lp.iem.tennismatch;
// 1 set = 6 jeux
// Si égalité au dernier set (avant match donc), il faut 2 sets d'avance, on active donc le tiebreak
public class TennisMatch {

    private Player player1;
    private Player player2;
    private MatchType matchType;
    private boolean tieBreakInLastSet;
    private boolean inTieBreak;
    private boolean isRunning;

    public TennisMatch(Player player1, Player player2, MatchType matchType, boolean tieBreakInLastSet) {
        this.player1 = player1;
        this.player2 = player2;
        this.matchType = matchType;
        this.tieBreakInLastSet = tieBreakInLastSet;
        this.isRunning = true;
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
            throw new Error("Joueur non trouvé");
        }
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void setIsRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setTieBreakInLastSet(boolean tieBreakInLastSet) {
        this.tieBreakInLastSet = tieBreakInLastSet;
    }

    public void updateWithPointWonBy(Player player) {
        if (this.isRunning) {
            if (!this.inTieBreak) {
                if (player.getScore() == 3) { // si points du joueur = 40
                    if (getOtherPlayer(player).getScore() < 3) { //si autre joueur a moins de 40
                        player.setScore(5); //joueur gagne le jeu, passe à "GAME"
                    } else if (getOtherPlayer(player).getScore() == 3 || getOtherPlayer(player).getScore() == 4 ) { //Si autre est à 40 ou A
                        player.setScore(4);//joueur passe à "A"
                    }
                } else {
                    player.updateScore(1);
                }

                if (pointsForPlayer(player) == "A") {
                    if (pointsForPlayer(getOtherPlayer(player)) == "A") {
                        getOtherPlayer(player).setScore(3);
                        player.setScore(3);
                        //Les deux joueurs passent à 40
                    }
                }

                if (pointsForPlayer(player) == "GAME") {
                    player.updateGames(1);
                    System.out.println("won game for "+player.getName());
                    //Gain du set puisque 2 points d'écart
                    if (player.getGames() >= 6) { //Dans le cas d'un problème de gain du set
                        System.out.println("Equals or more than 6 games");
                        if (getOtherPlayer(player).getGames() == 6) {
                            System.out.println("Both have 6 games");
                            if (this.matchType.numberOfSetsToWin() == 2) {
                                if (this.tieBreakInLastSet && player.getSets() == 1) {
                                    System.out.println("dernier set avec tie break");
                                    this.inTieBreak = true;
                                } else {
                                    System.out.println("dernier set sans tie break");
                                }
                            } else {
                                if (this.tieBreakInLastSet && player.getSets() == 2) {
                                    System.out.println("dernier set avec tie break");
                                    this.inTieBreak = true;
                                } else {
                                    System.out.println("dernier set sans tie break");
                                }
                            }
                        } else if (player.getScore() >= 7 && player.getScore() >= getOtherPlayer(player).getScore() + 2) {
                            System.out.println(player.getName() + " gagne un set car 2 jeux d'écart au dessus de 6");
                            addSetToPlayer(player);
                        }
                    } else if (player.getGames() == 7 && getOtherPlayer(player).getGames() == 5) {
                        System.out.println(player.getName() + " gagne un set car 2 jeux d'écart au dessus de 6");
                        addSetToPlayer(player);
                    } else if (player.getGames() == getOtherPlayer(player).getGames() + 2) {
                        System.out.println(player.getName() + " gagne un set car 2 jeux d'écart et au moins 6");
                        addSetToPlayer(player);
                    }
                    player.setScore(0);
                    getOtherPlayer(player).setScore(0);
                }
            } else if (this.inTieBreak) { //tie break sur le dernier set
                player.updateScore(1);
                if (player.getScore() == 7) { //Si on a 7 points...
                    if (getOtherPlayer(player).getScore() <= 5) { //...On teste de savoir si l'autre joueur a 5 ou moins et win si c'est le cas
                        addSetToPlayer(player);
                    }
                } else if (player.getScore() >= 7 && player.getScore() >= getOtherPlayer(player).getScore() + 2) { // si score du joueur est superieur ou égal à score du j2 + 2
                    addSetToPlayer(player);
                } else {
                    System.out.print("Still not won with the tie break because no gap of 2...\n");
                }
            }
            //Check for winner here
            Player winner = checkForWinner(player);
            if (winner != null) {
                System.out.println("LE JOUEUR " +
                        winner.getName() +
                        " REMPORTE LE MATCH FACE A " +
                        getOtherPlayer(winner).getName() +
                        " AVEC UN SCORE DE " + winner.getSets() + "-" + getOtherPlayer(winner).getSets() + " !");

                this.isRunning = false;
            }
        } else {
            System.out.println("Mise à jour impossible : le match est terminé !");
        }
    }

    public Player checkForWinner(Player player) {
        Player p1 = player;
        Player p2 = getOtherPlayer(player);
        switch(this.matchType.numberOfSetsToWin()) {
            case 2:
                if (p1.getSets() == 2) {
                    return p1;
                } else if (p2.getSets() == 2) {
                    return p2;
                } else {
                    return null;
                }
            case 3:
                if (p1.getSets() == 3) {
                    return p1;
                } else if (p2.getSets() == 3) {
                    return p2;
                } else {
                    return null;
                }
            default:
                return null;
        }
    }

    public void addSetToPlayer(Player player) {
        player.updateSets(1);
        //TODO Sauvegarder le nombre de jeux
        player.setScore(0);
        getOtherPlayer(player).setScore(0);
        player.setGames(0);
        getOtherPlayer(player).setGames(0);
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
