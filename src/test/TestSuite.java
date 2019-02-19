import fr.lp.iem.tennismatch.MatchType;
import fr.lp.iem.tennismatch.Player;
import fr.lp.iem.tennismatch.TennisMatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSuite {

    Player player1;
    Player player2;
    TennisMatch m;

    @Test //On gagbe un jeu sans avantage sur les points
    public void winJeuSansAvantage() throws Exception {
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        m = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);

        //Début du match
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne le jeu

        assertEquals(1, m.getPlayer1().getGames()); //Le test passe si player 1 a bien gagné 1 jeu
    }

    @Test //On gagne un jeu avec avantage sur les points
    public void winJeuAvecAvantage() throws Exception {
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        m = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);

        //Début du match
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 30
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à avantage, J2 est à 40
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à avantage, J1 repasse à 40

        //On vérifie que J2 est à Avantage
        assertEquals("A", m.pointsForPlayer(m.getPlayer2()));

        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à avantage, J2 repasse à 40

        //On vérifie que J1 est à Avantage
        assertEquals("A", m.pointsForPlayer(m.getPlayer1()));

        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne

        assertEquals(1, m.getPlayer1().getGames()); //Le test passe si player 1 a bien gagné 1 jeu avec avantage
    }

    @Test //On gagne un set avec 2 jeux d'écart, donc win direct sans tie break
    public void winSetNormal() throws Exception {
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        m = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);

        m.getPlayer1().setGames(4);
        m.getPlayer2().setGames(4);

        //Début du match, les joueurs commencent avec chacun 4 jeux
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne un jeu
        System.out.println("P1 score: "+m.pointsForPlayer(m.getPlayer1())+", P1 games: "+ m.getPlayer1().getGames());
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne un jeu
        System.out.println("P1 score: "+m.pointsForPlayer(m.getPlayer1())+", P1 games: "+ m.getPlayer1().getGames());
        System.out.println("P1 set: "+m.getPlayer1().getSets());
        //J1 est à 6 jeux et au moins 2 de plus que J2 = gagne le set

        assertEquals(1, m.getPlayer1().getSets()); //Le test passe si player 1 a bien gagné 1 jeu avec avantage
    }

    @Test //On gagne un set avec 2 jeux d'écart mais avec égalité à 5-5 avant
    public void winSetNormalEquals5() throws Exception {
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        m = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);

        m.getPlayer1().setGames(4);
        m.getPlayer2().setGames(4);

        //Début du match, les joueurs commencent avec chacun 4 jeux
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne un jeu (passage à 5 jeux)
        System.out.println("P1 score: "+m.pointsForPlayer(m.getPlayer1())+", P1 games: "+ m.getPlayer1().getGames());
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 15
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 30
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 40
        m.updateWithPointWonBy(m.getPlayer2()); //J2 gagne un jeu (passage à 5 jeux)
        System.out.println("P2 score: "+m.pointsForPlayer(m.getPlayer2())+", P2 games: "+ m.getPlayer2().getGames());
        System.out.println("Egalité à 5 ! On continue jusqu'à 7-5");
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne un jeu (passage à 6)
        System.out.println("P1 score: "+m.pointsForPlayer(m.getPlayer1())+", P1 games: "+ m.getPlayer1().getGames());
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne un jeu (passage à 7)
        System.out.println("P1 score: "+m.pointsForPlayer(m.getPlayer1())+", P1 games: "+ m.getPlayer1().getGames());

        System.out.println("P1 set: "+m.getPlayer1().getSets());

        assertEquals(1, m.getPlayer1().getSets()); //Le test passe si player 1 a bien gagné 1 jeu avec avantage
    }

    @Test //On gagne un set sans 2 d'écart, donc il y a tie break : point décisif. Points en 1234567... et non plus en 15/30/40
    public void winSetAvecTieBreak() throws Exception {
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        m = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, true);

        m.getPlayer1().setGames(5);
        m.getPlayer2().setGames(5);

        //Début du match, les joueurs commencent avec chacun 5 jeux
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 15
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 30
        m.updateWithPointWonBy(m.getPlayer1()); //J1 passe à 40
        m.updateWithPointWonBy(m.getPlayer1()); //J1 gagne un jeu et un set
        System.out.println("P1 set: "+m.getPlayer1().getSets());
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 15
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 30
        m.updateWithPointWonBy(m.getPlayer2()); //J2 passe à 40
        m.updateWithPointWonBy(m.getPlayer2()); //J2 gagne un jeu et un set
        System.out.println("P2 set: "+m.getPlayer2().getSets());
        System.out.println("-------------------------");
        System.out.println("P1 games: "+m.getPlayer1().getGames());
        System.out.println("P2 games: "+m.getPlayer2().getGames());
        System.out.println("P1 set: "+m.getPlayer1().getSets());
        System.out.println("P2 set: "+m.getPlayer2().getSets());
        //J1 est à 6 jeux et au moins 2 de plus que J2 = gagne le set

        assertEquals(1, m.getPlayer1().getSets()); //Le test passe si player 1 a bien gagné 1 jeu avec avantage

    }

    @Before //Dernier set: pas de tie break possible, jeux qui peuvent aller plus haut que 6
    public void winSetSansTieBreak() throws Exception {

    }

    @Test
    public void setUp() throws Exception {
        //MATCH 1
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        m = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);

        for (int i=0; i<100; i++) {
            double rand = Math.random() * ( 20 - 0 );
            String test = "";
            if (rand > 10) {
                m.updateWithPointWonBy(m.getPlayer1());
                test = "Point pour Jon";
            } else {
                m.updateWithPointWonBy(m.getPlayer2());
                test = "Point pour Doe";
            }

            System.out.print(test + "\n");
            System.out.print(player1.statement() + "\n");
            System.out.print(player2.statement() + "\n");

        }
        System.out.print("FINAL \n");
        System.out.print(player1.statement() + "\n");
        System.out.print(player2.statement() + "\n");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        String res = "Joueur1: Score"+ m.pointsForPlayer(m.getPlayer1())
                + ", Jeu"+ m.getPlayer1().getGames()
                + ", Set"+ m.getPlayer1().getSets();


    }


}
