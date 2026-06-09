import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServeurNoeuds {

    public static void main(String[] args) {

        int port = 1099;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try {
            Registry registry;

            try {
                registry = LocateRegistry.createRegistry(port);
                System.out.println("Registry créé sur le port " + port);
            } catch (Exception e) {
                registry = LocateRegistry.getRegistry(port);
                System.out.println("Registry déjà existant sur le port " + port);
            }

            ServiceNoeuds serviceNoeuds = new ServiceNoeudsImpl();

            registry.rebind("noeuds", serviceNoeuds);

            System.out.println("Serveur de nœuds prêt.");
            System.out.println("Service enregistré sous le nom : noeuds");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}