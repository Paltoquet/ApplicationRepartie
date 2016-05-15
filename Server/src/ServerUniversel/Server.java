package ServerUniversel;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by user on 25/04/2016.
 */
public class Server {

    public static void main(String args[]) {
        /*
        * Options de VM :
        *   -Djava.rmi.server.useCodebaseOnly=false
        *   -Djava.security.policy="emplacement du fichier de policy
        *   -Djava.naming.provider.url=rmi://127.0.0.1:4000
        *   -Djava.naming.factory.initial=com.sun.jndi.rmi.registry.RegistryContextFactory
        *
        * Lancement du rmi registry :
        *   start rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 4000
        *   start C:/"Program Files"/Java/jdk1.8.0_73/bin/rmiregistry.exe -J-Djava.rmi.server.useCodebaseOnly=false 4000
        *
        * VM options :
        *
        *  -Djava.security.policy=java.policy -Djava.rmi.server.codebase=http://BXXX:4001/
        */

        System.out.println("Server starting");

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Registry registry = LocateRegistry.getRegistry("localhost",4000);
            MyRegistry r = new MyRegistry();
            ThreadProducer t = new ThreadProducer("user", "tcp://localhost:61616", "chat");
            t.start();
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
