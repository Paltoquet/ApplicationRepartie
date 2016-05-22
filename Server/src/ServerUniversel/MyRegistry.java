package ServerUniversel;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by user on 25/04/2016.
 */
public class MyRegistry extends UnicastRemoteObject implements MyRegistryInterface {

    private HashMap<String,Serializable> map;
    private HashMap<String, Integer> mapCounter;
    private PriorityQueue<String> lastKeys;
    private final static int QUEUE_SIZE = 10;

    protected MyRegistry() throws RemoteException {
        map = new HashMap<String, Serializable>();
        mapCounter = new HashMap<String, Integer>();
        lastKeys = new PriorityQueue<>();
    }

    /**
     * Bind l'objet obj avec la clef key
     * @param key
     *      Clefs a associer a l'objet
     * @param obj
     *      Objet a enregistrer
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    @Override
    public void bind(String key, Serializable obj) throws RemoteException, AlreadyBoundException {
        if(map.containsKey(key))
            throw new AlreadyBoundException();
        map.put(key, obj);
        mapCounter.put(key, 0);
        saveInQueue(key);
        System.out.println("## Bind sur la clef : " + key);
    }

    /**
     * Remplace le binding de la clef key avec l'objet obj
     * @param key
     *      Clefs a associer a l'objet
     * @param obj
     *      Objet a enregistrer
     * @throws RemoteException
     */
    @Override
    public void rebind(String key, Serializable obj) throws  RemoteException {
        map.put(key, obj);
        mapCounter.put(key, 0);
        saveInQueue(key);
        System.out.println("## Rebind sur la clef : " + key);
    }

    /**
     * Methode pour supprimer le binding de la clef key
     * @param key
     *      Clef dont y faut supprimer le binding
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public void unbind(String key) throws RemoteException, NotBoundException {
        if(!map.containsKey(key))
            throw new NotBoundException();
        map.remove(key);
        System.out.println("## Unbind sur la clef : " + key);
    }

    /**
     * Methode pour obtenir toutes les clefs enregistree
     * @return
     *      Un Set contenant toutes les clefs enregistrees
     * @throws RemoteException
     */
    @Override
    public String[] list() throws RemoteException {
        String[] list = new String[map.size()];
        int i = 0;
        System.out.print("## Recuperation de la liste des clefs\n" +
                "   => Resultat : ");
        for(String s : map.keySet()){
            list[i] = s;
            System.out.print(s + ", ");
            i++;
        }
        System.out.println();
        return list;
    }

    /**
     * Methode pour obtenir les 10 dernieres clefs enregistrees
     * @return
     *      Un tableau contenant les 10 dernieres clefs enregistrees
     * @throws RemoteException
     */
    @Override
    public LinkedList<String> getLastKeys() throws RemoteException {
        System.out.println("## Recherche des 10 dernieres clefs recherchees\n" +
                "   => Resultat : " +  lastKeys);
        LinkedList<String> res = new LinkedList<>();
        for(String s : lastKeys)
            res.add(s);
        return res;
    }

    /**
     * Methode permettant d'obtenir la clefs la plus recherchee
     * @return
     *      La clefs la plus recherche (retourne null si aucune clefs enregistree)
     * @throws RemoteException
     */
    @Override
    public String getMostSearchedKey() throws RemoteException {
        String mostSearchedKey = null;
        Integer max = 0;
        Integer currentValue;
        for(String key : map.keySet()){
            if((currentValue = mapCounter.get(key)) >= max) {
                mostSearchedKey = key;
                max = currentValue;
            }
        }
        System.out.println("## Recherche de la clef la plus demandee\n" +
                "   => Resultat : " + mostSearchedKey + " (" + mapCounter.get(mostSearchedKey) + " lookup(s))");
        return mostSearchedKey;
    }

    /**
     * Retourne l'objet enregistre avec la clef key
     * @param key
     *      Clef a laquelle faire acces
     * @return
     *      L'objet trouve avec la clef key
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public Serializable lookup(String key) throws RemoteException, NotBoundException {
        if(!map.containsKey(key))
            throw new NotBoundException();
        mapCounter.put(key, mapCounter.get(key) + 1);
        System.out.println("## Acces sur la clef : " + key);
        return map.get(key);
    }

    /**
     * Methode pour connaitre le nombre d'appel a une clef
     * @param key
     *      Clef pour laquelle on veux connaitre le nombre d'appels
     * @return
     *      Nombre d'appel a la clef 'key'
     * @throws RemoteException
     * @throws NotBoundException
     */
    @Override
    public Integer keyNumberLookup(String key) throws RemoteException, NotBoundException {
        if(!map.containsKey(key)){
            System.out.println("## Tentative d' acces au nombre de lookup de la clef : " + key);
            throw new NotBoundException();
        }
        int nb = mapCounter.get(key);
        System.out.println("## Acces au nombre de lookup de la clef : " + key + "\n" +
                "   => Reultat : " + nb);
        return nb;
    }

    @Override
    public String getUrl() throws RemoteException {
        return "tcp://localhost:61616";
    }

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

    /**
     * Methode pour sauvegarder les 10 dernieres clefs utilisees
     * @param s
     *      Clefs a enregistrer dans la queue
     */
    private void saveInQueue(String s){
        lastKeys.add(s);
        if(lastKeys.size() > QUEUE_SIZE)
            lastKeys.poll();
    }
}
