package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientInversion {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            ServiceInversion stub = (ServiceInversion) registry.lookup("ServiceInversion");
            
            // قراءة نص من المستخدم لتجربة الخدمة
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter text to reverse it: ");
            String input = scanner.nextLine();
            
            String result = stub.inverserChaine(input);
            
            System.out.println("Result from the server: " + result);
            scanner.close();
            
        } catch (Exception e) {
            System.err.println("Erreur Client: " + e.toString());
            e.printStackTrace();
        }
    }
}