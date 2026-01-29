package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServeurInversion extends UnicastRemoteObject implements ServiceInversion {
    private static final long serialVersionUID = 1L;

    protected ServeurInversion() throws RemoteException {
        super();
    }

    @Override
    public String inverserChaine(String chaine) throws RemoteException {
        // هذا السطر سيظهر في واجهة الخادم لاحقاً
        System.out.println(">> طلب وارد لعكس الجملة: " + chaine);
        if (chaine == null) return null;
        return new StringBuilder(chaine).reverse().toString();
    }
}