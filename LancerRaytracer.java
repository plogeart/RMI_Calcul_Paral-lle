import java.time.Duration;
import java.time.Instant;

import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

public class LancerRaytracer {

    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";
     
    public static void main(String args[]){

        // Le fichier de description de la scène si pas fournie
        String fichier_description="simple.txt";

        // largeur et hauteur par défaut de l'image à reconstruire
        int largeur = 512, hauteur = 512;
        
        if(args.length > 0){
            fichier_description = args[0];
            if(args.length > 1){
                largeur = Integer.parseInt(args[1]);
                if(args.length > 2)
                    hauteur = Integer.parseInt(args[2]);
            }
        }else{
            System.out.println(aide);
        }
        
   
        // création d'une fenêtre 
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        
        // Initialisation d'une scène depuis le modèle 
        Scene scene = new Scene(fichier_description, largeur, hauteur);
        
        // Calcul de l'image de la scène les paramètres : 
        // - x0 et y0 : correspondant au coin haut à gauche
        // - l et h : hauteur et largeur de l'image calculée
        // Ici on calcule toute l'image (0,0) -> (largeur, hauteur)
        
        Instant debut = Instant.now();

        int x0 = 0;
        int y0 = 0;
        int l = largeur / 2;
        int h = hauteur / 2;

        System.out.println("Calcul du morceau 1 :");
        System.out.println(" - Coordonnées : " + x0 + "," + y0);
        System.out.println(" - Taille : " + l + "x" + h);

        Image image1 = scene.compute(x0, y0, l, h);
        disp.setImage(image1, x0, y0);

       
        x0 = largeur / 2;
        y0 = hauteur / 2;
        l = largeur / 2;
        h = hauteur / 2;

        System.out.println("Calcul du morceau 2 :");
        System.out.println(" - Coordonnées : " + x0 + "," + y0);
        System.out.println(" - Taille : " + l + "x" + h);

        Image image2 = scene.compute(x0, y0, l, h);
        disp.setImage(image2, x0, y0);

        Instant fin = Instant.now();

        long duree = Duration.between(debut, fin).toMillis();

        System.out.println("Image calculée en : " + duree + " ms");
    
    }	
}
