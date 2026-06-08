import java.util.Scanner;
class  PrimeNumber
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a number");
		int n = sc.nextInt();
		int count =0;
		for (int i=1;i<=n;i++)
		{
			if (n%i==0)
			{
				count++;
			
			}
		}     
		System.out.println("count of factor is:"+count);
		if (count==2)
		{
				 System.out.print("this is prime number:");
		}
		else
		{
          System.out.println("This is Not prime Number:");
		}
			
		
	}
}
