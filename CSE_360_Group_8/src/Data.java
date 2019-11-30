/* Names and IDs: Christopher Reece creece3
 * Assignment: Final Project
 * 
 * Description: 
 */

import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * Data class to...
 */
public class Data 
{
	public List<Float> data;
	public List<String> history, list;
	
	public Data(String filePath)
	{
		data = new ArrayList<Float>();
		history = new ArrayList<String>(); 
		list = new ArrayList<String>();
		
		handleFile(filePath);
	}
	
	void handleFile(String filePath) 
	{
		File file = new File(filePath);
		
		if(file.exists()) 
		{
		
		}
		else 
		{
			
		}
	}
}
