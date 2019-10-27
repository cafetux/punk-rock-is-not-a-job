package fr.punk.validation;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.marpunk.core.Sentence;
import org.marpunk.core.word.SimpleWord;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class GrammarModeratorTest {

    private GrammarModerator moderator = new GrammarModerator();

    @Test
    public void should_reject_when_too_many_rules_brokes() {
        assertThat(moderator.isValid(sentence("tu courrir en look coco en prévoirisionner"))).isFalse();
    }

    @Test
    public void should_not_reject_when_have_some_punk_woot() {
        assertThat(moderator.isValid(sentence("Hey, les moutons affamés, oué oué"))).isTrue();
    }

    @NotNull
    private Sentence sentence(String sentence) {
        return new Sentence(Arrays.stream(sentence.split(" ")).map(SimpleWord::from).collect(Collectors.toList()));
    }
}