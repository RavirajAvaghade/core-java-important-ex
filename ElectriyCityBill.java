import java.util.Scanner;
Class ElectriyCityBill
{
  public static void main(string[]args);
 {
     Scanner sc = new Scanner(System.in);
     System.out.println("Enter Customer Name:");
     char customer = nextLine();
	 System.out.println("Enter units consumed:");
	 int units = nextInt();
     double bill =0;
     if (units<=100)
     {
		bill=units*1.5;
     }
	 else if (units<=200)
	 {
		 bill=(100*1.5) + (units-100)*2.0;
	 }
	 else if (units<=300)
	 {
		 bill  = (100*1.5) + (100*.0) + (units-200) * 3.0;
	 }
	 else 
	 {
		 bill = (100*1.5) + (100*2.0) + (100*3.0) + (units-300) *5.0;
		
	 }
	
     double fixedcgarge 
  
  
  
  
  
  
   }
} 
 