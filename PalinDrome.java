import java.util.Scanner;
class  PalinDrome
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a number:");
		int n = sc.nextInt();
		int temp=n,rev=0;
		while (n>0) {
		  rev = rev*10+n%10;
		  n/=10;
		}
		if (temp==rev)
		{ System.out.println("palindrome number:");

		}
		else 
		{
			System.out.println("is not palindrome number:");
	    }
	}  
}
