package com.oglimmer.kniffel.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KniffelGameReRollDiceTest {
    
    private KniffelGame kniffelGame;
    
    @BeforeEach
    public void setUp() {
        List<KniffelPlayer> players = new ArrayList<>();
        players.add(new KniffelPlayer("Player1"));
        kniffelGame = new KniffelGame(players);
    }
    
    @Test
    public void testReRollDice_keepSpecificDice() {
        // Set up initial dice rolls
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 5, 6));
        kniffelGame.setRollRound(1);
        
        // Keep dice [1, 2, 3]
        int[] diceToKeep = {1, 2, 3};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify kept dice are present
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        Assertions.assertEquals(5, diceRolls.size());
        Assertions.assertTrue(diceRolls.contains(1));
        Assertions.assertTrue(diceRolls.contains(2));
        Assertions.assertTrue(diceRolls.contains(3));
        
        // Verify roll round incremented
        Assertions.assertEquals(2, kniffelGame.getRollRound());
        
        // Verify dice are sorted
        for (int i = 0; i < diceRolls.size() - 1; i++) {
            Assertions.assertTrue(diceRolls.get(i) <= diceRolls.get(i + 1));
        }
    }
    
    @Test
    public void testReRollDice_keepAllDice() {
        // Set up initial dice rolls
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 4, 5));
        kniffelGame.setRollRound(1);
        
        // Keep all dice
        int[] diceToKeep = {1, 2, 3, 4, 5};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify all dice are kept
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        Assertions.assertEquals(5, diceRolls.size());
        Assertions.assertTrue(diceRolls.contains(1));
        Assertions.assertTrue(diceRolls.contains(2));
        Assertions.assertTrue(diceRolls.contains(3));
        Assertions.assertTrue(diceRolls.contains(4));
        Assertions.assertTrue(diceRolls.contains(5));
        
        // Verify roll round incremented
        Assertions.assertEquals(2, kniffelGame.getRollRound());
    }
    
    @Test
    public void testReRollDice_keepNoDice() {
        // Set up initial dice rolls
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 4, 5));
        kniffelGame.setRollRound(1);
        
        // Keep no dice (null)
        kniffelGame.reRollDice(null);
        
        // Verify we still have 5 dice
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        Assertions.assertEquals(5, diceRolls.size());
        
        // Verify all dice values are between 1 and 6
        for (int die : diceRolls) {
            Assertions.assertTrue(die >= 1 && die <= 6);
        }
        
        // Verify roll round incremented
        Assertions.assertEquals(2, kniffelGame.getRollRound());
    }
    
    @Test
    public void testReRollDice_keepEmptyArray() {
        // Set up initial dice rolls
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 4, 5));
        kniffelGame.setRollRound(1);
        
        // Keep no dice (empty array)
        int[] diceToKeep = {};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify we still have 5 dice
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        Assertions.assertEquals(5, diceRolls.size());
        
        // Verify all dice values are between 1 and 6
        for (int die : diceRolls) {
            Assertions.assertTrue(die >= 1 && die <= 6);
        }
        
        // Verify roll round incremented
        Assertions.assertEquals(2, kniffelGame.getRollRound());
    }
    
    @Test
    public void testReRollDice_keepOneDie() {
        // Set up initial dice rolls
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 4, 5));
        kniffelGame.setRollRound(1);
        
        // Keep one die
        int[] diceToKeep = {3};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify kept die is present
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        Assertions.assertEquals(5, diceRolls.size());
        Assertions.assertTrue(diceRolls.contains(3));
        
        // Verify all dice values are between 1 and 6
        for (int die : diceRolls) {
            Assertions.assertTrue(die >= 1 && die <= 6);
        }
        
        // Verify roll round incremented
        Assertions.assertEquals(2, kniffelGame.getRollRound());
    }
    
    @Test
    public void testReRollDice_duplicateValues() {
        // Set up initial dice rolls with duplicates
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(2, 2, 2, 5, 6));
        kniffelGame.setRollRound(1);
        
        // Keep two of the duplicate dice
        int[] diceToKeep = {2, 2};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify we have 5 dice
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        Assertions.assertEquals(5, diceRolls.size());
        
        // Verify at least two 2's are present
        long count2s = diceRolls.stream().filter(die -> die == 2).count();
        Assertions.assertTrue(count2s >= 2);
        
        // Verify roll round incremented
        Assertions.assertEquals(2, kniffelGame.getRollRound());
    }
    
    @Test
    public void testReRollDice_thirdRollTriggersNextPhase() {
        // Set up game state for third roll
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 4, 5));
        kniffelGame.setRollRound(2);
        kniffelGame.setState(GameState.ROLL);
        
        // Keep some dice
        int[] diceToKeep = {1, 2};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify roll round is 3
        Assertions.assertEquals(3, kniffelGame.getRollRound());
        
        // Verify state changed to BOOK (nextPhase was called)
        Assertions.assertEquals(GameState.BOOK, kniffelGame.getState());
    }
    
    @Test
    public void testReRollDice_diceAlwaysSorted() {
        // Set up initial dice rolls
        kniffelGame.getDiceRolls().clear();
        kniffelGame.getDiceRolls().addAll(Arrays.asList(6, 1, 4, 2, 5));
        kniffelGame.setRollRound(1);
        
        // Keep some dice
        int[] diceToKeep = {6, 1};
        kniffelGame.reRollDice(diceToKeep);
        
        // Verify dice are sorted
        List<Integer> diceRolls = kniffelGame.getDiceRolls();
        for (int i = 0; i < diceRolls.size() - 1; i++) {
            Assertions.assertTrue(diceRolls.get(i) <= diceRolls.get(i + 1));
        }
    }
    
    @Test
    public void testReRollDice_alwaysHasFiveDice() {
        // Test multiple scenarios to ensure we always have 5 dice
        for (int keepCount = 0; keepCount <= 5; keepCount++) {
            kniffelGame.getDiceRolls().clear();
            kniffelGame.getDiceRolls().addAll(Arrays.asList(1, 2, 3, 4, 5));
            kniffelGame.setRollRound(1);
            
            int[] diceToKeep = new int[keepCount];
            for (int i = 0; i < keepCount; i++) {
                diceToKeep[i] = i + 1;
            }
            
            kniffelGame.reRollDice(diceToKeep);
            
            // Verify we always have exactly 5 dice
            Assertions.assertEquals(5, kniffelGame.getDiceRolls().size());
        }
    }
}