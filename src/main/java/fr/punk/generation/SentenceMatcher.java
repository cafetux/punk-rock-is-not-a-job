package fr.punk.generation;

import org.jetbrains.annotations.NotNull;
import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SentenceMatcher {


    private static final Logger LOGGER = LoggerFactory.getLogger(SentenceMatcher.class);


    public boolean match(Sentence sentence1, Sentence sentence2) {
        return sameSize(sentence1, sentence2) &&
                rhymes(sentence1, sentence2) &&
                notSameEndWord(sentence1,sentence2);
    }

    private boolean notSameEndWord(Sentence words1, Sentence words2) {
        boolean sameEndWord = lastWord(words1).equals(lastWord(words2));
        if(sameEndWord){
            LOGGER.warn("reject (same end word {}/{}) {}",lastWord(words1),lastWord(words2), words2.format());
        }
        return !sameEndWord;
    }

    private String lastWord(Sentence words1) {
        String value = words1.format().replaceAll("[?.;,!]","").trim();
        return value.substring(value.lastIndexOf(" "));
    }

    private boolean sameSize(Sentence sentence1, Sentence sentence2) {
        boolean match = sentence1.size() == sentence2.size();
        if(!match){
            LOGGER.warn("reject (not same size) {}", sentence2.format());
        }
        return match;
    }

    private boolean rhymes(Sentence sentence1, Sentence sentence2) {
        boolean match = end(lastWord(sentence1)).endsWith(end(lastWord(sentence2)));
        if(!match){
            LOGGER.warn("reject (not rhymes) {}", sentence2.format());
        }
        return match;
    }

    @NotNull
    private String end(String value) {
        return value.substring(value.length() - 2);
    }

}
