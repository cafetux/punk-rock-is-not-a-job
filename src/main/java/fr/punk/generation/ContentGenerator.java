package fr.punk.generation;

import fr.punk.validation.SentenceValidator;
import org.marpunk.core.Sentence;
import org.marpunk.core.SentenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 *
 */
public class ContentGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentGenerator.class);

    private SentenceGenerator sentences;
    private SentenceMatcher sentenceMatcher = new SentenceMatcher();
    private SentenceValidator validator;
    public ContentGenerator(SentenceGenerator sentences, SentenceValidator validator) {
        this.sentences = sentences;
        this.validator = validator;
    }

    public Optional<Lyrics> generate(){
        try {
            Sentence first = generateSentence();
            LOGGER.info("Generate sentence 1: {}", first);
            Sentence second = findSecondSentenceCompatibleWith(first);
            LOGGER.info("Generate sentence 2: {}", second);
            return Optional.of(new Lyrics(first, second));
        } catch(Exception e) {
            return Optional.empty();
        }
    }

    private Sentence findSecondSentenceCompatibleWith(Sentence first) {
        for(int i = 0; i< 10000; i++) {
            Sentence sentenceCandidat = generateSentence();
            LOGGER.debug("candidat "+i+":{}", sentenceCandidat.format());
                if(sentenceMatcher.match(first, sentenceCandidat))
                {
                    return sentenceCandidat;
                }
        }
        throw new IllegalStateException("Cannot find a compatible sentence to '"+first.format()+"' after 10000 attempts: check configuration");
    }

    private Sentence generateSentence() {
        for(int i = 0; i< 10000; i++) {
            Sentence sentence = sentences.generateSentence();
            if (validator.isValid(sentence)) {
                    return sentence;
            }
        }
        throw new IllegalStateException("Cannot find a valid sentence after 10000 attempts: check configuration");
    }


}
