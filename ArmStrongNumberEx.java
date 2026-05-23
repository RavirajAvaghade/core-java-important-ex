import java.util.Scanner;
class ArmstrongNumEx
{ 
	public static void main(String[]args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a Number:");
		int n = sc.nextInt();
        int temp = num;
		int count = 0;
		while (temp!=0)
		{
			count++;
			temp=temp/10;
		 }
         temp = num;
		 int sum = 0;
		 while (temp!=0)
		 {
			 int ld = temp%10;
			 int p = 1;
			 for (int i = 1;i<=count;i++)
			 {
				 p = p*ld;
			 }
			 sum = sum+p;
			 temp = temp/10;
		 }
		 if (sum==num)
		 {
		 }
	    }


}