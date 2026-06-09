import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.time.Instant;
import raytracer.Image;
import raytracer.Scene;

public class NoeudCalculImpl extends UnicastRemoteObject implements ServiceNoeudCalcul {

    public NoeudCalculImpl() throws RemoteException {
        super();
    }

    public Image calculerFragment(Scene scene, int x0, int y0, int largeur, int hauteur)
        throws RemoteException {

        System.out.println("Calcul reçu :");
        System.out.println(" - coordonnées : " + x0 + "," + y0);
        System.out.println(" - taille : " + largeur + "x" + hauteur);

        Instant debut = Instant.now();

        Image resultat = scene.compute(x0, y0, largeur, hauteur);

        Instant fin = Instant.now();
        long duree = Duration.between(debut, fin).toMillis();

        System.out.println("Fragment terminé en " + duree + " ms");

        return resultat;
    }
}