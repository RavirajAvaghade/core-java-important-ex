class  Pattern26
{
	public static void main(String[] args) 
	{ 
		int space = 4;
		int star = 1;
		for (int i=1;i<=5;i++)
		{
			for (int b=1;b<=space ;b++ )
			{
				System.out.print("   ");
			}
			for (int a=1;a<=star ;a++)
			{
				System.out.print(" * ");
			}
			space--;
			star++;
			System.out.println();
			
		}
	}
}
