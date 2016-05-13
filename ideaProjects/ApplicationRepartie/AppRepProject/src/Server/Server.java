package Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by user on 25/04/2016.
 */
public class Server {

    public static void main(String args[]) {
        /*argument de vm:
             -Djava.rmi.server.useCodebaseOnly=false
             -Djava.security.policy="emplacement du fichier de policy
             -Djava.naming.provider.url=rmi://127.0.0.1:4000


          lancement du rmi registry:
              rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 4000

        */
        System.out.println("erver starting");
        try {
            Registry registry = LocateRegistry.createRegistry(4000);
            MyRegistry r=new MyRegistry();
            // Bind the remote object's stub in the registry
            registry.bind("Registry", r);
            System.err.println("Server ready");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
}
