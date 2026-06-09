import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

public class CoordinateurRaytracer {

    public static String aide =
            "Usage : java CoordinateurRaytracer [fichier-scene] [largeur] [hauteur] [hote] [port]";

    public static void main(String[] args) {

        String fichierDescription = "simple.txt";
        int largeur = 512;
        int hauteur = 512;
        String hoteServeur = "localhost";
        int port = 1099;

        if (args.length > 0) {
            fichierDescription = args[0];
        }

        if (args.length > 1) {
            largeur = Integer.parseInt(args[1]);
        }

        if (args.length > 2) {
            hauteur = Integer.parseInt(args[2]);
        }

        if (args.length > 3) {
            hoteServeur = args[3];
        }

        if (args.length > 4) {
            port = Integer.parseInt(args[4]);
        }

        try {
            System.out.println(aide);

            Disp disp = new Disp("Raytracer distribué", largeur, hauteur);

            Scene scene = new Scene(fichierDescription, largeur, hauteur);

            Registry registry = LocateRegistry.getRegistry(hoteServeur, port);

            ServiceNoeuds serviceNoeuds =
                    (ServiceNoeuds) registry.lookup("noeuds");

            List<ServiceCalcul> noeuds = serviceNoeuds.getNoeuds();

            if (noeuds.size() == 0) {
                System.out.println("Aucun nœud disponible.");
                return;
            }

            System.out.println("Nombre de nœuds disponibles : " + noeuds.size());

            int nbNoeuds = noeuds.size();

            Image[] resultats = new Image[nbNoeuds];
            int[] positionsX = new int[nbNoeuds];
            int[] positionsY = new int[nbNoeuds];

            List<Thread> threads = new ArrayList<Thread>();

            int hauteurFragment = hauteur / nbNoeuds;

            Instant debut = Instant.now();

            for (int i = 0; i < nbNoeuds; i++) {

                final int index = i;

                final int x0 = 0;
                final int y0 = i * hauteurFragment;
                final int l = largeur;

                final int h;

                if (i == nbNoeuds - 1) {
                    h = hauteur - y0;
                } else {
                    h = hauteurFragment;
                }

                final ServiceCalcul noeud = noeuds.get(i);

                positionsX[index] = x0;
                positionsY[index] = y0;

                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Envoi du fragment " + index);
                            System.out.println(" - position : " + x0 + "," + y0);
                            System.out.println(" - taille : " + l + "x" + h);

                            Image image = noeud.calculerFragment(scene, x0, y0, l, h);

                            resultats[index] = image;

                            System.out.println("Fragment " + index + " reçu.");

                        } catch (Exception e) {
                            System.err.println("Erreur sur le fragment " + index);
                            e.printStackTrace();
                        }
                    }
                });

                threads.add(t);
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }

            for (int i = 0; i < resultats.length; i++) {
                if (resultats[i] != null) {
                    disp.setImage(resultats[i], positionsX[i], positionsY[i]);
                } else {
                    System.out.println("Fragment " + i + " manquant.");
                }
            }

            Instant fin = Instant.now();
            long duree = Duration.between(debut, fin).toMillis();

            System.out.println("Image calculée en distribué : " + duree + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}