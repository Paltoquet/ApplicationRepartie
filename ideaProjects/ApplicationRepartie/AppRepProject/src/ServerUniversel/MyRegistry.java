package ServerUniversel;

import java.io.Serializable;
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
    public void bind(String key,Serializable obj) throws RemoteException {
        map.put(key,obj);
        System.out.println("ajout sur la clé "+key);
    }

    @Override
    public Serializable lookup(String key) throws RemoteException {
        System.out.println("accès sur la clé "+key);
        return map.get(key);
    }

    @Override
    public String getUrl() {
        return "tcp://localhost:61616";
    }
}
