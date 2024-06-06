import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Distributeur implements ServiceDistributeur {
    List<CalculInterface> listeServices = new ArrayList<>();
    int indice = 0;

    @Override
    public synchronized CalculInterface demanderService() throws RemoteException {
        // Vérification si il y a bien un service
        if (this.listeServices.isEmpty()) {
            return null;
        }
        indice++;
        if (indice >= this.listeServices.size()){
            indice = 0;
        }

        return this.listeServices.get(indice);
    }

    @Override
    public synchronized void  ajoutCalcul(CalculInterface serviceCalcul) throws RemoteException {
        synchronized (this.listeServices){
            this.listeServices.add(serviceCalcul);
            System.out.println("Service ajouté");
        }


    }

    @Override
    public synchronized void supprimerCalcul(CalculInterface serviceCalcul) throws RemoteException {
        synchronized (this.listeServices){
            this.listeServices.remove(serviceCalcul);
            System.out.println("Service supprimé");
        }

    }


}
