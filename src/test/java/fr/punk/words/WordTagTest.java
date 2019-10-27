package fr.punk.words;

import fr.punk.words.property.Gender;
import fr.punk.words.property.Number;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class WordTagTest {


    @Test
    public void should_convert_analysis_result(){

        //<S> à[à/P] ronger[ronger/V inf] des[du/D e p] bouts[bout/N m p] de[de/P] corde[corde/N f s,</S><P/>]
        assertThat(WordTag.from("P")).as("P").isPresent().matches(w->w.get().getType().equals(WordTag.PREPOSITION));

        assertThat(WordTag.from("V inf")).as("V inf").isPresent().matches(w->w.get().getType().equals(WordTag.VERB_INF));

        assertThat(WordTag.from("D e p")).as("D e p").isPresent()
                .matches(w->w.get().getType().equals(WordTag.DETERMINER))
                .matches(w->w.get().getGender().equals(Gender.EPICENE))
                .matches(w->w.get().getNumber().equals(Number.PLURAL));

        assertThat(WordTag.from("N m p")).isPresent()
                .matches(w->w.get().getType().equals(WordTag.NOUN))
                .matches(w->w.get().getGender().equals(Gender.MASCULINE))
                .matches(w->w.get().getNumber().equals(Number.PLURAL));

        assertThat(WordTag.from("P")).isPresent().matches(w->w.get().getType().equals(WordTag.PREPOSITION));

        assertThat(WordTag.from("N f s")).isPresent()
                .matches(w->w.get().getType().equals(WordTag.NOUN))
                .matches(w->w.get().getGender().equals(Gender.FEMININE))
                .matches(w->w.get().getNumber().equals(Number.SINGULAR));
    }

    @Test
    public void should_not_match_tags_too_small_when_match_other() {
        assertThat(WordTag.from("V inf"))
                .as("V inf should not match with V")
                .isPresent().matches(w->w.get().getType().equals(WordTag.VERB_INF));

    }
}