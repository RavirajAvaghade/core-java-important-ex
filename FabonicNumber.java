import java.util.Scanner;
class  FabonicNumber
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter a Number:");
		int n= sc.nextInt();
        int a = 0, b =1;
		System.out.println("fabonic series:" +a+ " "  +b);
		for (int i =2;i<n;i++)
		{
			int c = a+b;
			System.out.println(" "+c);
		}
	}
}
