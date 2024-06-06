import raytracer.Image;
import raytracer.Scene;
import java.rmi.RemoteException;
import java.time.Duration;
import java.time.Instant;

public class Calcul implements CalculInterface
{
    @Override
    public Image calculer(Scene scene, Coordonnees coordonnee) throws RemoteException{
        // Chronométrage du temps de calcul
        Instant debut = Instant.now();
        System.out.println("Calcul de l'image :\n - Coordonnées : "+ coordonnee.x +","+ coordonnee.y +"\n - Taille "+ coordonnee.l + "x" + coordonnee.h);
        Image image = scene.compute(coordonnee.x, coordonnee.y,coordonnee.l, coordonnee.h);
        Instant fin = Instant.now();

        long duree = Duration.between(debut, fin).toMillis();
        System.out.println("Image calculée en :"+duree+" ms");
        return image;
    }
}