import java.util.Scanner;
class  ArmStrongNumber
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a number:");
		int num = sc.nextInt();
		//logic 1: we count number of digits
		// since number will become zero, we use temp variable
		int temp = num;
		int count = 0;
		while (temp!=0)

		{  // step 1: increment the count
			count++;
           // step 2: remove ld
			temp = temp/10;
		}
        // logic 2: we find sum of powers of digits
		// since, temp already became zero in first logic, re-initialize it
		temp = num;
		int sum = 0;
		while (temp!=0)
		{ //step-1:take ld
          int ld = temp%10;

		  // step:-2:multiply ld,count times
		  int p =1;
		  for(int i =1;i<=count;i++)
			{
			  p = p*ld;

			}
			//step3:- add product to sum
			sum = sum+p;

			//step4:- remove ld
			temp = temp/10;
		}
		if (sum==num)
		{
			System.out.println("Armstrong number:");
		}
		else
		{
			System.out.println("Not Armstrong Number:");

		}

	}
}
