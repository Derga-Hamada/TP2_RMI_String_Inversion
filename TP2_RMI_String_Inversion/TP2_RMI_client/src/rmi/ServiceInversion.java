package rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceInversion extends Remote {
    String inverserChaine(String chaine) throws RemoteException;
}