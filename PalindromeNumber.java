import java.util.Scanner;
class  PalindromeNumber
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a number:");
		int num = sc.nextInt();
		int original =num;
		int rev = 0;
		for (int n =num;n>0;n=n/10)
		{ int digit = n%10;
		  rev =(rev*10)+digit;
		}
		if (original==rev)
		{
			System.out.println("it is palindrome Number:");
		}
		else
		{
			System.out.println("it is Not palindrome Number:");
	    }
	}
}
