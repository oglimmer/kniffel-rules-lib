package com.oglimmer.kniffel.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveDiceTest {
    private KniffelGame getKniffelGame() {
        List<KniffelPlayer> players = new ArrayList<>();
        players.add(new KniffelPlayer("Player1"));
        return new KniffelGame(players);
    }

    @Test
    public void testRemoveDice_simplest_removeAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1));
        kniffelGame.removeDice(diceRolls, null);
        Assertions.assertArrayEquals(new Integer[0], diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_simplest_keepAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1));
        kniffelGame.removeDice(diceRolls, new int[]{1});
        Assertions.assertArrayEquals(new Integer[]{1}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_2ele_removeAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 2));
        kniffelGame.removeDice(diceRolls, null);
        Assertions.assertArrayEquals(new Integer[0], diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_2ele_keepAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 2));
        kniffelGame.removeDice(diceRolls, new int[]{1, 2});
        Assertions.assertArrayEquals(new Integer[]{1, 2}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_6ele_keepHalf() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        int[] diceToKeep = new int[]{1, 2, 3};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_6ele_keepSome() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        int[] diceToKeep = new int[]{1, 3, 5};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{1, 3, 5}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_6ele_keepAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        int[] diceToKeep = new int[]{1, 2, 3, 4, 5, 6};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_duplicatedEle_keepNone() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 1));
        int[] diceToKeep = new int[0];
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_duplicatedEle_keepHalf() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 1));
        int[] diceToKeep = new int[]{1};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{1}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_duplicatedEle_keepAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 1));
        int[] diceToKeep = new int[]{1, 1};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{1, 1}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_complex_keepAll() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(6, 4, 2, 4, 4, 2, 3, 1));
        int[] diceToKeep = new int[]{6, 4, 2, 4, 4, 2, 3, 1};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{6, 4, 2, 4, 4, 2, 3, 1}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_complex_keepNone() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(6, 4, 2, 4, 4, 2, 3, 1));
        int[] diceToKeep = new int[]{};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_complex_keepOne() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(6, 4, 2, 4, 4, 2, 3, 1));
        int[] diceToKeep = new int[]{4};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{4}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_complex_keepOne2() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(6, 4, 2, 4, 4, 2, 3, 1));
        int[] diceToKeep = new int[]{1};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{1}, diceRolls.toArray());
    }

    @Test
    public void testRemoveDice_complex_keepSome() {
        KniffelGame kniffelGame = getKniffelGame();
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(6, 4, 2, 4, 4, 2, 3, 1));
        int[] diceToKeep = new int[]{1, 3, 2, 4};
        kniffelGame.removeDice(diceRolls, diceToKeep);
        Assertions.assertArrayEquals(new Integer[]{4, 2, 3, 1}, diceRolls.toArray());
    }

}
