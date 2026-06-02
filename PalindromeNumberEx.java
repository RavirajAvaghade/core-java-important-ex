import java.util.Scanner;
class  PalindromeNumberEx
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a Number:");
		int num = sc.nextInt();
		int temp = num;
		int rev = 0;
		while (temp!=0)
		{
			int ld = temp%10;
			rev = rev*10+ld;
			temp = temp/10;
		}
		if (rev==num)
		{
			System.out.println(num+"is an palindrome number:");
		}
		else
		{
			System.out.println(num+"is Not a palindrome Number:");
		}
	}
}
