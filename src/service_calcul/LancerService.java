import java.rmi.NotBoundException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class LancerService
{
    public static void main(String[] args) throws RemoteException
    {
        int portService = 0;
        try {
            //On crée une instance du service
            Calcul serviceCalcul = new Calcul();
            CalculInterface serviceInterface = (CalculInterface) UnicastRemoteObject.exportObject(serviceCalcul, portService);

            // Recupération du serveur central
            Registry reg = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            ServiceDistributeur distributeur = (ServiceDistributeur) reg.lookup("distributeur");

            // Ajout du service au serveur central
            distributeur.ajoutCalcul(serviceInterface);

            System.out.println("Noeud de calcul lancé");
        }catch (RemoteException r){
            System.out.println("Impossible d'ajouter le service au serveur");
            r.printStackTrace();
        }
         catch (NotBoundException e) {
            System.out.println("Impossible d'acceder au serveur central");
        }
    }
}