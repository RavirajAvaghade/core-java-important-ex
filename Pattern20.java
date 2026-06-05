class Pattern20 
{
	public static void main(String[] args) 
	{   

		int star =1;
		int space =4;
		for (int i=1;i<=9;i++)
		{
			for (int b=1;b<=space ;b++)
			{
				System.out.print("   ");
			}
		    for (int a =1;a<=star ;a++)
		    {
				System.out.print(" * ");
		    }
			if (i<=4)
			{
				star++;
				space--;
			}
			else
			{
				star--;
				space++;
			}
			System.out.println();
		}
	}
}
