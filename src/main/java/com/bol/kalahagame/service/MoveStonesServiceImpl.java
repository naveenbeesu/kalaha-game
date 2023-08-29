package com.bol.kalahagame.service;

import com.bol.kalahagame.dto.Constants;
import com.bol.kalahagame.dto.KalahaGame;
import com.bol.kalahagame.dto.Player;
import com.bol.kalahagame.dto.Step;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MoveStonesServiceImpl implements MoveStonesService {

    private final KalahaGameSetupServiceImpl kalahaGameSetupServiceImpl;

    public MoveStonesServiceImpl(KalahaGameSetupServiceImpl kalahaGameSetupServiceImpl) {
        this.kalahaGameSetupServiceImpl = kalahaGameSetupServiceImpl;
    }


    /**
     * @param step object consists of game id, pit index and present player
     * @return KalahaGame object
     */
    @Override
    public KalahaGame moveStones(Step step) {
        KalahaGame currentGame = kalahaGameSetupServiceImpl.startGame(step.id());
        String currentPlayer = step.player();

        if (!step.player().equals(currentGame.getPresentPlayer())) return currentGame;

        int pitIndex = step.pitIndex();

        if (currentPlayer.equals(Constants.PLAYER_ONE)) {
            if (moveStonesOfSelectedPit(pitIndex, currentGame)) currentGame.setPresentPlayer(Constants.PLAYER_TWO);
        } else if (currentPlayer.equals(Constants.PLAYER_TWO)) {
            moveStonesInSecondPlayerPits(currentGame, pitIndex);
        }
        selectWinner(currentGame);
        return currentGame;
    }

    private boolean moveStonesOfSelectedPit(int pitIndex, KalahaGame currentGame) {
        Player presentPlayer = currentGame.player1;
        Player oppositePlayer = currentGame.player2;

        boolean getsAnotherTurn = false;
        int[] presentPlayerPits = presentPlayer.getStonesInPits();
        int stonesInPit = presentPlayerPits[pitIndex];
        presentPlayerPits[pitIndex] = 0;
        if (stonesInPit == 0) return false;
        int[] oppositePits = oppositePlayer.stonesInPits;
        while (stonesInPit > 0) {
            while (pitIndex < Constants.NUMBER_OF_PITS && stonesInPit > 0) {
                if (stonesInPit == 1 && presentPlayerPits[pitIndex] == 0) {
                    moveLastStoneInPresentPlayerPit(pitIndex, presentPlayer, oppositePits, presentPlayerPits);
                } else {
                    presentPlayerPits[pitIndex]++;
                    stonesInPit--;
                    pitIndex++;
                }
            }
            if (stonesInPit > 0) {
                getsAnotherTurn = true;
                presentPlayer.stonesInBigPit++;
                stonesInPit--;
            }
            if (stonesInPit > 0) {
                getsAnotherTurn = false;
                int presentIndex = 0;
                int targetIndex = Math.min(stonesInPit, Constants.NUMBER_OF_PITS);
                while (presentIndex < targetIndex && stonesInPit > 0) {
                    oppositePits[presentIndex]++;
                    stonesInPit--;
                    presentIndex++;
                }
                oppositePlayer.setStonesInPits(oppositePits);
            }
            presentPlayer.setStonesInPits(presentPlayerPits);
        }
        currentGame.setPlayer1(presentPlayer);
        currentGame.setPlayer2(oppositePlayer);
        return !getsAnotherTurn;
    }

    private static void moveLastStoneInPresentPlayerPit(int pitIndex, Player presentPlayer, int[] oppositePits, int[] presentPlayerPits) {
        presentPlayer.stonesInBigPit += oppositePits[pitIndex];
        presentPlayer.stonesInBigPit += presentPlayerPits[pitIndex];
        presentPlayerPits[pitIndex] = 0;
        oppositePits[pitIndex] = 0;
    }

    private void moveStonesInSecondPlayerPits(KalahaGame currentGame, int pitIndex) {
        Player firstPlayer = currentGame.player1;
        Player secondPlayer = currentGame.player2;
        currentGame.setPlayer1(secondPlayer);
        currentGame.setPlayer2(firstPlayer);

        if (moveStonesOfSelectedPit(pitIndex, currentGame)) {
            currentGame.setPresentPlayer(Constants.PLAYER_ONE);
        }

        firstPlayer = currentGame.player2;
        secondPlayer = currentGame.player1;
        currentGame.setPlayer1(firstPlayer);
        currentGame.setPlayer2(secondPlayer);
    }

    private void selectWinner(KalahaGame currentGame) {

        if (areAllPitsEmpty(currentGame.player1.stonesInPits) || areAllPitsEmpty(currentGame.player2.stonesInPits)) {
            int player1StonesInBigPit = currentGame.player1.stonesInBigPit + Arrays.stream(currentGame.player1.stonesInPits).sum();
            int player2StonesInBigPit = currentGame.player2.stonesInBigPit + Arrays.stream(currentGame.player2.stonesInPits).sum();

            if (player1StonesInBigPit < player2StonesInBigPit) {
                currentGame.setWinnerOfGame(Constants.PLAYER_TWO);
            } else {
                currentGame.setWinnerOfGame(Constants.PLAYER_ONE);
            }
        }
    }

    private boolean areAllPitsEmpty(int[] stonesInPits) {
        for (int stonesInPit : stonesInPits) {
            if (stonesInPit != 0) return false;
        }
        return true;
    }
}
