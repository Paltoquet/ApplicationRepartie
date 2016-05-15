package Client;

/**
 * Created by Théo on 14/05/2016.
 */
public class MusiqueLyrique extends Musique {
    public MusiqueLyrique(String name, int duree, String auteur) {
        super(name, duree, auteur);
    }

    @Override
    public String toString(){
        return "Je suis lyrique";
    }
}
