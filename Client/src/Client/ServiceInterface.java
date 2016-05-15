package Client;


import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by user on 07/05/2016.
 */
public interface ServiceInterface extends Remote {

    void ajouterMusique(String genre, Musique m) throws RemoteException;
    Musique recupererMusique(String genre)throws RemoteException;
    String getUrl();
}
