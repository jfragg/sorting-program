package jfragaki_assignment2;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FrameController extends JFrame implements ActionListener{
	
	//declares the components
	private PaintPanel paintPanel;
	private JRadioButton selectionSortButton;
	private JRadioButton mergeSortButton;
	private JButton sortButton;
	private JButton resetButton;
	private JButton scrambleButton;

	//declares the arrays and booleans required 
	private int [] clone;
	private int [] heights;
	private boolean sortedMerge;
	private boolean sortedSelect;
	
	//declares the objects needed for the animation 
	Thread thread;
	SelectionSort s;
	MergeSort m;
	
	/**
	 * constructor for the main components of the frame, 
	 * does all the necessary UI related programming for the frame
	 * */
	public FrameController(){
		
//====================================================== CONSTRAINTS ======================================================\\		

		//add constraints to the radio button grid bag 
		GridBagConstraints rbConstraints = new GridBagConstraints();
		rbConstraints.insets = new Insets(2,10,5,10); //constraints for the grid bag buttons
		
		//add constraints to the panels
		GridBagConstraints pConstraints = new GridBagConstraints();
		pConstraints.insets = new Insets(15,15,15,15); //constraints for the grid bag panels 
		
//====================================================== CONSTRAINTS ======================================================\\
		
//====================================================== PANEL CREATION ======================================================\\
		//create east panel that sorts the right side
		JPanel eastPanel = new JPanel(new GridBagLayout());
		eastPanel.setBorder(new TitledBorder(""));
		eastPanel.setBackground(Color.white);
				
		//add panel 1
		JPanel p1 = new JPanel();
		pConstraints.fill = GridBagConstraints.HORIZONTAL;
		pConstraints.ipady = 100;
		pConstraints.gridx = 0;
		pConstraints.gridy = 0;
		p1.setBackground(Color.white);
		eastPanel.add(p1, pConstraints);
		
		//add panel 2
		JPanel p2 = new JPanel(new GridBagLayout());
		pConstraints.fill = GridBagConstraints.HORIZONTAL;
		pConstraints.ipady = 50;
		pConstraints.gridx = 0;
		pConstraints.gridy = 1;
		p2.setBackground(Color.white);
		eastPanel.add(p2, pConstraints);
		
		//add panel 3
		JPanel p3 = new JPanel(new GridBagLayout());
		pConstraints.fill = GridBagConstraints.HORIZONTAL;
		pConstraints.ipady = 0;
		pConstraints.weighty = 1;
		pConstraints.anchor = GridBagConstraints.PAGE_START;
		pConstraints.gridx = 0;
		pConstraints.gridy = 2;
		p3.setBackground(Color.white);
		eastPanel.add(p3, pConstraints);
		
		//add the main panel to the main frame
		add(eastPanel, BorderLayout.EAST);
		
		//call the randomize height function to create the array 
		RandomizeHeights();
		
		//put that paintpanel on the main frame
		paintPanel = new PaintPanel();
		paintPanel.draw(heights); //draw the set of rectangles onto the panel
		paintPanel.initializeColor(new Color [256]); //create the color array and initialize it 
		add(paintPanel); //add the paint panel to the main frame 
//====================================================== PANEL CREATION ======================================================\\

		
//====================================================== PANEL 1 COMPONENTS ======================================================\\
		//create button to scramble the lines
		scrambleButton = new JButton("Scramble Lines");

		//add the action listener and action performed when button is clicked 
		scrambleButton.addActionListener(this);
		
		//add the button to p1
		p1.add(scrambleButton);
		
		
//====================================================== PANEL 1 COMPONENTS ======================================================\\
		
		
//====================================================== PANEL 2 COMPONENTS ======================================================\\


		//create radio buttons for selection sort & merge sort
		selectionSortButton = new JRadioButton("Selection Sort");
		
		//pass the same amount of characters into the merge sort button so the buttons are perfectly in line
		mergeSortButton = new JRadioButton("Merge Sort    "); 
		
		//add the radio buttons to a group so only one can be selected at one time
		ButtonGroup group = new ButtonGroup();
		group.add(selectionSortButton);
		group.add(mergeSortButton);
		
		//set the initial positions of the radio buttons
		rbConstraints.gridx = 0;
		rbConstraints.gridy = 0;
		
		//add the first radio button to panel 2
		p2.add(selectionSortButton, rbConstraints);
		
		rbConstraints.gridx = 0; //keep the next element in line with first
		rbConstraints.gridy = 1; //move next element 1 grid spacing below
		
		//add next radio button to panel 2
		p2.add(mergeSortButton, rbConstraints);
		p2.setBorder(new TitledBorder("Sorting Algorithms"));
		
//====================================================== PANEL 2 COMPONENTS ======================================================\\
		
		
//====================================================== PANEL 3 COMPONENTS ======================================================\\
		sortButton = new JButton("Sort!"); //create new button named Sort!
		sortButton.addActionListener(this); //add action listener to the Sort button
		p3.add(sortButton); //add the button to panel p3
		
		resetButton = new JButton("Reset"); //create new button named Reset
		resetButton.addActionListener(this); //add action listener to the reset button
		p3.add(resetButton); //add the button to panel p3
		
//====================================================== PANEL 3 COMPONENTS ======================================================\\

	} //end FrameController
	
	
	public static void main(String [] args){
		//create new frame
		FrameController frame = new FrameController();
		
		//set the frame components
		frame.setTitle("Sort Analysis");
		frame.setSize(1000, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}//end main

	/**
	 * action event handler
	 * determines what each button UI component does to the frame
	 * */
	public void actionPerformed(ActionEvent e) {
		if(mergeSortButton.isSelected() && e.getSource() == sortButton){
			
			/*if both merge sort radio button is selected and the user initiates the sort with the button
			 *merge sort will begin, disable the button if one of the sorts have begun 
			 **/
			scrambleButton.setEnabled(false);
			mergeSort();
			sortedMerge = true;
			if(scrambleButton.isEnabled() == false && sortedSelect == true){
				scrambleButton.setEnabled(true);
			}
			
		}
		if (selectionSortButton.isSelected() && e.getSource() == sortButton){
			
			/*if both the selection sort radio button is selected and the user initiates the sort with the button
			 * selection sort will begin, disable the button if one of the sorts have begun
			 * */
			scrambleButton.setEnabled(false);
			selectionSort();
			sortedSelect = true;
			if(scrambleButton.isEnabled() == false && sortedMerge == true){
				scrambleButton.setEnabled(true);
			}

		}
		
		if(e.getSource() == scrambleButton){
			/*if the scramble button is selected, create a new randomized set of rectangles
			 * draw the rectangles onto the panel and reset the sort booleans to false 
			 * this way the scramble button can be disable again with the new set of rectangles*/
			RandomizeHeights();
			paintPanel.draw(heights);
			sortedMerge = false;
			sortedSelect = false;
		}
		
		if(e.getSource() == resetButton){
			
			/*check to see which sort method was */
			if(sortedMerge == true){
				paintPanel.draw(heights);
			}
			if(sortedSelect == true){
				paintPanel.draw(clone);
			}

		}
	}
	/**
	 * use the merge sort object to send in the array to be sorted on the paint panel
	 * create a new thread that takes in the object and can use its run method as well as 
	 * create the delay between each swap to show the animation
	 * */
	public void mergeSort(){
		
		m = new MergeSort(256, paintPanel, clone); //create new selection sort object; pass through one of the arrays, size etc
		thread = new Thread(m); //add the merge sort object to the new thread
		thread.start();
	
	}
	/**
	 * use the selection sort object to send in the array to be sorted on the paint panel
	 * create a new thread that takes in the object and can use its run method as well as 
	 * create the delay between each swap to show the animation
	 * */
	public void selectionSort(){

		s = new SelectionSort(256, paintPanel, heights); //create new selection sort object; pass through the array & size etc
		thread = new Thread(s); //add the selection sort object to the new thread
		thread.start(); //begin the thread
	}
	
	/**
	 * create a random array of heights to be passed to the paint panel for creation
	 * as well fill another array to be it's clone so the array can be reset to its original
	 * set of rectangles and both sort methods can be compared
	 * */
	public void RandomizeHeights(){
		
		Random rand = new Random();
		heights = new int [256];
		clone = new int [256];
		
		for(int i = 0; i < 256; i++){
			heights[i] = rand.nextInt(300);
			clone[i] = heights[i];
		}
		
	}
}
