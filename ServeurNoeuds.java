import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServeurNoeuds extends UnicastRemoteObject implements ServiceNoeuds {

    private List<ServiceCalcul> noeuds;

    public ServeurNoeuds() throws RemoteException {
        super();
        this.noeuds = new ArrayList<ServiceCalcul>();
    }

    public synchronized void ajouterNoeud(ServiceCalcul noeud) throws RemoteException {
        this.noeuds.add(noeud);

        System.out.println("Nouveau nœud ajouté.");
        System.out.println("Nombre de nœuds disponibles : " + this.noeuds.size());
    }

    public synchronized List<ServiceCalcul> getNoeudsDisponibles() throws RemoteException {
        return new ArrayList<ServiceCalcul>(this.noeuds);
    }
}