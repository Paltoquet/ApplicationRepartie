package ServerUniversel;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 25/04/2016.
 */
public interface MyRegistryInterface extends Remote {

    void bind(String key, Serializable obj) throws RemoteException, AlreadyBoundException;
    Serializable lookup(String key) throws RemoteException, NotBoundException;
    String getUrl() throws RemoteException;
    byte[] download(String file) throws RemoteException;
    void upload(byte[] buff,String name) throws RemoteException;
}
