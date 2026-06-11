/* WAPTP a person is eligible for voting or not read age from user use conditional operator
 operator
 example:1
 input:17
 output:not eligible for voting
 */
import java.util.Scanner;
class  VotingEligiblity
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your age: ");
		int age = sc.nextInt();
		String result = (age>=18) ? "Eligible for voting":"Not eligible for voting";
		System.out.println(result);
	}
}

//q2) WAPTP profit for the given selling price and cost price
// hint;- read sp,cp,from user,print sp-cp
//q3) WAPTP area of triangle(hint) read the base and height from user.print
// 0.5*b*h
//Q4) WAPTP simple intrest
// hint:- read principle,rate of intrest and time from user.print 
// WAPTP to convert KM/h to M/s 