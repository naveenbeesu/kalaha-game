package com.bol.kalahagame.service;

import com.bol.kalahagame.model.Constants;
import com.bol.kalahagame.model.KalahaGame;
import com.bol.kalahagame.model.Player;
import com.bol.kalahagame.model.Step;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
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
        KalahaGame currentGame = kalahaGameSetupServiceImpl.startGame(step.id);
        String currentPlayer = step.player;

        if (!step.player.equals(currentGame.getPresentPlayer())){
            log.info("Returning current game since its not requested player's turn, {}", step.player);
            return currentGame;
        }

        int pitIndex = step.pitIndex;

        if (currentPlayer.equals(Constants.PLAYER_ONE)) {
            //checks if player wins another turn with last stone in big pit else sets other player turn
            if (moveStonesOfSelectedPit(pitIndex, currentGame)) currentGame.setPresentPlayer(Constants.PLAYER_TWO);
        } else if (currentPlayer.equals(Constants.PLAYER_TWO)) {
            moveStonesInSecondPlayerPits(currentGame, pitIndex);
        }
        log.info("All stones of selected pit {} of currentPlayer {} are moved", pitIndex, currentPlayer);
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
        int currentIndex = pitIndex+1;
        while (stonesInPit > 0) {
            int[] oppositePits = oppositePlayer.stonesInPits;
            while (currentIndex < Constants.NUMBER_OF_PITS && stonesInPit > 0) {
                if (stonesInPit == 1 && presentPlayerPits[currentIndex] == 0) {
                    //validates and moves if last stone is in present player's empty pit along with stones in opposite pit opponent to big pit of present player
                    moveLastStoneInPresentPlayerPit(currentIndex, presentPlayer, oppositePits);
                    stonesInPit = 0;
                } else {
                    //adds stones in presentplayer pits
                    presentPlayerPits[currentIndex]++;
                    currentIndex++;
                    stonesInPit--;
                }
            }
            currentIndex = 0;
            if (stonesInPit > 0) {
                //adds stone in BigPit
                getsAnotherTurn = true;
                presentPlayer.stonesInBigPit++;
                stonesInPit--;
            }
            if (stonesInPit > 0) {
                //adds stones in opponent's pits
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

    private static void moveLastStoneInPresentPlayerPit(int currentIndex, Player presentPlayer, int[] oppositePits) {
        presentPlayer.stonesInBigPit += oppositePits[Constants.NUMBER_OF_PITS - currentIndex-1] + 1;
        oppositePits[Constants.NUMBER_OF_PITS - currentIndex-1] = 0;
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

        if (checkAllPitsEmpty(currentGame.player1.stonesInPits) || checkAllPitsEmpty(currentGame.player2.stonesInPits)) {
            int player1StonesInBigPit = currentGame.player1.stonesInBigPit + Arrays.stream(currentGame.player1.stonesInPits).sum();
            int player2StonesInBigPit = currentGame.player2.stonesInBigPit + Arrays.stream(currentGame.player2.stonesInPits).sum();

            if (player1StonesInBigPit < player2StonesInBigPit) {
                log.info("Winner is {}", Constants.PLAYER_TWO);
                currentGame.setWinnerOfGame(Constants.PLAYER_TWO);
            } else {
                log.info("Winner is {}", Constants.PLAYER_ONE);
                currentGame.setWinnerOfGame(Constants.PLAYER_ONE);
            }
        }
    }

    private boolean checkAllPitsEmpty(int[] stonesInPits) {
        for (int stonesInPit : stonesInPits) {
            if (stonesInPit != 0) return false;
        }
        return true;
    }
}
