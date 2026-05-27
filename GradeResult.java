import java.util.Scanner;
class  GradeResult
{
	public static void main(String[] args) 
	{   Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Grade");
	    char grade = sc.next().charAt(0);
	if (grade=='A'||grade=='a')
	{ System.out.println("You got first rank:");

	}
	else if(grade=='B'||grade=='b') 
    {
	 System.out.println("You got Second rank:");

	}
	else if (grade=='C'||grade=='c')
	{ System.out.println("You got Third rank:");
	}
    else if (grade=='D'||grade=='d')
    { System.out.println("You got first class:");
    }
	else if (grade=='E'||grade=='e')
	{System.out.println("You got second class:");
	}
	else if (grade=='F'||grade=='f')
	{System.out.println("You got third class:");

	}
	else if (grade=='E'||grade=='e')
	{System.out.println("You got just fail:");

	}
	else 

	{
		System.out.println("Enter valid grade:");
	}

  
    }
}