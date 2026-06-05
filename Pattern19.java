class  Pattern19
{
	public static void main(String[] args) 
	{   int star = 1;
	    
		for (int i=1;i<=9;i++)
		{
			for (int a=1;a<=star  ;a++)
			{
				System.out.print(" * ");
			}
			if (i<=4)
			{
				star++;
			}
			else
			{
				star--;

			}
			System.out.println();
		}
	}
}
