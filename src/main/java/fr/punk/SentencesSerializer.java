package fr.punk;

import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SentencesSerializer {

    private static final String SAVE_FILENAME = "tree.serial";
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public void save(List<Sentence> load) {
        try {

            FileOutputStream fos = new FileOutputStream(SAVE_FILENAME);

            // création d'un "flux objet" avec le flux fichier
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            try {
                // sérialisation : écriture de l'objet dans le flux de sortie
                oos.writeObject(load);
                // on vide le tampon
                oos.flush();
            } finally {
                //fermeture des flux
                try {
                    oos.close();
                } finally {
                    fos.close();
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public List<Sentence> load() {

        try {
            // ouverture d'un flux d'entrée depuis le fichier "personne.serial"
            FileInputStream fis = new FileInputStream(SAVE_FILENAME);
            // création d'un "flux objet" avec le flux fichier
            ObjectInputStream ois= new ObjectInputStream(fis);
            try {
                // désérialisation : lecture de l'objet depuis le flux d'entrée
                return (List<Sentence>) ois.readObject();
            } finally {
                // on ferme les flux
                try {
                    ois.close();
                } finally {
                    fis.close();
                }
            }
        } catch(IOException | ClassNotFoundException ioe) {
            LOGGER.error("cannot load file",ioe);
        }
        return new ArrayList<>();
    }

}
