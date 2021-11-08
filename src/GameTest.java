import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class GameTest {
    Game game;
    Player p1;

// todo this BeforeEach doesn't work. Also tried @BeforeAll. didn't work.

    /*
    @BeforeEach
    public void init(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
    }
    */



    @Test
    public void testAddPlayer() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            game.addPlayer(p);
        }
        assertEquals(5, game.players.size());
    }

    // No player is bankrupt. CheckNumActivePlayers must return true.
    @Test
    public void testCheckNumOfActivePlayersCase2() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            game.addPlayer(p);
            game.setCurrentPlayer(p);
        }
        assertEquals(true, game.checkNumOfActivePlayers());
    }

    // 1 player is bankrupt. CheckNumActivePlayers must return true.
    @Test
    public void testCheckNumOfActivePlayersCase1() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            game.addPlayer(p);
            game.setCurrentPlayer(p);
        }
        Player pBankrupt  = new Player("PBankrupt", new Color(10,10,10), 1500);
        pBankrupt.setBankruptStatus(true);
        game.addPlayer(pBankrupt);
        game.setCurrentPlayer(pBankrupt);
        assertEquals(true, game.checkNumOfActivePlayers());
    }


    // All but one players (the winner) are bankrupt. CheckNumActivePlayers must return false.
    @Test
    public void testCheckNumOfActivePlayersCase3() {
        game = new Game();
        for (int i = 0; i < 5; i++) {
            Player p = new Player("P" + (i+1) , new Color(10,10,10),1500);
            p.setBankruptStatus(true);
            game.addPlayer(p);
            game.setCurrentPlayer(p);
        }
        Player pNotBankrupt  = new Player("PNotBankrupt", new Color(10,10,10), 1500);
        game.addPlayer(pNotBankrupt);
        game.setCurrentPlayer(pNotBankrupt);
        pNotBankrupt.setBankruptStatus(false);
        assertEquals(false, game.checkNumOfActivePlayers());
    }


    // testing if player moves forward on the board after rolling
    @Test
    public void testRollCase1(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        p1.setInJail(false);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.roll();
        assertNotEquals( 0,p1.getPositon());
    }

    //test case for when player is not in jail and rolls a double. roll() must return true.
    @Test
    public void testRollCase2(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setInJail(false);
        game.addPlayer(p1);

        game.setDiceValue1(2);
        game.setDiceValue2(2);

        Boolean actualValue = game.roll();
        while(game.getDiceValue1() != game.getDiceValue2()){
            actualValue = game.roll();
        }
        assertEquals( true, actualValue);
    }

    //test case for when player is in jail and rolls. Player should not move on the board and stay at Jail (position 10)
    @Test
    public void testRollCase3(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setPosition(10);
        p1.setInJail(true);
        game.addPlayer(p1);
        game.roll();

        assertEquals( 10, p1.getPositon());
    }


    @Test
    public void testOperateCommandRoll(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);

        game.operateCommand(Game.Commands.roll);
        assertNotEquals( 0,p1.getPositon());
    }

    // Test operateCommand pass turn when next player (p2) is Not Bankrupt
    @Test
    public void testOperateCommandPassTurn1(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);

        Player p2 = new Player("P2", new Color(20,20,20),1500);
        game.operateCommand(Game.Commands.roll);
        game.addPlayer(p2);
        game.operateCommand(Game.Commands.passTurn);
        assertEquals(p2,game.getCurrentPlayer());
    }

    // Test operateCommand pass turn when next player (p2) is Bankrupt
    @Test
    public void testOperateCommandPassTurn2(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);

        Player p2 = new Player("P2", new Color(20,20,20),1500);
        p2.setBankruptStatus(true);
        game.addPlayer(p2);
        Player p3 = new Player("P3", new Color(10,10,10),1500);
        game.addPlayer(p3);

        // p1 rolls , passes turn, p2 is bankrupt so p3 is next current player.
        game.operateCommand(Game.Commands.roll);
        game.operateCommand(Game.Commands.passTurn);
        assertEquals(p3,game.getCurrentPlayer());
    }

    @Test
    public void testPurchaseProperty(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setPosition(6);
        game.purchaseProperty();
        Boolean actualValue = (p1.getControlledProperties().get(0).getName().equals("Oriental Avenue")) &&(p1.getBalance() == 1400);
        assertEquals(true,actualValue);
    }

    @Test
    public void testOperateCommandPurchaseProperty(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setPosition(6);
        game.operateCommand(Game.Commands.purchaseProperty);
        Boolean actualValue = (p1.getControlledProperties().get(0).getName().equals("Oriental Avenue")) &&(p1.getBalance() == 1400);
        assertEquals(true,actualValue);
    }

    // Test purchaseHouseOrHotel command when player does not have color set (so no houses bought)
    @Test
    public void testPurchaseHouseOrHotel1(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setPosition(1);
        game.purchaseProperty();
        game.purchaseHouseOrHotel(game.getBoard().getProperty(1));
        assertEquals(0,game.getBoard().getProperty(1).getNumHouses());
    }

    // Test purchaseHouseOrHotel command when player does have color set (so 1 house bought)
    @Test
    public void testPurchaseHouseOrHotel2(){
        game = new Game();
        p1 = new Player("P1", new Color(10,10,10),1500);
        game.setCurrentPlayer(p1);
        p1.setPosition(1);
        game.purchaseProperty();
        p1.setPosition(3);
        game.purchaseProperty();
        game.purchaseHouseOrHotel(game.getBoard().getProperty(1));
        assertEquals(1,game.getBoard().getProperty(1).getNumHouses());
    }


}
