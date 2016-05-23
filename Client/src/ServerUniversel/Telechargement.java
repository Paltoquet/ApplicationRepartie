package ServerUniversel;

/**
 * Created by user on 23/05/2016.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 23/05/2016.
 */
public interface Telechargement extends Remote {

    byte[] download(String file) throws RemoteException;
    void upload(byte[] buff, String name) throws RemoteException;

}
