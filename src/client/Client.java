import raytracer.Disp;
import raytracer.Scene;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client {
    public static List<Coordonnees> diviserImage(int nbPart, int largeur, int hauteur){
        // Nombre de division de l'image pour faire une grille
        int partLargeur = largeur/nbPart;
        int partHauteur = hauteur;
        int x = 0;
        List<Coordonnees> listeCoordonnees = new ArrayList<>();

        for (int j=0; j<nbPart; j++){
            listeCoordonnees.add(new Coordonnees(x,0,partLargeur,partHauteur));
            x+= partLargeur;

        }
        //Liste des coordonnées de chacune des parties de l'images
        return listeCoordonnees;
    }

    public static void calculerImage(ServiceDistributeur distributeur, String fichier_description, int nbPart, int largeur, int hauteur) {
        List<Coordonnees> listeCoordonnees = Client.diviserImage(nbPart, largeur, hauteur);

        // Initialisation d'une scène depuis le modèle
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        // création d'une fenêtre
        Disp disp = new Disp("Raytracer", largeur, hauteur);

        for (Coordonnees c : listeCoordonnees) {
            new Thread(() -> {
                boolean success = false;
                while (!success) {
                    // Récupération d'un service de calcul
                    CalculInterface serviceCalcul = null;
                    try {
                        serviceCalcul = distributeur.demanderService();
                    } catch (RemoteException e) {
                        System.out.println("Le serveur n'est pas disponible");
                    }

                    if (serviceCalcul != null) {
                        // Calcul de l'image
                        try {
                            raytracer.Image image = serviceCalcul.calculer(scene, c);
                            System.out.println(c.x + " " + c.y);
                            disp.setImage(image, c.x, c.y);
                            success = true; // Calcul réussi
                        } catch (RemoteException | ServerNotActiveException e) {
                            try {
                                distributeur.supprimerCalcul(serviceCalcul);
                            } catch (RemoteException re) {
                                System.out.println("Erreur lors de la suppression du service de calcul");
                            }
                            System.out.println("Service supprimé car nous n'avons pas réussi à faire le calcul");
                        }
                    }
                }
            }).start();
        }
    }

}
