class  Pattern17
{
	public static void main(String[] args) 
	{   int space = 4;
	    int star = 1;
		for (int i=1;i<=5;i++)
		{
			for (int a=1;a<=space ;a++)
			{
				System.out.print("   ");
			}
			for (int b=1;b<=star  ;b++)
			{
				System.out.print(" * ");
			}
			space--;
			star=star + 2;
		    System.out.println();

		} 

	}
}
