package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LanceurServeur {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // إنشاء نسخة من الكائن البعيد
            ServeurInversion objetDistant = new ServeurInversion();
            
            registry.rebind("ServiceInversion", objetDistant);
            
            System.out.println("Le Serveur RMI est prêt (Serveur Inversion)...");
            
        } catch (Exception e) {
            System.err.println("Erreur Serveur: " + e.toString());
            e.printStackTrace();
        }
    }
}