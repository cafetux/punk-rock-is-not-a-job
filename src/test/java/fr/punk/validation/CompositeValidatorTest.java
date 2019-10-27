package fr.punk.validation;

import org.junit.Test;
import org.marpunk.core.Sentence;
import org.marpunk.core.word.SimpleWord;
import org.marpunk.core.word.Word;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class CompositeValidatorTest {


    public static final SentenceValidator ALWAYS_FALSE = sentence -> false;
    public static final SentenceValidator ALWAYS_TRUE = sentence -> true;

    @Test
    public void should_not_valid_if_only_false_validators() {
        CompositeValidator validator = new CompositeValidator().register(ALWAYS_FALSE).register(ALWAYS_FALSE);
        assertThat(validator.isValid(anySentence())).isFalse();
    }

    @Test
    public void should_be_valid_if_only_true_validators() {
        CompositeValidator validator = new CompositeValidator().register(ALWAYS_TRUE).register(ALWAYS_TRUE);
        assertThat(validator.isValid(anySentence())).isTrue();
    }

    @Test
    public void should_be_valid_if_no_validators() {
        CompositeValidator validator = new CompositeValidator();
        assertThat(validator.isValid(anySentence())).isTrue();
    }

    @Test
    public void should_not_be_valid_if_not_match_one_validator() {
        CompositeValidator validator = new CompositeValidator().register(ALWAYS_TRUE).register(ALWAYS_FALSE).register(ALWAYS_TRUE);
        assertThat(validator.isValid(anySentence())).isFalse();
    }

    private Sentence anySentence() {
        return new Sentence(SimpleWord.from("truc"));
    }
}