import java.util.Scanner;
class  HcfLcm
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a First Number:");
		int a = sc.nextInt();
		System.out.println("Enter a Second Number:");
		int b = sc.nextInt();
		int x = a, y=b;
		while (b!=0)
		{
			int temp =b;
			b = a%b;
			a = temp;

		}
		 
		 int hcf = a;
		 int lcm = (x*y) / hcf;

		System.out.println("HCF ="+hcf);
		System.out.println("Lcm ="+lcm);
	}
}
