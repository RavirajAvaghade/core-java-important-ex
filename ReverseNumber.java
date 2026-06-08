import java.util.Scanner;
class  ReverseNumber
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a number:");
		int num = sc.nextInt();
		int rev =0;
		while (num!=0)
		{
			//step 1:- late last digit from user
			int ld = num%10;
            // step 2:- add last digit to rev
			rev = rev*10+ld;
            // step 3:- remove last digit from user
			num = num/10;
		}
		System.out.println("Reversed number is"+rev);

	}
}
