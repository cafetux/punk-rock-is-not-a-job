package fr.punk;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class AutoCorrectorTest {

    private final AutoCorrector corrector = new AutoCorrector();

    @Test
    public void should_not_use_old_index_after_first_correction() {
        assertThat(corrector.correct("avec les rats qui voit rien..")).isEqualTo("Avec les rats qui voient rien.");
    }

    @Test
    public void should_not_not_replace_english_word_by_french() {
        assertThat(corrector.correct("assassine le look tour de réponses répressives ?")).contains("look");
    }


}