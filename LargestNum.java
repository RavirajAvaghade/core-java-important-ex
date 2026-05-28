class LargestNum 
{
	public static void main(String[] args) 
	{
		int a = 12;
		int b = 15; 
		int c = 20;
		int d = 100;
		int largest = (a>=b&&a>=c&&a>=d) ? a : (b>=c&&b>=d)?b:(c>=d) ? c:d;
		System.out.println("Largest of given three numbers : " +largest);
	}
}
