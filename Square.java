/*
 * 	Represents the squares on the Minesweeper field.
 */

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Square extends JButton
{

	private static final long serialVersionUID = 1L;
	private boolean covered, mined, flagged, qmarked, qon, clickable;
	private int number; //number of mines in surrounding squares
	private ImageIcon mine, redmine, empty, cover;
	private ImageIcon one, two, three, four, five, six, seven, eight;
	
	public Square()
	{ //default constructor

		covered = true;
		clickable = true;
		qon = true;
		flagged = false;
		qmarked = false;
		mined = false;
		number = 0;
		cover = new ImageIcon("Images/cover.jpg");
		mine = new ImageIcon("Images/mine.jpg");
		redmine = new ImageIcon("Images/redmine.jpg");
		empty = new ImageIcon("Images/empty.jpg");
		one = new ImageIcon("Images/one.jpg");
		two = new ImageIcon("Images/two.jpg");
		three = new ImageIcon("Images/three.jpg");
		four = new ImageIcon("Images/four.jpg");
		five = new ImageIcon("Images/five.jpg");
		six = new ImageIcon("Images/six.jpg");
		seven = new ImageIcon("Images/seven.jpg");
		eight = new ImageIcon("Images/eight.jpg");
		setPreferredSize(new Dimension (16, 16));
		setIcon(cover);
		setDisabledIcon(cover);
		
		
	}
	
	public void Uncover()
	{

		if (!covered) //no further action required if square already uncovered
			return;
		
		setEnabled(false); //disable button
		covered = false;
		
		//set appropriate icon depending on contents of square
		if (hasMine())
		{
			setDisabledIcon(mine);
		}
		else if (number == 0)
		{
			setDisabledIcon(empty);
		}
		else if (number == 1)
		{
			setDisabledIcon(one);
		}
		else if (number == 2)
		{
			setDisabledIcon(two);
		}
		else if (number == 3)
		{
			setDisabledIcon(three);
		}
		else if (number == 4)
		{
			setDisabledIcon(four);
		}
		else if (number == 5)
		{
			setDisabledIcon(five);
		}
		else if (number == 6)
		{
			setDisabledIcon(six);
		}
		else if (number == 7)
		{
			setDisabledIcon(seven);
		}
		else if (number == 8)
		{
			setDisabledIcon(eight);
		}
	}
	
	public void Arm()
	{ //plant a mine on this square
		mined = true;
	}
	
	public void Trigger()
	{
		setDisabledIcon(redmine);
	}
	
	public boolean isCovered()
	{ //is this square covered?
		return covered;
	}
	
	public boolean hasMine()
	{ //does this square contain a mine?
		return mined;
	}
	
	public void setNumber(int n)
	{
		number = n;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public boolean isFlagged()
	{
		return flagged;
	}
	
	public void setFlagged( boolean f )
	{
		flagged = f;
	}
	
	public boolean isQmarked()
	{
		return qmarked;
	}
	
	public void setQmarked( boolean q )
	{
		qmarked = q;
	}
	
	public boolean isClickable()
	{
		return clickable;
	}
	
	public void setClickable( boolean c )
	{
		clickable = c;
	}
	
	public void setQon( boolean q )
	{
		qon = q;
	}
	
	public boolean isQon()
	{
		return qon;
	}

}
