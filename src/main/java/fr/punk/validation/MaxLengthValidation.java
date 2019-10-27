package fr.punk.validation;

import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class MaxLengthValidation implements SentenceValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaxLengthValidation.class);

    private final int maxLength;

    public MaxLengthValidation(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(Sentence sentence) {
        boolean isValid = sentence.size() <= maxLength;
        if(!isValid){
            LOGGER.warn("reject {}",sentence);
        }
        return isValid;
    }
}
