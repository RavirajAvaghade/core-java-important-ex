class  Pattern24
{
	public static void main(String[] args) 
	{   
		int space = 0;
		int star = 5;
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
			star--;
			space++;
		    System.out.println();
		}
	}
}
