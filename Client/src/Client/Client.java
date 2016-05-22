package Client;

import ServerUniversel.MyRegistryInterface;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
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

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4000);
            MyRegistryInterface reg = (MyRegistryInterface) registry.lookup("Registry");

            System.out.println("## Creation de deux musiques : Halo & Halo2");
            Musique halo = new Musique("Second Prelude", 108, "Paul Lupson");
            MusiqueLyrique halo2 = new MusiqueLyrique("Truc", 100, "Machin");

            System.out.println("## Tentative de bind les deux musiques");
            try {
                reg.bind("Halo", halo);
            } catch (AlreadyBoundException e){
                System.out.println("Halo already bound...");
            }

            try {
                reg.bind("Halo2", halo2);
            } catch (AlreadyBoundException e) {
                System.out.println("Halo2 already bound...");
            }

            System.out.println("## Lookup sur halo");
            halo = (Musique) reg.lookup("Halo");
            System.out.println("    => " + halo.getName());

            System.out.println("## Recuperation des 10 dernieres clefs auquelles un acces a ete fait\n" +
                    "   => Resultat : " + reg.getLastKeys());

            System.out.println("## Recuperation du nombre de lookup pour halo2\n" +
                    "   => Resultat : " + reg.keyNumberLookup("Halo2"));

            String mostSearchedKey = reg.getMostSearchedKey();
            System.out.println("## Recherche de la clef comptabilisant le plus d'acces\n" +
                    "   => Resultat : " + mostSearchedKey + " (" + reg.keyNumberLookup(mostSearchedKey) + " lookup(s))");

            System.out.print("## Recuperation de la liste des clefs enregistrees\n" +
                    "   => Resultat : ");
            for(String s : reg.list()){
                System.out.print(s);
            }
            System.out.println();

            System.out.println("Telechargement de Halo.mp3...");
            byte[] data=reg.download("src/File/halo.mp3");
            BufferedOutputStream output=new BufferedOutputStream(new FileOutputStream("src/Client/File/halo.mp3"));
            output.write(data,0,data.length);
            output.flush();
            output.close();
            System.out.println("Fin du telechargement de Halo.mp3 (taille: "+ data.length + " octets)");
        } catch (NotBoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
