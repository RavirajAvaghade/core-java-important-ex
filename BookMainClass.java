package ClassObjectPrograms;

public class BookMainClass {
	public static void main (String[]args)
	{
		Book b1 = new Book("Chota bheem","green foot",120);
		b1.issueBook();
		b1.turnCurrentPage();
	}

}
class Book
{
	public String name;
	public String authorName;
	public double price;
	public Book(String name, String authorName,double price)
	{
		this.name = name;
		this.authorName = authorName;
		this.price = price;
	}
	public void issueBook()
	{
	System.out.println("Book named "+name+"is issued.....!!");
	System.out.print("Happy Reading....!!");
	}
	public void returnBook()
	{
		System.out.println("book named"+name+"is returned now.....!");
		System.out.println("thanks for return");
	}
	public void turnCurrentPage()
	{
		System.out.println("current page is turned");
		System.out.println("enjoy reading");


		
	}
	public void bookMarkpage()
	{
		System.out.println("this page is bookmarked");
		System.out.println("you enjoy any other book");

	}
	
}