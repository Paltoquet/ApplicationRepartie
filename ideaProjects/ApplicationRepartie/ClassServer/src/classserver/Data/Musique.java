package classserver.Data;

import java.io.Serializable;

/**
 * Created by user on 07/05/2016.
 */
public class Musique implements Serializable {

    String name;
    int duree;
    String auteur;

    public Musique(String name, int duree, String auteur) {
        this.name = name;
        this.duree = duree;
        this.auteur = auteur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}
