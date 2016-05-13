package Client;

import Server.MyRegistryInterface;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by user on 25/04/2016.
 */
public class Client {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("10.212.104.230",4000);
            MyRegistryInterface reg= (MyRegistryInterface) registry.lookup("Registry");
            //ServiceInterface musique= (ServiceInterface) reg.lookup("Musique");
            Musique halo=new Musique("Second Prelude",108,"Paul Lupson");
            reg.bind("Halo",halo);
            //musique.ajouterMusique("Symphonie",halo);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
