/* Names and IDs: Christopher Reece creece3
 * Assignment: Final Project
 * 
 * Description: 
 */

import java.util.ArrayList;
import java.util.Collections;
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
	
	public Data(String filePath)
	{
		min = 0f;
		max = 100f;
		
		data = new ArrayList<Float>();
		history = new ArrayList<String>(); 
		errors = new ArrayList<String>();
				
		handleFile(filePath);
	}
	
	public void printData() 
	{
		for(Float dataElement : data) 
		{
			if(dataElement >= min && dataElement <= max) 
			{
				System.out.println(dataElement);
			}
		}
	}
	
	public void printErrors() 
	{
		for(String error : errors) 
		{
			System.out.println(error);
		}
	}
	
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
	
	public void sortData() 
	{
		Collections.sort(data);
		
		history.add(Events.sort);
	}
		
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
					// TODO Auto-generated catch block
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
