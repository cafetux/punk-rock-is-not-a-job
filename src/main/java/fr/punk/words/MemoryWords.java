package fr.punk.words;

import org.marpunk.core.Sentence;
import org.marpunk.core.word.Word;
import org.marpunk.core.word.Words;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 *
 */
public class MemoryWords implements Words {

    private Map<String,List<Word>> links = new HashMap<>();

    @Override
    public List<Word> getStartingWords() {
        return unmodifiableList(links.getOrDefault(START.getKey(), emptyList()));
    }

    @Override
    public List<Word> findCandidatFor(Word word) {
        return unmodifiableList(links.getOrDefault(word.getKey(), emptyList()));
    }

    @Override
    public void save(Sentence words) {
        //System.out.println(words.format());
        Word previous = START;
        for (Word current : words) {
            push(previous, current);
            previous=current;
        }
        push(previous, END);
    }

    private void push(Word key, Word newValue) {
        System.out.println("push("+key.getValue()+","+newValue.getValue()+")");
        List<Word> wordLinks = links.getOrDefault(key.getKey(), new ArrayList<>());
        wordLinks.add(newValue);
        links.put(key.getKey(), wordLinks);
    }
}
