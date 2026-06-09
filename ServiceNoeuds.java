import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceNoeuds extends Remote {

    void ajouterNoeud(ServiceCalcul noeud) throws RemoteException;

    List<ServiceCalcul> getNoeudsDisponibles() throws RemoteException;
}