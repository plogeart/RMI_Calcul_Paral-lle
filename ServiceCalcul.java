import java.rmi.Remote;
import java.rmi.RemoteException;
import raytracer.Image;
import raytracer.Scene;

public interface ServiceCalcul extends Remote {

    Image calculerFragment(Scene scene, int x0, int y0, int largeur, int hauteur)
        throws RemoteException;
}