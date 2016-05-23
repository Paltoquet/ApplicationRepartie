package ServerUniversel;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by user on 23/05/2016.
 */
public class TelechargementImpl  extends UnicastRemoteObject implements Telechargement{

    protected TelechargementImpl() throws RemoteException {
    }

    /**
     *
     * @param fil
     * le fichier à telecharger
     * @return
     * le flot de byte qui sera envoyé au client
     */
    @Override
    public byte[] download(String fil) {
        File file = new File(fil);
        byte buffer[] = new byte[(int) file.length()];
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(fil));
            input.read(buffer, 0, buffer.length);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("## Telechargement de " + fil + " (Taille : "+ (int) file.length() + ")");
        return buffer;
    }

    /**
     *
     * @param buff
     * le flot de byte de donnéee
     * @param name
     * l'emplacement du fichier dest
     */
    @Override
    public void upload(byte[] buff, String name) {
        BufferedOutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(name));
            output.write(buff, 0, buff.length);
            output.flush();
            output.close();
            System.out.println("## Chargement de " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
