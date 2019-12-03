public class Tester 
{
	public static void main(String[] args) 
	{
		Data d = new Data("a.txt");
		d.insertData("asdas123123");
		d.insertData("12.213");
		d.insertData("0");
		d.insertData("");
		d.insertData("-123.213");
		
		d.printData();
		d.printErrors();
	}
}
