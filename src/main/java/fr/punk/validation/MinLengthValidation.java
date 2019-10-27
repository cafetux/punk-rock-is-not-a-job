package fr.punk.validation;

import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MinLengthValidation implements SentenceValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinLengthValidation.class);

    private final int minLength;

    public MinLengthValidation(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean isValid(Sentence sentence) {
        boolean isValid = sentence.size() >= minLength;
        if(!isValid){
            LOGGER.warn("reject {}",sentence);
        }
        return isValid;
    }
}
