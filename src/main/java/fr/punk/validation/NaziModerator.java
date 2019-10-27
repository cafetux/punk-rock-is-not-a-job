package fr.punk.validation;

import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Générer des phrases aléatoires à partir de textes anti-fascistes,
 * peut amener à des phrases pronant exactement l'inverse.
 * Ce qui est inacceptable. Appliquons le principe de précaution.
 */
public class NaziModerator implements SentenceValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(NaziModerator.class);

    private final List<String> forbiddenWords = Arrays.asList("nazi","reich","hitler","nazisme","gammée","omarska");


    @Override
    public boolean isValid(Sentence sentence) {
        boolean isValid = forbiddenWords.stream().noneMatch(w -> sentence.format().toLowerCase().contains(w));
        if(!isValid){
            LOGGER.warn("reject {}",sentence);
        }
        return isValid;
    }
}
