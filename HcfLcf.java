import java.util.Scanner;
class  HcfLcf
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter two numbers one by one:");
		int x = sc.nextInt();
		int y = sc.nextInt();
		int hcf = 1;
		for (int i =1;i<=x&&i<=y ;i++ )
		{
			if (x%i==0&&y%i==0)
			{
				hcf = i;
			}
		}
		System.out.println("hcf:" +hcf);
	}
}
