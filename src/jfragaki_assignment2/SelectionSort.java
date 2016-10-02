package jfragaki_assignment2;

public class SelectionSort implements Runnable {
	
	//declares the variables
	private int [] array;
	PaintPanel paint;
	
	/**
	 * constructor that initializes the variables for the selection sort
	 * */
	public SelectionSort(int i, PaintPanel p, int [] arr){
		array = new int [256];
		array = arr;
		paint = p;
	}

	/**
	 * the selection sort method that does the actual swapping, 
	 * as well as does the time delay for animation
	 * */
	public void selectionSort(int[] a){

		int i, j, minIndex, tmp;
		int n = a.length;
			for(i = 0; i < n - 1; i++){
				minIndex = i;
				for(j = i + 1; j < n; j++){
					if(a[j] < a[minIndex]){
						minIndex = j;
					}
				}
				
				paint.draw(a);
				try{ Thread.sleep(25); } catch (InterruptedException e) { }
				tmp = a[i];
				a[i] = a[minIndex];
				a[minIndex] = tmp;
			}
			paint.draw(a);
	}


	/**
	 * calls the sort method which calls the selection Sort
	 * */
	public void run() {
		// TODO Auto-generated method stub
		sort();
	}

	/**
	 * calls the selection sort method and passes through the array
	 * */
	private void sort() {
		// TODO Auto-generated method stub
		selectionSort(array);
	}
}