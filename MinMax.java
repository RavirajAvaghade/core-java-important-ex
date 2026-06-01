import java.util.Scanner;
class  MinMax
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a first number:");
		int a = sc.nextInt();
		System.out.println("Enter a second number:");
		int b = sc.nextInt();
		if (a>b)
		{
			System.out.println("the grater number is :"+a);
			System.out.println("the smaller number is:"+b);
		}
		else if (b>a)
		{
			System.out.println("the s number is:"+b);
			System.out.println("
		}
		else 
		{
			System.out.println("Both are equal numbers:");
		}
	}
}
