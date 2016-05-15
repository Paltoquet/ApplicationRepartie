package ServerUniversel;

import java.io.Serializable;
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
}
