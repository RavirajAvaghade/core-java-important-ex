import java.util.Scanner;

public class JavaJarvis {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! I am Java Jarvis. Type your command:");
        while(true) {
            String command = sc.nextLine().toLowerCase();
            if(command.contains("hello")) {
                System.out.println("Hello Raviraj!");
            } else if(command.contains("youtube")) {
                System.out.println("Opening YouTube...");
                try { 
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://www.youtube.com");
                } catch(Exception e) { e.printStackTrace(); }
            } else if(command.contains("exit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Command not recognized.");
            }
        }
        sc.close();
    }
}
