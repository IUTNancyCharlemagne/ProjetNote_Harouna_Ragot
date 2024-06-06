import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceDistributeur extends Remote {

    public CalculInterface demanderService() throws RemoteException;

    public void ajoutCalcul(CalculInterface serviceCalcul) throws RemoteException;

    public void supprimerCalcul(CalculInterface serviceCalcul) throws RemoteException;
}
