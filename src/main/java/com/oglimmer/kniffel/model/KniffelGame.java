package com.oglimmer.kniffel.model;

import com.oglimmer.kniffel.service.AppContextUtil;
import com.oglimmer.kniffel.service.KniffelRules;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class KniffelGame {

    /** player name to player object mapping */ 
    private final Map<String, KniffelPlayer> players;

    /** secret game id, knowing this id allows a client to retrieve/alter data */
    private final String gameId;

    /**  in each turn each player can re-roll the dice twice. round keeps track of re-rolls
     * round = 1 : initial roll (happens automatically)
     * round = 2 : first re-roll
     * round = 3 : second re-roll
     */
    private int rollRound;

    private KniffelPlayer currentPlayer;
    private GameState state = GameState.ROLL;

    /** holds the rolled dice values for the current player */
    private List<Integer> diceRolls = new ArrayList<>();

    /**
     * Creates a new KniffelGame for a certain number of players and start the game, but doing the first dice
     * roll for the starting player.
     *
     * @param players defines the player names
     */
    public KniffelGame(List<KniffelPlayer> players) {
        this.gameId = UUID.randomUUID().toString().replaceAll("-", "");
        this.players = new HashMap<>();
        for (KniffelPlayer player : players) {
            this.players.put(player.getName(), player);
        }
        currentPlayer = players.get(0);
        rollRound = 0;
        reRollDice(null);
    }

    /**
     * Re-rolls all, some or no dice.
     *
     * @param diceToKeep the dice value to keep, so it only re-rolls the other dice, may be null or empty
     */
    public void reRollDice(int[] diceToKeep) {
        removeDice(diceRolls, diceToKeep);
        while(diceRolls.size() < 5) {
            diceRolls.add((int) (Math.random() * 6) + 1);
        }
        diceRolls.sort(Comparator.naturalOrder());
        rollRound++;
        if (rollRound == 3) {
            nextPhase(GameState.BOOK);
        }
    }

    /**
     * Books the current dice into a booking type. Each booking type must only be used once.
     * This method uses a Spring registered bean named "kniffelRules" implementing the KniffelRules
     *
     * @param bookingType to use for this dice roll
     */
    public void bookDiceRoll(BookingType bookingType) {
        if (currentPlayer.getUsedBookingTypes().contains(bookingType)) {
            throw new IllegalStateException("BookingType already used");
        }
        KniffelRules kniffelRules = AppContextUtil.getKniffelRules();
        int toAddScore = getToAddScore(bookingType, diceRolls, kniffelRules);
        currentPlayer.setScore(currentPlayer.getScore() + toAddScore);
        currentPlayer.getUsedBookingTypes().add(bookingType);
        nextPhase(GameState.ROLL);
    }

    private void nextPhase(GameState state) {
        this.state = state;
        if (state == GameState.ROLL) {
            this.currentPlayer = findNextPlayer();
            this.rollRound = 0;
            reRollDice(null);
        }
    }

    void removeDice(List<Integer> diceRolls, int[] diceToKeep) {
        List<Integer> toKeepList = diceToKeep == null ? new ArrayList<>() : new ArrayList<>(Arrays.stream(diceToKeep).boxed().toList());
        for (Iterator<Integer> iterator = diceRolls.iterator(); iterator.hasNext(); ) {
            Integer diceVal = iterator.next();
            int idx = toKeepList.indexOf(diceVal);
            if (idx == -1) {
                iterator.remove();
            } else {
                toKeepList.remove(idx);
            }
        }
    }

    private KniffelPlayer findNextPlayer() {
        Iterator<KniffelPlayer> iterator = players.values().iterator();
        while (iterator.hasNext()) {
            KniffelPlayer player = iterator.next();
            if (player.equals(currentPlayer)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                } else {
                    rollRound++;
                    return players.values().iterator().next();
                }
            }
        }
        throw new IllegalStateException("No next player found");
    }

    int getToAddScore(BookingType bookingType, List<Integer> diceRolls, KniffelRules kniffelRules) {
        switch (bookingType) {
            case ONES:
                return kniffelRules.getScore1To6(diceRolls, 1);
            case TWOS:
                return kniffelRules.getScore1To6(diceRolls, 2);
            case THREES:
                return kniffelRules.getScore1To6(diceRolls, 3);
            case FOURS:
                return kniffelRules.getScore1To6(diceRolls, 4);
            case FIVES:
                return kniffelRules.getScore1To6(diceRolls, 5);
            case SIXES:
                return kniffelRules.getScore1To6(diceRolls, 6);
            case THREE_OF_A_KIND:
                return kniffelRules.getScoreForXofAKind(diceRolls, 3);
            case FOUR_OF_A_KIND:
                return kniffelRules.getScoreForXofAKind(diceRolls, 4);
            case KNIFFEL:
                return kniffelRules.getScoreKniffel(diceRolls);
            case FULL_HOUSE:
                return kniffelRules.getScoreFullHouse(diceRolls);
            case SMALL_STRAIGHT:
                return kniffelRules.getScoreSmallStraight(diceRolls);
            case LARGE_STRAIGHT:
                return kniffelRules.getScoreLargeStraight(diceRolls);
            case CHANCE:
                return kniffelRules.getScoreChance(diceRolls);
        }
        throw new RuntimeException();
    }    
}
