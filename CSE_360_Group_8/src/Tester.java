public class Tester 
{
	public static void main(String[] args) 
	{
		Data d = new Data("a.txt");
		
		d.sortData();
		d.printData();
		d.insertData("abc");
		d.printErrors();
	}
}
