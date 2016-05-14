package ServerUniversel;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 25/04/2016.
 */
public interface MyRegistryInterface extends Remote {

    void bind(String key,Serializable obj) throws RemoteException;
    Serializable lookup(String key) throws RemoteException;
    String getUrl();
}
