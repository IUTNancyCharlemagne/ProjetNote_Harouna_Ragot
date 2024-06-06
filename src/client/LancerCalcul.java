import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class LancerCalcul {
    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";

    public static void main(String[] args)  throws RemoteException, NotBoundException, ServerNotActiveException{
        Registry registry = LocateRegistry.getRegistry(args[0], 7891);
        ServiceDistributeur distributeur = (ServiceDistributeur) registry.lookup("distributeur");


        // Le fichier de description de la scène si pas fournie
        String fichier_description="simple.txt";

        // largeur et hauteur par défaut de l'image à reconstruire
        int largeur = 512, hauteur = 512, nbPart = 1;

        if(args.length > 0){
            fichier_description = args[1];
            if(args.length > 1){
                largeur = Integer.parseInt(args[2]);
                if(args.length > 2)
                    hauteur = Integer.parseInt(args[3]);
                    if(args.length > 3)
                        nbPart = Integer.parseInt(args[4]);
            }
        }else{
            System.out.println(aide);
        }
        Client.calculerImage(distributeur, fichier_description, nbPart, largeur, hauteur);
    }
}
