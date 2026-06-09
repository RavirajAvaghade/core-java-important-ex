import java.util.Scanner;

public class ProfitCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read SP and CP from user
        System.out.print("Enter Selling Price (SP): ");
        double sp = sc.nextDouble();

        System.out.print("Enter Cost Price (CP): ");
        double cp = sc.nextDouble();

        // Calculate Profit
        double profit = sp - cp;

        // Print result
        System.out.println("Profit = " + profit);

        sc.close();
    }
}
