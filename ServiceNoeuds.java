import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceNoeuds extends Remote {

    void enregistrerNoeud(ServiceCalcul serviceCalcul) throws RemoteException;

    List<ServiceCalcul> getNoeuds() throws RemoteException;
}