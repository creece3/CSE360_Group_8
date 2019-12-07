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
	public Float min, max;

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
		sortData();
		return data;
	}
	
	public String size() 
	{
		return Integer.toString(data.size());
	}
	
	public String maxValue() 
	{
		if(data.size() > 0) 
		{
			Float maxValue = Float.NEGATIVE_INFINITY;
			
			for(int index = 0; index < data.size(); index++) 
			{
				if(data.get(index) > maxValue) 
				{
					maxValue = data.get(index); 
				}
			}
			
			return "Max value: " + Float.toString(maxValue);
		}
		else 
		{
			return "N/A";
		}
	}
	
	public String minValue() 
	{
		if(data.size() > 0) 
		{
			Float minValue = Float.POSITIVE_INFINITY;
			
			for(int index = 0; index < data.size(); index++) 
			{
				if(data.get(index) < minValue) 
				{
					minValue = data.get(index);
				}
			}
			
			return "Min value: " + Float.toString(minValue);
		}
		else 
		{
			return "N/A";
		}
	}
	
	/**
	 * Function that returns the mean value of the data arraylist. It does this
	 * by adding up all the values of the arraylist and dividing them by the
	 * size of data. It then removes the trailing zeros by calling the trimFloat
	 * function, and it returns the string returned from the trimFloat function.
	 * It returns this value as a string.
	 * 
	 * @return Mean value of the data arraylist. N/A if no elements in data.
	 */
	public String mean() 
	{
		if(data.size() > 0)
		{
			Float total = 0f,
				mean = 0f;
		
			for(Float element : data) 
			{
				total += element;
			}

			mean = total / (float)data.size();

			return "Mean: " + trimFloat(mean);
		}
		else 
		{
			return "N/A";
		}
	}
	
	/**
	 * Function that returns a String representation of the median value of the
	 * data arraylist. It does this by dividing the size of the array in half.
	 * This is the median value if data's size is odd. The median value of even
	 * sized data is the average of this value and the next value. The median
	 * value is N/A if data is empty.
	 * 
	 * @return The median value of the arraylists. N/A if data is empty.
	 */
	public String median() 
	{
		if(data.size() > 0)
		{
			int medianIndex;
			Float median;
		
			medianIndex = data.size() / 2;

			if(data.size() % 2 == 1) 
			{
				median = data.get(medianIndex);
			}
			else
			{
				median = (data.get(medianIndex) + data.get(medianIndex - 1)) / 2f;
			}

			return "Median: " + trimFloat(median);
		}
		else
		{
			return "N/A";
		}
	}
	
	public void deleteElement(String elementToDelete) 
	{
		try
    	{
    		Float floatElementToDelete = Float.parseFloat(elementToDelete);
    		
    		boolean deletedElement = false;
    		int index = 0;
    		
    		while(!deletedElement && index < data.size()) 
    		{
    			if(data.get(index).equals(floatElementToDelete)) 
    			{
    				data.remove(index);
    				deletedElement = true;
    				history.add("Deleted element: " + elementToDelete);
    			}
   
    			index++;
    		}
       	}
    	catch(NumberFormatException e)
    	{
    		errors.add("PROGRAM FAILURE CANNOT DELETE");
    	};
	}
	
	public String printHistory() 
	{
		String returnHistory = "";
		
		for(String element : history) 
		{
			returnHistory += element + "\n";
		}
		
		return returnHistory;
	}
	
	/**
	 * Function that returns the String representation of the Mode of the data
	 * arraylist. It does this by first sorting the arraylist data, and then it
	 * uses an iterative process to count the number that appears the most. The
	 * definition of mode used in this function follows:
	 * 1. N/A is returned for empty data
	 * 2. "No mode" is returned for data where is element is unique
	 * 3. "Mode: modeFloatValue" is returned if one number appears the most
	 * 4. "Mode: modeFloatValue1, ..., ModeFloatValueN" is returned if multiple
	 * numbers appear more than once and appear the most.
	 * 
	 * @return The mode of the data arraylist. N/A if data is empty.
	 */
	public String mode() 
	{
		if(data.size() > 0) 
		{
			sortData();
			
			int numberOfAppearances = 0, numberOfMostApperances = 1;
			Float currentValue;
			
			List<Float> modes = new ArrayList<Float>();
			
			currentValue = data.get(0);
			
			for(int index = 0; index < data.size(); index++) 
			{
				if(currentValue == data.get(index) && index != data.size() - 1) 
				{
					numberOfAppearances++;
				}
				else 
				{
					if(index == data.size() - 1 && currentValue == data.get(index)) 
					{
						numberOfAppearances++;
					}
					
					if(numberOfAppearances > numberOfMostApperances) 
					{
						numberOfMostApperances = numberOfAppearances;						
						
						modes.clear();
						modes.add(currentValue);
					}
					else if(numberOfAppearances == numberOfMostApperances && numberOfAppearances > 1) 
					{
						modes.add(currentValue);
					}
			
					currentValue = data.get(index);
					numberOfAppearances = 1;
				}
			}
			
			if(modes.size() == 0) 
			{
				return "Mode: no mode, all unique elements";
			}
			else if(modes.size() == 1)
			{
				return "Mode: " + trimFloat(modes.get(0));
			}
			else 
			{
				String modesOutput = "Modes: ";
				
				for(int index = 0; index < modes.size(); index++) 
				{
					modesOutput += trimFloat(modes.get(index));
					
					if(index != modes.size() - 1) 
					{
						modesOutput += ", ";
					}
				}
				
				return modesOutput;
			}
		}
		else 
		{
			return "N/A";
		}
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
	 * Function that returns a string representation of the data arraylist 
	 * in 4 neat columns. It first sorts the data by calling the sort function.
	 * It then arranges the elements of the data arraylist into 4 arrays that
	 * represent each column to print. Lastly, it concatenates the elements of
	 * these arrays while calling the trimFloat method which formats the floats 
	 * to appear the same as they were input.
	 * 
	 * @return The String of the data formatted into 4 columns.
	 */
	public String printData() 
	{
		sortData();
		
		int rows;
		
		if(data.size() > 0 && data.size() % 4 == 0) 
		{
			rows = data.size() / 4;
		}
		else if(data.size() > 0) 
		{
			rows = data.size() / 4 + 1;
		}
		else 
		{
			rows = 0;
		}
		
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
				column2[index - rows] = data.get(index);
			}
			for(int index = rows * 2; index < 3 * rows; index++) 
			{
				column3[index - rows * 2] = data.get(index);
			}
			for(int index = 3 * rows; index < 4 * rows; index++) 
			{
				column4[index - rows * 3] = data.get(index);
			}
		}
		
		//Print the data
		String data = "";
		
		for(int index = 0; index < rows; index++) 
		{
			if(index < column1.length) 
			{
				data += String.format("%-15s", trimFloat(column1[index]));
			}
			if(index < column2.length) 
			{
				data += String.format("%-15s", trimFloat(column2[index]));
			}
			if(index < column3.length) 
			{
				data += String.format("%-15s", trimFloat(column3[index]));
			}
			if(index < column4.length) 
			{
				data += String.format("%-15s", trimFloat(column4[index]));
			}
			
			data += "\n";
		}

		return data;
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
	 * Function to return a string containing all the errors in the error 
	 * arraylist. It simply iterates through the errors and puts them on a new
	 * line in the string.
	 */
	public String printErrors() 
	{
		String errors = "";
		
		for(int index = this.errors.size() - 1; index >= 0; index--) 
		{
			errors += this.errors.get(index);
			errors += "\n";
		}
		
		return errors;
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
    		
    		if(dataElementFloat <= max && dataElementFloat >= min)
    		{
    			data.add(dataElementFloat);
    			history.add(Events.dataInput + dataElementString);
    		}
    		else 
    		{
    			errors.add("User input data out of range: " + dataElementString);
    		}
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
	public void handleFile(String filePath) 
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
		    	
		    		if(dataElementFloat <= max && dataElementFloat >= min) 
		    		{
		    			data.add(dataElementFloat);
		    		}
		    		else 
		    		{
		    			errors.add("File input out of range (Line = " +
		    				currentLine + ", Column = " + currentColumn + "): "
		    				+ dataElementString);
		    		}
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
