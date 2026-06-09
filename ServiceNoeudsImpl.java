import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServiceNoeudsImpl extends UnicastRemoteObject implements ServiceNoeuds {

    private List<ServiceCalcul> noeuds;

    public ServiceNoeudsImpl() throws RemoteException {
        super();
        this.noeuds = new ArrayList<ServiceCalcul>();
    }

    public synchronized void enregistrerNoeud(ServiceCalcul serviceCalcul)
            throws RemoteException {

        this.noeuds.add(serviceCalcul);

        System.out.println("Nouveau nœud enregistré.");
        System.out.println("Nombre de nœuds disponibles : " + this.noeuds.size());
    }

    public synchronized List<ServiceCalcul> getNoeuds()
            throws RemoteException {

        return new ArrayList<ServiceCalcul>(this.noeuds);
    }
}