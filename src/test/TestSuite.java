import fr.lp.iem.tennismatch.MatchType;
import fr.lp.iem.tennismatch.Player;
import fr.lp.iem.tennismatch.TennisMatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestSuite {

    Player player1;
    Player player2;
    TennisMatch monMatch;

    @Before
    public void setUp() throws Exception {
        //MATCH 1
        player1 = new Player("Jon");
        player2 = new Player("Doe");
        monMatch = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);

        for (int i=0; i<20; i++) {
            double rand = Math.random() * ( 2 - 0 );
            String test = "";
            if (rand > 1) {
                monMatch.updateWithPointWonBy(monMatch.getPlayer1());
                test = "Point pour Jon";
            } else {
                monMatch.updateWithPointWonBy(monMatch.getPlayer2());
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
        String res = "Joueur1: S"+monMatch.getPlayer1().getScore()
                + ", G"+monMatch.getPlayer1().getGames()
                + ", S"+monMatch.getPlayer1().getSets();


    }


}
