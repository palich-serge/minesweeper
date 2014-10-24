/*
 * 	The window that the game will take place in 
 */

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

public class MinesweeperWindow
{
	
	private JFrame frame = null;
	private String difficulty;
	private ImageIcon mine_icon = null;
	private Minesweeper ms;
	public int bestBeg = 999;
	public String bestBegName = "Anonymous",
					bestIntName = "Anonymous",
					bestExpName = "Anonymous";
	
	public MinesweeperWindow()
	{	//default constructor
		difficulty = "Beginner";
		newGame();
	}		
	
	public void newGame()
	{
			
		frame = new JFrame("Minesweeper");

		mine_icon = new ImageIcon("mineicon.png");
		frame.setIconImage((mine_icon).getImage());		
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	     
	    frame.setVisible(true);
    	frame.setResizable(false);
    	frame.setLocation((int)screenSize.getWidth()/2 - 200, (int)screenSize.getHeight()/2 - 200);
	    
		ms = new Minesweeper(difficulty);
		
		frame.setJMenuBar(createMenuBar());
		
	    frame.setContentPane(ms);
	    frame.pack();
	    ms.smiley.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				endGame();
				newGame();
				
			}
		});

		ms.bestBeg = bestBeg;		
		ms.bestBegName = bestBegName;			   
	}

	public JMenuBar createMenuBar()
	{	//create menu bar
		JMenuBar menuBar = new JMenuBar(); //allocate new JMenuBar
		
		//create two menus, Game and Help
		JMenu gameMenu = new JMenu("Game");
		JMenu helpMenu = new JMenu("Help"); //disabled, we don't need it
		
		//create the items on the menus
		JMenuItem newGame = new JMenuItem("New");
		JMenuItem bestTimes = new JMenuItem("Best Times...");
		JMenuItem exit = new JMenuItem("Exit");		
		JMenuItem about = new JMenuItem("About Minesweeper...");			
		
		//set the hotkeys for the items that have them
		newGame.setAccelerator(KeyStroke.getKeyStroke("F2"));		
		
		//create checkbox menu items
		final JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Beginner", false);		
		
		//add menu items to appropriate menu in correct order
		gameMenu.add(newGame);
		gameMenu.addSeparator();
		gameMenu.add(beginner);		
		gameMenu.addSeparator();
		gameMenu.add(bestTimes);
		gameMenu.addSeparator();
		gameMenu.add(exit);		
		helpMenu.add(about);

		//add listeners to the menu items
		
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                endGame();
                newGame();
            }
        });		

        beginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {                
                difficulty = "Beginner";
                endGame();
                newGame();
            }
        });
                
        bestTimes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	JOptionPane.showMessageDialog(null, "Fastest Minesweepers\n\n" +
            			"Beginner: " + ms.bestBeg + " seconds" + " " + ms.bestBegName + "\n");
            }
        });		
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	JOptionPane.showMessageDialog(null,"The best Minesweeper ever created\n");
            }
        });               
		
		//add the menus to the menu bar
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		return menuBar;
	}	
	
	private void endGame()
	{	//get new high scores and scrap current frame
		bestBeg = ms.bestBeg;		
		bestBegName = ms.bestBegName;
		frame.dispose();
	}
	
	public static void main( String args[] )
	{		
		MinesweeperWindow msw = new MinesweeperWindow();					
	}
	
}
