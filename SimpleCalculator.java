import java.util.Scanner;
class  SimpleCalculator
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
	    System.out.println("===================");
	    System.out.println("  Simple calculator");
		System.out.println("===================");
        System.out.println("Enter 1 for Addition");
		System.out.println("Enter 2 for Substraction");
		System.out.println("Enter 3 for Multipication");
		System.out.println("Enter 4 for division(quotent)");
		System.out.println("Enter 5 for division(remainder)");
		System.out.println("Enter Your Choice: ");
		int choice = sc.nextInt();
		System.out.println("Enter first Number" );
		int a = sc.nextInt();
		System.out.println("Enter Second Number:");
		int b = sc.nextInt();
		System.out.println("===================");
		switch(choice)
		{
		   case 1:
			{ 
			   System.out.println("You have Choosen Addition:"+(a+b));
			   break;
            }
		   case 2:
			{ 
			   System.out.println("You have Choosen Substraction:"+(a-b));
			   break;
            }
			case 3:
			{ 
			   System.out.println("You have Choosen Multiplication:"+(a*b));
			   break;
            }
            case 4:
			{ 
			   System.out.println("You have Choosen division(quotent):"+(a/b));
			   break;
            }
            case 5:
			{ 
			   System.out.println("You have Choosen division(remainder):"+(a%b));
			   break;
            }
			default : System.out.println("Enter valid input:");



		
		}
		System.out.println("============================");
		System.out.println(     "Thank You!!!"            );
		System.out.println("=============================");
	}
}
