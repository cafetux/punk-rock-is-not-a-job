package fr.punk.validation;

import org.junit.Test;
import org.marpunk.core.Sentence;
import org.marpunk.core.word.SimpleWord;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class NaziModeratorTest {

    private NaziModerator moderator = new NaziModerator();

    @Test
    public void should_reject_sentence_with_nazi_word(){
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("nazi")))).isFalse();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("nAZi")))).isFalse();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("Reich")))).isFalse();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("nazisme")))).isFalse();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("gammée")))).isFalse();
    }

    @Test
    public void should_accept_sentence_without_nazi_word(){
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("Paul")))).isTrue();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("gamétogénèse")))).isTrue();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("HellO")))).isTrue();
        assertThat(moderator.isValid(new Sentence(SimpleWord.from("pouet")))).isTrue();
    }

}