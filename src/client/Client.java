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
        int nbLongPart = (int) (Math.sqrt(nbPart));
        int partLargeur = largeur/nbLongPart;
        int partHauteur = hauteur/nbLongPart;
        int x;
        int y = 0;
        List<Coordonnees> listeCoordonnees = new ArrayList<>();

        for (int i=0; i<nbLongPart; i++){
            x=0;
            for (int j=0; j<nbLongPart; j++){
                listeCoordonnees.add(new Coordonnees(x,y,partLargeur,partHauteur));
                x+= partLargeur;

            }
            y+= partHauteur;
        }
        //Liste des coordonnées de chacune des parties de l'images
        return listeCoordonnees;
    }

    public static void calculerImage(ServiceDistributeur distributeur , String fichier_description, int nbPart, int largeur, int hauteur){
        List<Coordonnees> list = Client.diviserImage(nbPart, largeur, hauteur);

        // Initialisation d'une scène depuis le modèle
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        // création d'une fenêtre
        Disp disp = new Disp("Raytracer", largeur, hauteur);

        // Pour chaque partie de l'image
        for (Coordonnees c : list){
            // On crée un thread

            Thread thread = new Thread() {
                public void run() {

                    // Recupération d'un service de calcul
                    CalculInterface serviceCalcul = null;
                    try {
                        serviceCalcul = distributeur.demanderService();
                    } catch (RemoteException e) {
                        System.out.println("Le serveur n'est pas disponible");
                        e.printStackTrace();
                    }

                    if (serviceCalcul != null){
                        // Calcule de l'image
                        try {
                            raytracer.Image image = serviceCalcul.calculer(scene, c);
                            System.out.printf(c.x +" " + c.y);
                            disp.setImage(image, c.x, c.y);
                        }
                        catch (RemoteException e){
                            e.printStackTrace();
                            try {
                                distributeur.supprimerCalcul(serviceCalcul);
                            }
                            catch (RemoteException re){
                                e.printStackTrace();
                            }
                            System.out.println("Service supprimée car nous n'avons pas reussi à faire le calcul");
                        } catch (ServerNotActiveException e) {
                            e.printStackTrace();
                            try {
                                distributeur.supprimerCalcul(serviceCalcul);
                            }
                            catch (RemoteException re){
                                e.printStackTrace();
                            }
                            System.out.println("Service supprimée car nous n'avons pas reussi à nous connecter au serveur");
                        }
                    }
                }
            };

            // Lancement du thread
            thread.start();
        }

    }
}
