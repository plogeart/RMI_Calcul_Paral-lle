import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NoeudCalcul {

    public static void main(String[] args) {

        String hoteServeur = "localhost";
        int port = 1099;

        if (args.length > 0) {
            hoteServeur = args[0];
        }

        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        try {
            Registry registry = LocateRegistry.getRegistry(hoteServeur, port);

            ServiceNoeuds serviceNoeuds =
                    (ServiceNoeuds) registry.lookup("noeuds");

            ServiceCalcul serviceCalcul = new ServiceCalculImpl();

            serviceNoeuds.enregistrerNoeud(serviceCalcul);

            System.out.println("Nœud de calcul prêt.");
            System.out.println("Connecté au serveur : " + hoteServeur + ":" + port);

            while (true) {
                Thread.sleep(60000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}