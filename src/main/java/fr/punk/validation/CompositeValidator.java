package fr.punk.validation;

import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CompositeValidator implements SentenceValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(CompositeValidator.class);

    private List<SentenceValidator> validators = new ArrayList<>();

    @Override
    public boolean isValid(Sentence sentence) {
        LOGGER.info("validation for sentence:"+sentence.format());
        return validators.stream().allMatch(x->x.isValid(sentence));
    }

    public CompositeValidator register(SentenceValidator validator) {
        validators.add(validator);
        return this;
    }
}
