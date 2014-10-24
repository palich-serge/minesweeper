/*
 * 	The play area.  Has a grid of squares and a top area
 * 	that includes a restart button, mine counter and timer
 */

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Minesweeper extends JPanel
{

	private static final long serialVersionUID = 1L;
	private Square field[][];
	private int height, width, mines;
	private int timer, minect;
	private ImageIcon xmine, flag, smiley_normal = new ImageIcon("Images/smiley.jpg");
	private ImageIcon smiley_nervous = new ImageIcon("Images/nervoussmiley.jpg");
	private ImageIcon smiley_cool = new ImageIcon("Images/coolsmiley.jpg");
	private ImageIcon smiley_pressed = new ImageIcon("Images/depressedsmiley.jpg");
	private ImageIcon smiley_dead = new ImageIcon("Images/deadsmiley.jpg");
	private ImageIcon qmark = new ImageIcon("Images/qmark.jpg");
	private ImageIcon cover = new ImageIcon("Images/cover.jpg");
	private JPanel smileyBtn = null, minefield = null, minectpanel, timerpanel, topArea;
	public JButton smiley = null;
	private boolean timerstarted, minesSet, gameOver;
	private Timer t;
	public int bestBeg;
	public String bestBegName;
	private String diff;
	private ImageIcon digit_0, digit_1, digit_2, digit_3, digit_4;
	private ImageIcon digit_5, digit_6, digit_7, digit_8, digit_9, neg;
	private JLabel tnum1, tnum2, tnum3, mnum1, mnum2, mnum3;
	
	public Minesweeper(String difficulty)
	{ //parameterized constructor (standard difficulties)
		
		diff = difficulty;
		if (difficulty.equals("Beginner"))
		{
			height = 9;
			width = 9;
			mines = 10;			
		}		
		
		createField();
		startNewGame();

	}	
	
	public Minesweeper( int h, int w, int m, int ct)
	{
		height = h;
		width = w;
		mines = m;
		createField();
		startNewGame();
		timer = ct;
		UpdateTimerDisplay();		
	}
	
	public void startNewGame()
	{
		LoadImages();		
		gameOver = false;
		setBackground(Color.lightGray);
		setLayout(new BorderLayout());
		timerpanel = new JPanel();
		timerpanel.setBackground(Color.black);
		SetupMineCount();
		SetupTimerButtons();
		timerpanel.setLayout(new GridLayout(1,3));
		timerpanel.add(tnum1);
		timerpanel.add(tnum2);
		timerpanel.add(tnum3);
		timerpanel.setPreferredSize(new Dimension(50,15));
		timerpanel.setBackground(Color.lightGray);
		timerpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		minectpanel = new JPanel();
		minectpanel.setBackground(Color.lightGray);
		minectpanel.setLayout(new GridLayout(1,3));
		minectpanel.add(mnum1);
		minectpanel.add(mnum2);
		minectpanel.add(mnum3);
		minectpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		minectpanel.setPreferredSize(new Dimension(50,15));
		topArea = new JPanel();
		topArea.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(5, 5, 0, 5),
				BorderFactory.createLoweredBevelBorder()));
		topArea.setPreferredSize(new Dimension(100,50));
		topArea.setLayout(new BorderLayout());
		topArea.add(minectpanel, BorderLayout.WEST);
		topArea.add(getSmileyBtn(), BorderLayout.CENTER);
		topArea.add(timerpanel, BorderLayout.EAST);
		topArea.setBackground(Color.lightGray);
		add(topArea, BorderLayout.CENTER);
		add(getMinefield(), BorderLayout.SOUTH);
		setBorder(BorderFactory.createRaisedBevelBorder());
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!gameOver && e.getButton() == 1)
					smiley.setIcon(smiley_nervous);
			}
			public void mouseReleased(MouseEvent e) {
				if (!gameOver)
					smiley.setIcon(smiley_normal);
			}
		});

		t = new javax.swing.Timer(1000, new ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	              if (timer < 999)
	            	  timer++;
	              UpdateTimerDisplay();
	          }
	       });
		timerstarted = false;
		minesSet = false;
	}
	
	private void UpdateMineCountDisplay()
	{
		switch (Math.abs(minect%10)){
	              case 0: mnum3.setIcon(digit_0); break;
	              case 1: mnum3.setIcon(digit_1); break;
	              case 2: mnum3.setIcon(digit_2); break;
	              case 3: mnum3.setIcon(digit_3); break;
	              case 4: mnum3.setIcon(digit_4); break;
	              case 5: mnum3.setIcon(digit_5); break;
	              case 6: mnum3.setIcon(digit_6); break;
	              case 7: mnum3.setIcon(digit_7); break;
	              case 8: mnum3.setIcon(digit_8); break;
	              case 9: mnum3.setIcon(digit_9); break;
	              default: mnum3.setIcon(digit_0); break;
	              }
	              switch (Math.abs(minect%100 / 10)){
	              case 0: mnum2.setIcon(digit_0); break;
	              case 1: mnum2.setIcon(digit_1); break;
	              case 2: mnum2.setIcon(digit_2); break;
	              case 3: mnum2.setIcon(digit_3); break;
	              case 4: mnum2.setIcon(digit_4); break;
	              case 5: mnum2.setIcon(digit_5); break;
	              case 6: mnum2.setIcon(digit_6); break;
	              case 7: mnum2.setIcon(digit_7); break;
	              case 8: mnum2.setIcon(digit_8); break;
	              case 9: mnum2.setIcon(digit_9); break;
	              default: mnum2.setIcon(digit_0); break;
	              }
	              switch (Math.abs(minect%1000 / 100)){
	              case 0: mnum1.setIcon(digit_0); break;
	              case 1: mnum1.setIcon(digit_1); break;
	              case 2: mnum1.setIcon(digit_2); break;
	              case 3: mnum1.setIcon(digit_3); break;
	              case 4: mnum1.setIcon(digit_4); break;
	              case 5: mnum1.setIcon(digit_5); break;
	              case 6: mnum1.setIcon(digit_6); break;
	              case 7: mnum1.setIcon(digit_7); break;
	              case 8: mnum1.setIcon(digit_8); break;
	              case 9: mnum1.setIcon(digit_9); break;
	              default: mnum1.setIcon(digit_0); break;
	              }
	              
	              if (minect < 0)
	            	  mnum1.setIcon(neg);
	}
	
	private void UpdateTimerDisplay()
	{
		switch (timer%10){
	              case 0: tnum3.setIcon(digit_0); break;
	              case 1: tnum3.setIcon(digit_1); break;
	              case 2: tnum3.setIcon(digit_2); break;
	              case 3: tnum3.setIcon(digit_3); break;
	              case 4: tnum3.setIcon(digit_4); break;
	              case 5: tnum3.setIcon(digit_5); break;
	              case 6: tnum3.setIcon(digit_6); break;
	              case 7: tnum3.setIcon(digit_7); break;
	              case 8: tnum3.setIcon(digit_8); break;
	              case 9: tnum3.setIcon(digit_9); break;
	              default: tnum3.setIcon(digit_0); break;
	              }
	              switch (timer%100 / 10){
	              case 0: tnum2.setIcon(digit_0); break;
	              case 1: tnum2.setIcon(digit_1); break;
	              case 2: tnum2.setIcon(digit_2); break;
	              case 3: tnum2.setIcon(digit_3); break;
	              case 4: tnum2.setIcon(digit_4); break;
	              case 5: tnum2.setIcon(digit_5); break;
	              case 6: tnum2.setIcon(digit_6); break;
	              case 7: tnum2.setIcon(digit_7); break;
	              case 8: tnum2.setIcon(digit_8); break;
	              case 9: tnum2.setIcon(digit_9); break;
	              default: tnum2.setIcon(digit_0); break;
	              }
	              switch (timer%1000 / 100){
	              case 0: tnum1.setIcon(digit_0); break;
	              case 1: tnum1.setIcon(digit_1); break;
	              case 2: tnum1.setIcon(digit_2); break;
	              case 3: tnum1.setIcon(digit_3); break;
	              case 4: tnum1.setIcon(digit_4); break;
	              case 5: tnum1.setIcon(digit_5); break;
	              case 6: tnum1.setIcon(digit_6); break;
	              case 7: tnum1.setIcon(digit_7); break;
	              case 8: tnum1.setIcon(digit_8); break;
	              case 9: tnum1.setIcon(digit_9); break;
	              default: tnum1.setIcon(digit_0); break;
	              }
	}
	
	private void SetupMineCount()
	{
		
		mnum1 = new JLabel();
		mnum2 = new JLabel();
		mnum3 = new JLabel();
		mnum1.setPreferredSize(new Dimension(16,20));
		mnum2.setPreferredSize(new Dimension(16,20));
		mnum3.setPreferredSize(new Dimension(16,20));
		UpdateMineCountDisplay();
	}
	
	private void SetupTimerButtons()
	{
		
		tnum1 = new JLabel();
		tnum2 = new JLabel();
		tnum3 = new JLabel();
		tnum1.setPreferredSize(new Dimension(16,20));
		tnum2.setPreferredSize(new Dimension(16,20));
		tnum3.setPreferredSize(new Dimension(16,20));
		tnum1.setIcon(digit_0);
		tnum2.setIcon(digit_0);
		tnum3.setIcon(digit_0);
	
	}
	
	public void LoadImages()
	{
		digit_0 = new ImageIcon("Images/0.gif");
		digit_1 = new ImageIcon("Images/1.gif");
		digit_2 = new ImageIcon("Images/2.gif");
		digit_3 = new ImageIcon("Images/3.gif");
		digit_4 = new ImageIcon("Images/4.gif");
		digit_5 = new ImageIcon("Images/5.gif");
		digit_6 = new ImageIcon("Images/6.gif");
		digit_7 = new ImageIcon("Images/7.gif");
		digit_8 = new ImageIcon("Images/8.gif");
		digit_9 = new ImageIcon("Images/9.gif");
		neg = new ImageIcon("Images/neg.gif");
	}
	
	public JPanel getSmileyBtn()
	{
		if (smileyBtn == null)
		{
			smileyBtn = new JPanel();
			smileyBtn.add(getSmiley());
			smileyBtn.setBackground(Color.lightGray);
		}
		
		return smileyBtn;
	}
	
	public JButton getSmiley()
	{
		if (smiley == null)
		{
			smiley = new JButton();
			smiley.setPreferredSize(new Dimension (28,28));
			smiley.setIcon(smiley_normal);
			smiley.setPressedIcon(smiley_pressed);
		}
		
		return smiley;
	}
	
	public JPanel getMinefield()
	{
		if (minefield == null)
		{
			minefield = new JPanel();
            GridLayout gridLayout = new GridLayout();
            gridLayout.setRows(height);
            gridLayout.setColumns(width);
            minefield.setLayout(gridLayout);
            minefield.setBorder(BorderFactory.createCompoundBorder(
            		BorderFactory.createEmptyBorder(5, 5, 5, 5),
    				BorderFactory.createLoweredBevelBorder()));
            minefield.setBackground(Color.lightGray);
            for (int i=1; i<height+1; i++){
            	for (int j=1; j<width+1; j++){
            		minefield.add(field[i][j]);
            	}
            }
		}
		
		return minefield;		
	}
	
	public void createField()
	{
		minect = mines;
		timer = 0;

		field = new Square[height+2][width+2];
		
		xmine = new ImageIcon("Images/xmine.jpg");
		flag = new ImageIcon("Images/flag.jpg");
		
		for (int i=0; i<height+2; i++){
			for (int j=0; j<width+2; j++){
				
				field[i][j] = new Square();
				final int a = i;
				final int b = j;
				field[i][j].addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						if (!gameOver)
							smiley.setIcon(smiley_normal);
					}
					public void mousePressed(MouseEvent e) {
						if (e.getButton() == 1 && !gameOver)
							smiley.setIcon(smiley_nervous);
						int bothMask = MouseEvent.BUTTON1_DOWN_MASK | MouseEvent.BUTTON3_DOWN_MASK;
					    if ((e.getModifiersEx() & bothMask) == bothMask){
					        if (!field[a][b].isCovered() && !gameOver){
					        	int numFlags = 0;
					        	for (int p=-1; p<2; p++){
					        		for (int q=-1; q<2; q++){
					        			if (field[a+p][b+q].isFlagged())
					        				numFlags++;
					        		}
					        	}
					        	if (numFlags == field[a][b].getNumber()){
					        		for (int p=-1; p<2; p++){
						        		for (int q=-1; q<2; q++){
						        			if (!field[a+p][b+q].isFlagged()){
						        				rippleUncover(a+p,b+q);
						        				if (field[a+p][b+q].hasMine()){
						        					Boom(a+p,b+q);
						        				}
						        				if (checkWin())
						        					Win();
						        			}
						        		}
						        	}
					        	}
					        }
					    	
					    }
						if (field[a][b].isClickable() && e.getButton() == 3 && 
								!((e.getModifiersEx() & bothMask) == bothMask) &&
								(field[a][b].isEnabled() || field[a][b].isFlagged())){	
						
							//for right clicks set empty squares to flags, flagged square to
							//qmark (if enabled: empty otherwise) and qmarks to empty
							
							if (!field[a][b].isFlagged() && !field[a][b].isQmarked()){
								field[a][b].setEnabled(false);
								field[a][b].setDisabledIcon(flag);
								field[a][b].setFlagged(true);
								minect--;
								UpdateMineCountDisplay();
							}
							else if ((!field[a][b].isQmarked()) && field[a][b].isQon()){
								field[a][b].setEnabled(true);
								field[a][b].setIcon(qmark);
								field[a][b].setFlagged(false);
								field[a][b].setQmarked(true);
								minect++;
								UpdateMineCountDisplay();
							}
							else {
								field[a][b].setEnabled(true);
								field[a][b].setIcon(cover);
								field[a][b].setQmarked(false);
								if (field[a][b].isFlagged()){
									minect++;
									UpdateMineCountDisplay();
								}
								field[a][b].setFlagged(false);
							}
							UpdateMineCountDisplay();
						}
					}
			    });
				
				field[i][j].addActionListener(new ActionListener(){ //add listener to each square on field
					public void actionPerformed(ActionEvent e) { //on square clicked
						
						if (!timerstarted){ //on first click start timer and increase it to 1
							timer++;
							UpdateTimerDisplay();
							t.start();
							timerstarted = true;
						}
						
						if (!minesSet){//on first click, set mines on field
							minesSet = true;
							setMines(a,b);
						}
						
						if (!field[a][b].isFlagged()){

							rippleUncover(a,b);
							if (field[a][b].hasMine()) //if mine clicked
							{
								Boom(a,b);
							}
							if (checkWin()){ //if game is won
								Win();
							}
						}
					}
				});
			}
		}
		

	}
	
	private void Win()
	{
		t.stop();//stop timer
		timerstarted = false;
		gameOver = true;
		minect = 0; //set mine count to 0
		UpdateMineCountDisplay();
		
		//disable all buttons on field, flag unflagged mines
		for (int p=1; p<height+1; p++){
			for (int q=1; q<width+1; q++){
				field[p][q].setClickable(false);
				if (field[p][q].hasMine()){
					field[p][q].setEnabled(false);
					field[p][q].setDisabledIcon(flag);
				}
			}
		}
		//set restart button to cool smiley (with sunglasses)
		smiley.setIcon(smiley_cool);
		
		//if time better than current best time, prompt for name and show top scores
		if (diff.equals("Beginner") && timer < bestBeg){
			bestBeg = timer;
			bestBegName = JOptionPane.showInputDialog("You have the fastest time\n" +
					"for beginner level.\n" +
					"Please enter your name.\n\n\n");
			JOptionPane.showMessageDialog(null, "Fastest Minesweepers\n\n" +
        			"Beginner: " + bestBeg + " seconds" + " " + bestBegName + "\n");
		}		
	}
	
	private void Boom(int a, int b)
	{
		gameOver = true;
		t.stop(); //stop timer
		timerstarted = false;
		smiley.setIcon(smiley_dead);
		//display all mines, disable all squares
		for (int m=1; m<height+1; m++){
			for (int n=1; n<width+1; n++){
				field[m][n].setEnabled(false);
				if (field[m][n].hasMine() && !field[m][n].isFlagged())
					field[m][n].Uncover();
				if (field[m][n].isFlagged() && !field[m][n].hasMine())
					field[m][n].setDisabledIcon(xmine);
				if (field[m][n].isFlagged())
					field[m][n].setClickable(false);
			}
		}
		field[a][b].Trigger();
	}
	
	private void setMines(int cura, int curb)
	{	//set mines, don't plant one where player initially clicked
		Random rand = new Random();
		int minex, miney;
		
		for (int i=0; i<mines; i++){
			minex = rand.nextInt(width);
			miney = rand.nextInt(height);
			if (minex+1 != curb || miney+1 != cura){
				if (field[miney+1][minex+1].hasMine())
				{
					i--; //if mine location repeated don't increment counter
				}
				field[miney+1][minex+1].Arm();
			}
			else {
				i--;
			}
		}

		int num;

		//once mines planted figure out how many mines are in
		//neighboring squares for each square
		
		for (int i=0; i<height+2; i++){
			for (int j=0; j<width+2; j++){
				num = 0;
				if (i!=0 && i!=(height+1) && j!=0 && j!=(width+1)){
				
					for (int a=-1; a<2; a++){
						for (int b=-1; b<2; b++){
							if (field[i+a][j+b].hasMine()){
								num++;
							}
						}
					}
				
					field[i][j].setNumber(num);
				
				} 
				else {
					field[i][j].setNumber(9);
					field[i][j].Uncover();
				}
			}
		}
	}
	
	private void rippleUncover(int x, int y)
	{
		if (field[x][y].hasMine() || field[x][y].isFlagged())
			return;
		field[x][y].Uncover();
		if (field[x][y].getNumber() != 0 ){
			return;
		}
		for (int i=0; i<3; i++){
			for (int j=0; j<3; j++){
				if (field[x+i-1][y+j-1].isCovered()
						&& x+i-1 > 0 && y+j-1 > 0
						&& x+i-1 < height + 1 && y+j-1 < width + 1){
					rippleUncover( x+i-1, y+j-1 );
				} 
			}
		}
		return;
	}
	
	private boolean checkWin()
	{
		for (int i=1; i<height+1; i++){
			for (int j=1; j<width+1; j++){
				if (!field[i][j].hasMine() && field[i][j].isCovered()){
					return false;
				}
			}
		}
		return true;
	}
		
	public int getRows()
	{
		return height;
	}
	
	public int getColumns()
	{
		return width;
	}
	
	public Square[][] getField()
	{
		return field;
	}
	
	public int getMinect()
	{
		return minect;
	}
	
	public int getTimer()
	{
		return timer;
	}
}
