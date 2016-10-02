package jfragaki_assignment2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class PaintPanel extends JPanel{
	
	//declare the variables for the paint panel
	private int [] heightArray;
	private Color [] colorArray;
	private int xPos = 17;
	
	/**
	 * default constructor for the PaintPanel class,
	 * this sets the preferred dimensions (size) of the panel as well
	 * as sets the background color to white to match the frame*/
	public PaintPanel(){
		setPreferredSize(new Dimension(600, 350));
		setBackground(Color.WHITE);
	}
	
	/**
	 * method that creates an array of random colors to be used on the rectangles
	 * giving the frame some color and making it visibly friendly*/
	public void initializeColor(Color [] arr){
		Random rand = new Random();
		colorArray = arr;
		for(int i = 0; i < 256; i++){
			colorArray[i] = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		
		}
	}
	
	/**
	 * paint method that does all the graphics work
	 * creating the set of 256 rectangles and giving them color
	 * and a border for more emphasis on the rectangles*/
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
			for(int i = 0; i < 256; i++){
				g.setColor(Color.black); //set the color of the rectangle to black
				g.drawRect(xPos, 500-heightArray[i], 1, heightArray[i]); //create the outline of the rect (black)
				g.setColor(colorArray[i]);//set the fill color to be a random color from the array
				g.fillRect(xPos, 500-heightArray[i], 1, heightArray[i]); //fill the rect with that random color
				xPos = xPos + 3; //increase its x-position so the next rectangle is a few pixels over and not overlapping
			}
		xPos = 17; //set the x-position back to 17 to allow a new set of rectangles to be put in the same spot
	}
	
	/**
	 * function that sets the height array and calls the repaint method 
	 * essentially drawing whatever array is passed into the function
	 * this allows the new repaint method to redraw a new array each time its called
	 * */
	public void draw(int [] arr){
		heightArray = arr;
		repaint();
	}

}
