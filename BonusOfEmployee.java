import java.util.Scanner;

class BonusOfEmployee {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your salary: ");
        double salary = sc.nextDouble();
        System.out.print("Enter your years of experience: ");
        int years = sc.nextInt();
		double bonus;

        
        if (years > 5) {
            bonus = salary * 0.40; 
            System.out.println("You got 40% bonus: " + bonus);
        } else {
            bonus = salary * 0.25;  
            System.out.println("You got 25% bonus: " + bonus);
        }

        
        System.out.println("Total Salary after bonus: " + (salary + bonus));
    }
}
