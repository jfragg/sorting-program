package jfragaki_assignment2;

public class MergeSort implements Runnable {

	private int [] array;
	private int [] tempArray;
    static PaintPanel paint;

	/**
	 * default constructor for the Merge Sort object
	 * */
	public MergeSort(int i, PaintPanel p, int [] arr){
		array = new int [256];
		array = arr;
		paint = p;
	}
	
	/**
	 * begins the merge sort by splitting the array in parts before calling the merge section
	 * */
	private void mergeSort(int indexL, int indexR) {

			if (indexL < indexR) {
				int middle = indexL + (indexR - indexL) / 2; //get the middle of the array
				
				mergeSort(indexL, middle); //sort left side
				mergeSort(middle + 1, indexR); //sort right side 
				//merge both sides
				merge(indexL, middle, indexR); 
			}
		}

	/**
	 * takes in the indexes as parameters so it can begin checking for swaps
	 * */
	private void merge(int indexL, int middle, int indexR) {

		//store the temp array with the original array 
		for (int i = indexL; i <= indexR; i++) {
			tempArray[i] = array[i];
		}
			
		int i = indexL;
		int j = middle + 1;
		int k = indexL;
		// While both subarrays are not empty, compare an element in one subarray with
		// an element in the other after that copy the smaller element from the temp array
		// into the original array
		while (i <= middle && j <= indexR) {
			if (tempArray[i] <= tempArray[j]) {
				
				array[k] = tempArray[i];
				
				paint.draw(array);
				try { Thread.sleep(25);} catch (Exception ex) {} //draw the new array after the swap and delay

				i++;
				
				} else {
					array[k] = tempArray[j];
					
					paint.draw(array);
					try { Thread.sleep(25);} catch (Exception ex) {}//draw the new array after the swap and delay

					j++;
				}
				k++;
			}
			while (i <= middle) {
				array[k] = tempArray[i];
				
				paint.draw(array);
				try { Thread.sleep(25);} catch (Exception ex) {}//draw the new array after the swap and delay
				
				k++;
				i++;
			}
		}
	
	/**
	 * call the sort function which calls merge sort, run allows the Thread to work
	 * allowing the delay to happen
	 * */
	public void run() {
		// TODO Auto-generated method stub
		sort();
	}
	/**
	 * sort creates the new temp array of same length,
	 * sends the minimum value of the array and the max (255 because of index)
	 * merge sort does its sorting algorithm to sort the array
	 * */
	private void sort() {
		// TODO Auto-generated method stub
		tempArray = new int[array.length];
		mergeSort(0, 255);
	}

}
