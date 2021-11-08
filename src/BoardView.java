import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * class BoardView implements the graphical user interface's view for the board game Monopoly.
 *
 * @author Mahtab Ameli, Imran Latif
 * @version 1.0
 */
public class BoardView {

    private Game game;

    private Boolean playersInitialized;

    private JLabel[] playerLabelList;

    private JLabel eventLabel;

    private JButton startButton;

    private int playerInitializeStage;

    private int MAX_PLAYERS;

    private JPanel[][] panelHolder;

    /**
     * main frame for the GUI's View.
     */
    private final JFrame frame;

    /**
     * an array of JPanels, one for each Property (40 in total).
     */
    private JPanel [] propertyPanels;

    /**
     * an array of JPanels for each Player (4 total).
     */
    private JPanel[] playerPanels;

    /**
     * this panel contains the board's propertyPanels.
     */
    private JPanel gamePanel;

    /**
     * this panel contains all control buttons and the message windows that appear by pressing them.
     */
    private JPanel controlPanel;


    /**
     * user can press this button to roll dice.
     */
    private JButton rollButton;

    /**
     * user can press this button to purchase a property if applicable.
     */
    private JButton purchaseButton;
    /**
     * USer can press this button to see the rules and user guide.
     */
    private JButton helpButton;

    /**
     * user can press this button to start a new game.
     */
    private JButton newGameButton;

    /**
     * user can press this button to quit the game.
     */
    private JButton quitButton;

    /**
     * User can press this to add or remove houses from a monopoly
     */
    private JButton modifyHouses;

    /**
     * user can press this button to pass the turn to the next player.
     */
    private JButton passButton;

    /**
     * Panels within the gamePanel.
     */
    private JPanel north, east, west, south, center;


    /**
     * constructor for the class.
     *
     */
    public BoardView(Game game) {
        this.frame = new JFrame("Welcome to G28's Monopoly!");
        this.frame.setVisible(true);
        this.frame.setResizable(true);
        this.game = new Game();
        this.playersInitialized = false;
        int totalNumberOfPlayers = 4;
        this.playerPanels = new JPanel[totalNumberOfPlayers];
        addBasePanels();
        this.playerInitializeStage = 0;
        this.MAX_PLAYERS = 0;
    }

    /**
     * Creates the mainPanel of the GUI frame. Adds gamePanel and controlPanel to the mainPanel.
     *
     */
    private void addBasePanels() {
         //mainPanel placed on the frame. Contains gamePanel and controlPanel.
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setSize(new Dimension(1100, 800));

        createGamePanel();
        createControlPanel();

        mainPanel.add(gamePanel, BorderLayout.WEST);
        //mainPanel.add(controlPanel, BorderLayout.EAST);

        frame.add(mainPanel);
        frame.revalidate();
    }

    /**
     * This helper funtion is used to create all the labels and properties on the grid.
     * @param direction The edge of the board we are creating labels on.
     * @param i Which property i we are adding.
     * @param propertyIndexLabels The list we are adding the labels to.
     */
    private void createPropertyPanelHelper(JPanel direction, int i, JLabel [] propertyIndexLabels){
        propertyPanels[i] = new JPanel(new BorderLayout());
        //setting a different background color for each propertyPanel to visually set them apart from each other for now
        propertyPanels[i].setBackground(game.getBoard().getProperty(i).getColor());
        //creating and adding an index label to each propertyPanel to see their order
        propertyIndexLabels[i] = new JLabel();
        propertyIndexLabels[i].setText(game.getBoard().getPropertiesArrayList().get(i).getName());
        propertyIndexLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
        propertyIndexLabels[i].setVerticalAlignment(SwingConstants.CENTER);
        propertyPanels[i].add(propertyIndexLabels[i]);
        direction.add(propertyPanels[i]);
    }



    /**
     * Creates 40 propertyPanels and adds them to gamePanel.
     *
     */
    private void createPropertyPanels() {
        // Create 40 panels, one for each property
        this.propertyPanels = new JPanel[40];

        //Create 40 JLabels, one for each propertyPanel
        JLabel [] propertyIndexLabels = new JLabel[40];

        for (int i = 0; i < 40; i++) {
            // Properties 0 to 10 are added to the south panel
            if (i < 11) {
                createPropertyPanelHelper(south, i, propertyIndexLabels);
            }
            // Properties 11 to 19 are added to the west panel.
            else if (i < 20) {
                createPropertyPanelHelper(west, 30 - i, propertyIndexLabels);
            }
            // Properties 20 to 30 are added to the north panel.
            else if (i < 31) {
                createPropertyPanelHelper(north, i, propertyIndexLabels);
            }
            // Properties 31 to 39 are added to the east panel.
            else {
                createPropertyPanelHelper(east, i,propertyIndexLabels);
            }
        }
    }

    /** creates gamePanel which contains all propertyPanels
     *
     */
    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
        gamePanel.setPreferredSize(size);
        gamePanel.setBackground(new Color(255, 255, 255));

        //gamePanel is made of 5  other panels: north, east, west, south, center
        north = new JPanel(new GridLayout(1,11));
        east = new JPanel(new GridLayout(9,1));
        west = new JPanel(new GridLayout(9,1));
        south = new JPanel(new GridLayout(1,11));
        center = new JPanel(new FlowLayout());

        north.setBackground(new Color(90,20,200));
        north.setPreferredSize(new Dimension(800,150));
        north.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        east.setBackground(new Color(100,140,10));
        east.setPreferredSize(new Dimension(150, 500));
        east.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        west.setBackground(new Color(0,200,150));
        west.setPreferredSize(new Dimension(150, 500));
        // todo: fix orientation of propertyPanels in the west panel
        west.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        south.setBackground(new Color(250,120,200));
        south.setPreferredSize(new Dimension(800,150));
        south.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


        // todo add more buttons later


        //todo handle buttons later

        //Button Handlers



        center.setBackground(new Color(190,250,250));
        createControlPanel();
        center.add(controlPanel);

        // Roll Dice button appears in center of the board
        rollButton = new JButton("Roll Dice");

        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean nextRoll = true;
                if(nextRoll && (game.getCurrentPlayer().getInJail() == false)) {
                    nextRoll = game.roll();
                    Properties propertyOn = game.getBoard().getProperty(game.getCurrentPlayer().getPositon());
                    if (!propertyOn.getOwner().equals(game.getCurrentPlayer())) {
                        propertyOn.payRent(game.getCurrentPlayer());
                    }
                }
                else{

                    System.out.println(game.getCurrentPlayer().getName()+" can NOT roll again. Pass your turn or buy property.");
                }
                if ((game.getCurrentPlayer().getInJail() == true)){
                    Boolean isDouble = game.roll();
                    if (isDouble && (game.getCurrentPlayer().getTurnsInJail() != 0)) {
                        game.getCurrentPlayer().setInJail(false);
                        game.getCurrentPlayer().setTurnsInJail(0);
                        System.out.println(game.getCurrentPlayer().getName() + " rolled a double and is out of jail.");
                    } else {
                        if (game.getCurrentPlayer().getTurnsInJail() == 3) {
                            game.getCurrentPlayer().removefromBalance(50);
                            game.getCurrentPlayer().setInJail(false);
                            game.getCurrentPlayer().setTurnsInJail(0);
                            System.out.println(game.getCurrentPlayer().getName() + " Payed $50 to get out of jail.");
                        }
                        game.getCurrentPlayer().setTurnsInJail(game.getCurrentPlayer().getTurnsInJail() + 1); //add 1 to time in jail for player.
                        // Manually pass turn. passPlayerTurn();
                    }
                }
                game.setHasCurrPlayerRolled(true);

            }
        });

        gamePanel.add(north, BorderLayout.NORTH);
        gamePanel.add(east, BorderLayout.EAST);
        gamePanel.add(west, BorderLayout.WEST);
        gamePanel.add(south, BorderLayout.SOUTH);
        gamePanel.add(center, BorderLayout.CENTER);

        createPropertyPanels();
    }

    /** creates controlPanel, which contains all controls and buttons
     * including purchase property, and quit game
     * todo: add a button that shows the players' states
     *       and other necessary buttons
     *
     */
    private void createControlPanel() {
        // controlPanel contains the buttons for starting a new game, purchasing property, and quiting the game.
        // The message windows that appear as a result of pressing these buttons will also appear on eastPanel
        int rows = 2;
        int columns = 3;
        controlPanel = new JPanel(new GridLayout(rows,columns));
        panelHolder = new JPanel[rows][columns];
        for(int m = 0; m < rows; m++) {
            for(int n = 0; n < columns; n++) {
                panelHolder[m][n] = new JPanel();
                controlPanel.add(panelHolder[m][n]);
            }
        }
        //todo fix size so that controlPanel in narrower than gamePanel but has same height. size currently incorrect.
        Dimension controlPanelSize = Toolkit.getDefaultToolkit().getScreenSize();
        controlPanelSize.setSize(controlPanelSize.getWidth()*0.8,controlPanelSize.getHeight()*0.5);
       controlPanel.setPreferredSize(controlPanelSize);
        controlPanel.setBackground(new Color(215, 200, 131, 255));

        // todo add more buttons later
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        startButton = new JButton("Start Game");
        rollButton = new JButton("Roll");
        newGameButton = new JButton("New Game");
        purchaseButton = new JButton("Purchase Property");
        quitButton = new JButton("Quit Game");
        passButton = new JButton("Pass turn");
        helpButton = new JButton("Help");
        modifyHouses = new JButton("Add or remove houses");

        //Button Handlers
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ask how many players?

                //for amount of players, ask name

                //Show what player starts first    show P.x = {boat, car,  dog, boot, penguin, hat}

                //Set middle board of player info V(player)
            }
        });

        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.purchaseProperty();

            }
        });

        quitButton.addActionListener(e -> System.exit(0));

        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //currPlayer -> next   make new var: currPlayerRolled? T/F
                if (game.getHasCurrPlayerRolled()){game.passPlayerTurn();}
                else{
                    if (JOptionPane.showConfirmDialog(frame, "You have not rolled yet. ")
                            == JOptionPane.OK_OPTION) {}
                }
                game.setHasCurrPlayerRolled(false);
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Alert w/ button desc, but like it is not needed for this milestonse said by the ta in the discord chat.
            }
        });


        modifyHouses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Type in the property name on which you would like to purchase a house/hotel on.");
                String propertyName =""; //TODO Get textinput JOption from new game to copy to here
                Boolean propertyExists = false;
                int propertyIndex = -1;
                for (int i = 0; i < game.getBoard().getPropertiesArrayList().size(); i++) {
                    if (game.getBoard().getProperty(i).getName().equals(propertyName)) {
                        propertyExists = true;
                        propertyIndex = i;
                    }
                }
                if (propertyExists) {
                    game.purchaseHouseOrHotel(game.getBoard().getProperty(propertyIndex));
                } else {
                    System.out.println("Property: " + propertyName + ", Does not exists");  //TODO Make alert
                }
            }
        });

        int totalNumPlayers = 4;
        for (int i=0; i<totalNumPlayers; i++){
            createNewPlayerPanel(i);
        }
        panelHolder[0][0].add(playerPanels[0]);
        panelHolder[1][0].add(playerPanels[1]);
        panelHolder[0][2].add(playerPanels[2]);
        panelHolder[1][2].add(playerPanels[3]);

        buttonPanel.add(startButton);
        buttonPanel.add(newGameButton);
        buttonPanel.add(rollButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(passButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(modifyHouses);
        panelHolder[0][1].add(buttonPanel);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                String player1= JOptionPane.showInputDialog("Enter the name of Player 1");
                String player2= JOptionPane.showInputDialog("Enter the name of Player 2");
                String player3= JOptionPane.showInputDialog("Enter the name of Player 3");
                String player4= JOptionPane.showInputDialog("Enter the name of Player 4");

                Player newPlayer1 = new Player(player1, new Color(20,20,100), 1500);
                game.addPlayer(newPlayer1);
                Player newPlayer2 = new Player(player2, new Color(100,20,20), 1500);
                game.addPlayer(newPlayer2);
                Player newPlayer3 = new Player(player3, new Color(20,100,20), 1500);
                game.addPlayer(newPlayer3);
                Player newPlayer4 = new Player(player4, new Color(100,100,0), 1500);
                game.addPlayer(newPlayer4);
            }
        });
        //todo handle buttons later

    }


    /**
     * Closes the main frame and prompts the user with a confirmation message beforehand.
     */
    private void closeFrame() {

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit the game?")
                        == JOptionPane.OK_OPTION) {
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Creates panels for player objects
     */
    private void createNewPlayerPanel(int playerIndex){
            int numOfLabels = 6;
            playerLabelList = new JLabel[numOfLabels];
            playerLabelList[0] = new JLabel("Name: ");
            playerLabelList[1] = new JLabel("Balance: $");
            playerLabelList[2] = new JLabel("Current location: ");
            playerLabelList[3] = new JLabel("In Jail Status = ");
            playerLabelList[4] = new JLabel("Bankrupt Status = ");
            String controledProperties = "";
            playerLabelList[5] = new JLabel("Owned Properties: " + controledProperties);
            playerPanels[playerIndex] = new JPanel();
            playerPanels[playerIndex].setLayout(new BoxLayout(playerPanels[playerIndex], BoxLayout.Y_AXIS));
            for (int k = 0; k < numOfLabels; k++) {
                playerPanels[playerIndex].add(playerLabelList[k]);
            }

    }

    /**
     * Update All Players Status
     */
    public void updateAllPlayersStatus(int numOfPlayers){
        System.out.println("Test 1");

            int numOfLabels = 6;
            playerLabelList = new JLabel[numOfLabels];
            for(int i =0; i < numOfPlayers; i++){
                System.out.println("Test 3");
                Player currentPlayer = game.getPlayer(i);
                playerLabelList[0] = new JLabel("Name: "+currentPlayer.getName() );
                System.out.println(currentPlayer.getName());
                playerLabelList[1] = new JLabel("Balance: $"+currentPlayer.getBalance());
                System.out.println(currentPlayer.getBalance());
                playerLabelList[2] = new JLabel("Current location: "+game.getBoard().getProperty(currentPlayer.getPositon()).getName());
                playerLabelList[3] = new JLabel("In Jail Status = "+currentPlayer.getInJail());
                playerLabelList[4] = new JLabel("Bankrupt Status = "+currentPlayer.getBankruptStatus());
                String controledProperties = "";
                for(int j = 0 ; j < currentPlayer.getControlledProperties().size(); j++){
                    controledProperties += "- "+ currentPlayer.getControlledProperties().get(j).getName() + "\n";
                }
                playerLabelList[5] = new JLabel("Owned Properties: "+controledProperties);

                for(int k = 0; k < numOfLabels; k++){
                    playerPanels[i].removeAll();
                    playerPanels[i].add(playerLabelList[k]);
                }
            }

    }

    /**
     * displays the GUI of the game.
     */
    public void displayGUI(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size);
        closeFrame();
    }

    public void setEventLabelText(String eventText) {
        eventLabel.setText(eventText);
    }

}
