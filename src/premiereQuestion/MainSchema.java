import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;
import java.io.*;
import java.time.*;

public class MainSchema {
    public static void main(String args[]) {
        // Le fichier de description 
        String fichierDescription = "simple.txt";

        // Tailles d'images
        int[][] tailles = {
                { 200, 200 },
                { 512, 512 },
                { 750, 750 },
                { 1024, 1024 },
                { 1500, 1500 },
                { 2048, 2048 },
                { 3000, 3000 },
                { 4096, 4096 }
        };

        // Création du fichier CSV
        String fichierCSV = "temps_calcul.csv";
        try (FileWriter writer = new FileWriter(fichierCSV)) {
            writer.append("Largeur,Hauteur,Temps de calcul (ms)\n");
            for (int[] taille : tailles) {
                int largeur = taille[0];
                int hauteur = taille[1];

                Instant debut = Instant.now();

                Scene scene = new Scene(fichierDescription, largeur, hauteur);

                int x0 = 0, y0 = 0;
                int l = largeur, h = hauteur;


                Image image = scene.compute(x0, y0, l, h);

                Instant fin = Instant.now();
                long duree = Duration.between(debut, fin).toMillis();
                writer.append(largeur + "," + hauteur + "," + duree + "\n");
            }

            System.out.println("Fichier CSV créé : " + fichierCSV);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
