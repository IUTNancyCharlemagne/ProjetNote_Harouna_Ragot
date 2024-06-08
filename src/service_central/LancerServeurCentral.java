import java.rmi.server.UnicastRemoteObject;
import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class LancerServeurCentral {
    public static void main(String[] args) {
        try {
            Distributeur distributeur = new Distributeur();
            ServiceDistributeur sd = (ServiceDistributeur)UnicastRemoteObject.exportObject(distributeur,0);
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("distributeur", sd);
            System.out.println("Serveur central lancé");
        } catch(RemoteException e){
            System.out.println(e.getMessage());
        }
    }
}