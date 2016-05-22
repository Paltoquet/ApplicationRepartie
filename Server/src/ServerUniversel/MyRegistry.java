package ServerUniversel;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * Created by user on 25/04/2016.
 */
public class MyRegistry extends UnicastRemoteObject implements MyRegistryInterface {

    private HashMap<String,Serializable>map;

    protected MyRegistry() throws RemoteException {
        map=new HashMap<String, Serializable>();
    }

    @Override
    public void bind(String key,Serializable obj) throws RemoteException, AlreadyBoundException {
        if(map.containsKey(key))
            throw new AlreadyBoundException();
        map.put(key, obj);
        System.out.println("Ajout sur la clé " + key);
    }

    @Override
    public Serializable lookup(String key) throws RemoteException, NotBoundException {
        if(!map.containsKey(key))
            throw new NotBoundException();
        System.out.println("Accès sur la clé " + key);
        return map.get(key);
    }

    @Override
    public String getUrl() throws RemoteException {
        return "tcp://localhost:61616";
    }
    @Override
    public byte[] download(String fil) {
        File file=new File(fil);
        byte buffer[]=new byte[(int)file.length()];
        System.out.println("telechargement de "+fil+" taille: "+ (int)file.length());
        BufferedInputStream input= null;
        try {
            input = new BufferedInputStream(new FileInputStream(fil));
            input.read(buffer,0,buffer.length);
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public void upload(byte[] buff,String name) {
        BufferedOutputStream output= null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(name));
            output.write(buff,0,buff.length);
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
