package com.oglimmer.kniffel.model;

import com.oglimmer.kniffel.service.KniffelRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RuleTest {

    private KniffelRules kniffelRules = new KniffelRules();

    private KniffelGame getKniffelGame() {
        List<KniffelPlayer> players = new ArrayList<>();
        players.add(new KniffelPlayer("Player1"));
        return new KniffelGame(players);
    }

    private static List<Integer> createDiceRolls(int... dices) {
        List<Integer> collect = Arrays.stream(dices).boxed().collect(Collectors.toList());
        Collections.shuffle(collect);
        return collect;
    }

    @Test
    public void ones_with1() {
        List<Integer> diceRolls = createDiceRolls(1);
        int res = getKniffelGame().getToAddScore(BookingType.ONES, diceRolls, kniffelRules);
        Assertions.assertEquals(1, res);
    }


    @Test
    public void ones_with3() {
        List<Integer> diceRolls = new ArrayList<>(Arrays.asList(1, 1, 1, 4, 5, 6));
        int res = getKniffelGame().getToAddScore(BookingType.ONES, diceRolls, kniffelRules);
        Assertions.assertEquals(3, res);
    }

    @Test
    public void smallStraigt_with1() {
        List<Integer> diceRolls = createDiceRolls(1, 2, 3, 4, 4, 4);
        int res = getKniffelGame().getToAddScore(BookingType.SMALL_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(30, res);
    }

    @Test
    public void smallStraigt_with2() {
        List<Integer> diceRolls = createDiceRolls(2, 2, 3, 4, 5, 5);
        int res = getKniffelGame().getToAddScore(BookingType.SMALL_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(30, res);
    }

    @Test
    public void smallStraigt_with3() {
        List<Integer> diceRolls = createDiceRolls(3, 4, 5, 6, 6, 6);
        int res = getKniffelGame().getToAddScore(BookingType.SMALL_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(30, res);
    }

    @Test
    public void smallStraigt_no() {
        List<Integer> diceRolls = createDiceRolls(3, 4, 5, 5, 5, 5);
        int res = getKniffelGame().getToAddScore(BookingType.SMALL_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(0, res);
    }

    @Test
    public void largeStraight_with1() {
        List<Integer> diceRolls = createDiceRolls(1, 2, 3, 4, 5);
        int res = getKniffelGame().getToAddScore(BookingType.LARGE_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(40, res);
    }

    @Test
    public void largeStraight_with2() {
        List<Integer> diceRolls = createDiceRolls(2, 3, 4, 5, 6);
        int res = getKniffelGame().getToAddScore(BookingType.LARGE_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(40, res);
    }

    @Test
    public void largeStraight_no() {
        List<Integer> diceRolls = createDiceRolls(1, 2, 3, 4, 4);
        int res = getKniffelGame().getToAddScore(BookingType.LARGE_STRAIGHT, diceRolls, kniffelRules);
        Assertions.assertEquals(0, res);
    }

    @Test
    public void test_threeOfKind_success1() {
        List<Integer> diceRolls = Arrays.asList(1, 3, 6, 6, 6);
        int res = getKniffelGame().getToAddScore(BookingType.THREE_OF_A_KIND, diceRolls, kniffelRules);
        Assertions.assertEquals(18, res);
    }

    @Test
    public void test_threeOfKind_success2() {
        List<Integer> diceRolls = Arrays.asList(1, 3, 1, 1, 1);
        int res = getKniffelGame().getToAddScore(BookingType.THREE_OF_A_KIND, diceRolls, kniffelRules);
        Assertions.assertEquals(3, res);
    }

    @Test
    public void test_threeOfKind_no() {
        List<Integer> diceRolls = Arrays.asList(1, 3, 5, 6, 6);
        int res = getKniffelGame().getToAddScore(BookingType.THREE_OF_A_KIND, diceRolls, kniffelRules);
        Assertions.assertEquals(0, res);
    }

}
