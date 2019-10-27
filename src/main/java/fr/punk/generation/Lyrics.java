package fr.punk.generation;

import org.marpunk.core.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Lyrics {

    private List<Sentence> sentences = new ArrayList<>();

    public Lyrics(Sentence first, Sentence second) {
        this.sentences.add(first);
        this.sentences.add(second);
    }

    public int size() {
        return sentences.size();
    }

    public Sentence get(int index) {
        return sentences.get(index);
    }

}
