package classserver.Services;

import classserver.Data.Musique;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * Created by user on 07/05/2016.
 */
public class Service  extends UnicastRemoteObject implements ServiceInterface {

    HashMap<String,Musique> samples;

    protected Service() throws RemoteException {
        super();
        samples=new HashMap<String, Musique>();
        samples.put("Rock",new Musique("San Francisco",179,"Scott McKenzie"));
    }

    @Override
    public void ajouterMusique(String genre,Musique m) throws RemoteException {
        samples.put(genre,m);
    }

    @Override
    public Musique recupererMusique(String genre) throws RemoteException {
        return samples.get(genre);
    }
}
