package com.oglimmer.kniffel.model;

import java.util.ArrayList;
import java.util.List;

public class DiceTest {

    private KniffelGame getKniffelGame() {
        List<KniffelPlayer> players = new ArrayList<>();
        players.add(new KniffelPlayer("Player1"));
        return new KniffelGame(players);
    }

//    @Test
    public void testRoll() {
        long a = 0;
        long b = 0;
        long c = 0;
        long d = 0;
        long e = 0;
        long f = 0;
        int i = 0;
        while (i++ < 500000) {
            KniffelGame kniffelGame = getKniffelGame();
            List<Integer> diceRolls = kniffelGame.getDiceRolls();
            for (int ele : diceRolls) {
                if (ele == 1) {
                    a++;
                } else if (ele == 2) {
                    b++;
                } else if (ele == 3) {
                    c++;
                } else if (ele == 4) {
                    d++;
                } else if (ele == 5) {
                    e++;
                } else if (ele == 6) {
                    f++;
                }
            }
        }
        System.out.println(a + " " + b + " " + c + " " + d + " " + e + " " + f);
    }

}
