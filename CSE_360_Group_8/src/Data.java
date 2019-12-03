/* Names and IDs: Christopher Reece creece3
 * Assignment: Final Project
 * 
 * Description: 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.*;

/**
 * The Data class represents a set of data in the Grade Analytics program. The
 * class contains the data itself, all functions to perform operations on the
 * data, it keeps track of all valid and invalid operations done on the data.
 */
public class Data 
{
	private Float min, max;

	private List<Float> data;
	private List<String> history, errors;
	
	/**
	 * Class constructor initializing default values for the min and max member
	 * variables that bound the data that analysis is done on. It also creates
	 * array lists to hold Float data imported from files or user input, to
	 * hold the history of operations done, and to hold the history of errors.
	 * 
	 * @param filePath
	 */
	public Data(String filePath)
	{
		min = 0f;
		max = 100f;
		
		data = new ArrayList<Float>();
		history = new ArrayList<String>(); 
		errors = new ArrayList<String>();
				
		handleFile(filePath);
	}
	
	/**
	 * Overloaded class constructor for the Data class that does not take a
	 * filePath. In this case, there will only be user input data and no file
	 * input.
	 */
	public Data()
	{
		min = 0f;
		max = 100f;
		
		data = new ArrayList<Float>();
		history = new ArrayList<String>(); 
		errors = new ArrayList<String>();
	}
	
	/**
	 * Function that returns the data arraylist.
	 * 
	 * @return the data arraylist containing the data of the current object
	 */
	public List<Float> getData() 
	{
		return data;
	}
	
	/**
	 * Function to set the min and max variables that bound the data that
	 * analysis is done on. If the min and max values are not floats, an
	 * error is added to the error arraylist.
	 * 
	 * @param min The min value to bound the data that analysis is done on.
	 * @param max The max value to bound the data that analysis is done on.
	 */
	public void setMinMax(String min, String max) 
	{
    	try
    	{
    		this.min = Float.parseFloat(min);
       	}
    	catch(NumberFormatException e)
    	{
    		errors.add("Attempted to set invalid min: " + min);
    	}
    	
    	try
    	{
    		this.max = Float.parseFloat(max);
    	}
    	catch(NumberFormatException e)
    	{
    		errors.add("Attempted to set invalid max: " + max);
    	}
	}
	
	/**
	 * Function to set the min variable that bound the data that
	 * analysis is done on. If the min value is not a Float, an
	 * error is added to the error arraylist.
	 * 
	 * @param min The min value to bound the data that analysis is done on.
	 */
	public void setMin(String min) 
	{
    	try
    	{
    		this.min = Float.parseFloat(min);
       	}
    	catch(NumberFormatException e)
    	{
    		errors.add("Attempted to set invalid min: " + min);
    	}
	}
	
	/**
	 * Function to set the max variable that bound the data that
	 * analysis is done on. If the max value is not a floats, an
	 * error is added to the error arraylist.
	 * 
	 * @param max The max value to bound the data that analysis is done on.
	 */
	public void setMax(String max) 
	{
    	try
    	{
    		this.max = Float.parseFloat(max);
    	}
    	catch(NumberFormatException e)
    	{
    		errors.add("Attempted to set invalid max: " + max);
    	}
	}

	/**
	 * Function that prints the data array of floats in 4 neat columns. It first
	 * sorts the data by calling the sort function. It then arranges the
	 * elements of the data arraylist into 4 arrays that represent each column
	 * to print. Lastly, it prints the elements of these arrays while calling
	 * the trimFloat method which formats the floats to appear the same as they
	 * were input.
	 */
	public void printData() 
	{
		sortData();
		
		int rows = data.size() / 4 + 1;
		
		Float[] column1 = new Float[rows], 
			column2 = null, 
			column3 = null, 
			column4 = null;

		//From here down, arrange the elements of data into 4 Float arrays that
		//represent the columns to print
		for(int index = 0; index < rows; index++) 
		{
			column1[index] = data.get(index);
		}
		
		if(data.size() % 4 == 1) 
		{
			column2 = new Float[rows - 1];
			column3 = new Float[rows - 1];
			column4 = new Float[rows - 1];
			
			for(int index = rows; index < rows * 2 - 1; index++) 
			{
				column2[index - rows] = data.get(index);
			}
			for(int index = rows * 2 - 1; index < 3 * rows - 2; index++) 
			{
				column3[index - (rows * 2 - 1)] = data.get(index);
			}
			for(int index = 3 * rows - 2; index < 4 * rows - 3; index++) 
			{
				column4[index - (3 * rows - 2)] = data.get(index);
			}
		}
		else if(data.size() % 4 == 2) 
		{
			column2 = new Float[rows];
			column3 = new Float[rows - 1];
			column4 = new Float[rows - 1];
			
			for(int index = rows; index < rows * 2; index++) 
			{
				column2[index - rows] = data.get(index);
			}
			for(int index = rows * 2; index < 3 * rows - 1; index++) 
			{
				column3[index - rows * 2] = data.get(index);
			}
			for(int index = 3 * rows - 1; index < 4 * rows - 2; index++) 
			{
				column4[index - (3 * rows - 1)] = data.get(index);
			}
		}
		else if(data.size() % 4 == 3) 
		{
			column2 = new Float[rows];
			column3 = new Float[rows];
			column4 = new Float[rows - 1];
			
			for(int index = rows; index < rows * 2; index++) 
			{
				column2[index - rows] = data.get(index);
			}
			for(int index = rows * 2; index < 3 * rows; index++) 
			{
				column3[index - rows * 2] = data.get(index);
			}
			for(int index = 3 * rows; index < 4 * rows - 1; index++) 
			{
				column4[index - rows * 3] = data.get(index);
			}
		}
		else if(data.size() > 0 && data.size() % 4 == 0) 
		{
			column2 = new Float[rows];
			column3 = new Float[rows];
			column4 = new Float[rows];
			
			for(int index = rows; index < rows * 2; index++) 
			{
				column2[index] = data.get(index);
			}
			for(int index = rows * 2; index < 3 * rows; index++) 
			{
				column3[index] = data.get(index);
			}
			for(int index = 3 * rows; index < 4 * rows; index++) 
			{
				column4[index] = data.get(index);
			}
		}
		
		//Print the data
		for(int index = 0; index < rows; index++) 
		{
			if(index < column1.length) 
			{
				System.out.printf("%-15s", trimFloat(column1[index]));
			}
			if(index < column2.length) 
			{
				System.out.printf("%-15s", trimFloat(column2[index]));
			}
			if(index < column3.length) 
			{
				System.out.printf("%-15s", trimFloat(column3[index]));
			}
			if(index < column4.length) 
			{
				System.out.printf("%-15s", trimFloat(column4[index]));
			}
			
			System.out.println();
		}
	}
	
	/**
	 * Function to format a Float value. It takes in the Float value to format,
	 * and it converts it to a String. It then trims all trailing 0's from the
	 * String. Lastly, it returns this String value.
	 * 
	 * @param floatToTrim The float to remove trailing 0's from.
	 * @return String value that represents the float with no trailing 0's.
	 */
	public String trimFloat(Float floatToTrim) 
	{
		String trimmedFloat = floatToTrim.toString();
		int index = trimmedFloat.length() - 1;
		
		while(index >= 1 && trimmedFloat.charAt(index) != '.') 
		{
			if(trimmedFloat.charAt(index) == '0') 
			{
				trimmedFloat = trimmedFloat.substring(0, index);
			}
			
			index--;
		}
			
		if(trimmedFloat.charAt(index) == '.' && trimmedFloat.length() == index + 1) 
		{
			trimmedFloat = trimmedFloat.substring(0, index);
		}
		
		return trimmedFloat;
	}
	
	/**
	 * Function to print all the errors in the error arraylist. It simply
	 * iterates through the errors and prints them each on a new line.
	 */
	public void printErrors() 
	{
		for(int index = errors.size() - 1; index >= 0; index--) 
		{
			System.out.println(errors.get(index));
		}
	}
	
	/**
	 * Function to insert user input data into the data arraylist. It first
	 * attempts to conver the user input string value into a Float value, and
	 * if it can be converted it is added to the data arraylist and history is
	 * kept of this action. If it cannot be converted to a float value, an error
	 * is added to errors.
	 * 
	 * @param dataElementString The string that the user wants to insert into
	 * the data arraylist.
	 */
	public void insertData(String dataElementString) 
	{
		Float dataElementFloat;
		
    	try
    	{
    		dataElementFloat = Float.parseFloat(dataElementString);
    		
    		data.add(dataElementFloat);
    		
    		history.add(Events.dataInput + dataElementString);
    	}
    	catch(NumberFormatException e)
    	{
    		errors.add(Errors.dataInputInvalid + dataElementString);
    	}
	}
	
	/**
	 * Function to sort the elements of the data arraylist in descending order.
	 * It does this by first initializing a Comparator<float> that returns true
	 * if the first element is greater than the second element and so forth. It
	 * then uses the Collections.sort method and the comparator to sort.
	 */
	public void sortData() 
	{
		Comparator<Float> comparator = new Comparator<Float>() 
		{

			@Override
			public int compare(Float arg0, Float arg1)
			{
				if(arg0 > arg1) 
				{
					return -1;
				}
				else if(arg0 < arg1) 
				{
					return 1;
				}
				else 
				{
					return 0;
				}
			}
		};
				
		Collections.sort(data, comparator);
	}
	
	/**
	 * Function that checks if the filePath from the class constructor is valid.
	 * If it is valid, it will call a function to read in the data. If it is
	 * invalid, it will record and error in errors.
	 * 
	 * @param filePath The filePath from the class constructor to check if it is
	 * valid and exists.
	 */
	private void handleFile(String filePath) 
	{
		File file = new File(filePath);

		if(file.exists()) 
		{
			if(filePath.length() > 4 && 
				(filePath.substring(filePath.length() - 4).equals(".txt") ||
				filePath.substring(filePath.length() - 4).equals(".csv"))) 
			{
				try 
				{
					readFile(filePath);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
			else 
			{
				errors.add(Errors.fileInvalid + filePath);
			}
		}
		else 
		{
			errors.add(Errors.fileNonexistent + filePath);
		}
	}
	
	/**
	 * Function that reads in the data from the file specified at the parameter
	 * filePath. It reads each element into the data arraylist if the element is
	 * valid. If it is not valid, an error is stored in the data arraylist.
	 * 
	 * @param filePath the filePath of the file to read from.
	 * @throws IOException Exception thrown if the filePath is invalid. This
	 * should not happen, because the filePath is checked in the function that
	 * calls this function.
	 */
	private void readFile(String filePath) throws IOException 
	{
		int currentLine = 0;
		int currentColumn = 0;
		
		String dataLine;
		String[] dataArray;
		Float dataElementFloat;
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		while((dataLine = reader.readLine()) != null) 
		{
			currentColumn = 0;
			
			dataArray = dataLine.split(",");

			for(String dataElementString : dataArray)
			{	
		    	try
		    	{
		    		dataElementFloat = Float.parseFloat(dataElementString);
		    		
		    		data.add(dataElementFloat);
		    	}
		    	catch(NumberFormatException e)
		    	{
		    		errors.add(Errors.fileDataInvalid + "(Line = " + currentLine
		    			+ ", Column = " + currentColumn + "): " 
		    			+ dataElementString);
		    	}
		    	
		    	currentColumn++;
			}
			
			currentLine++;
		}
		
		reader.close();
		
		history.add(Events.fileImport + filePath);
	}
}
