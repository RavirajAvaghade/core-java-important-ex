import java.util.Scanner;

class MarksGrade {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        if (marks >= 90 && marks <= 100) {
            System.out.println("Grade A+");
        } else if (marks >= 80) {
            System.out.println("Grade A");
        } else if (marks >= 70) {
            System.out.println("Grade B");
        } else if (marks >= 60) {
            System.out.println("Grade C");
        } else if (marks >= 50) {
            System.out.println("Grade D");
        } else if (marks >= 40) {
            System.out.println("Grade E");
        } else if (marks >= 0 && marks < 40) {
            System.out.println("You are Fail in Exam");
        } else {
            System.out.println("Enter valid marks (0-100)!");
        }
    }
}
