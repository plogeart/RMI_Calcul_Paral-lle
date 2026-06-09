import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServiceServeurNoeuds extends Remote {

    void ajouterNoeud(ServiceNoeudCalcul noeud) throws RemoteException;

    List<ServiceNoeudCalcul> getNoeudsDisponibles() throws RemoteException;
}