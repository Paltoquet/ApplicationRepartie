package Client;

import ServerUniversel.MyRegistryInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by user on 25/04/2016.
 */
public class Client {

    public static void main(String[] args) {
        /*
        * VM options :
        *
        *  -Djava.security.policy=java.policy -Djava.rmi.server.codebase=http://BXXX:4001/
        */

        ThreadConsume t = new ThreadConsume("user", "tcp://localhost:61616", "chat");
        t.start();

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4000);
            MyRegistryInterface reg = (MyRegistryInterface) registry.lookup("Registry");
            //ServiceInterface musique= (ServiceInterface) reg.lookup("Musique");
            Musique halo = new Musique("Second Prelude", 108, "Paul Lupson");
            MusiqueLyrique halo2 = new MusiqueLyrique("Truc", 100, "Machin");
            try {
                reg.bind("Halo", halo);
                reg.bind("Halo2", halo2);
            } catch (AlreadyBoundException e){
                e.printStackTrace();
            }
            reg.lookup("Halo");
            System.out.println("LA MUSIQUE : " + reg.lookup("Halo2").toString());
            //musique.ajouterMusique("Symphonie",halo);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
