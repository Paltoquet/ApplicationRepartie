package Client;

import ServerUniversel.MyRegistryInterface;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        /*if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());*/

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
            byte[] data=reg.download("Server/src/File/halo.mp3");
            BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream("Client/src/Client/File/halo.mp3"));
            output.write(data,0,data.length);
            output.flush();
            output.close();
            System.out.println("fin du telechargement taille: "+ data.length);
            //musique.ajouterMusique("Symphonie",halo);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
