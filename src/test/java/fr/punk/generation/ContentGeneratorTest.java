package fr.punk.generation;

import fr.punk.validation.CompositeValidator;
import fr.punk.validation.MaxLengthValidation;
import fr.punk.validation.MinLengthValidation;
import org.junit.Before;
import org.junit.Test;
import org.marpunk.core.Sentence;
import org.marpunk.core.SentenceGenerator;
import org.marpunk.core.word.SimpleWord;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 *
 */
public class ContentGeneratorTest {

    private SentenceGenerator sentencesMock = Mockito.mock(SentenceGenerator.class);
    private ContentGenerator sut;

    @Before
    public void init() {
        sut = new ContentGenerator(sentencesMock, new CompositeValidator());
    }

    @Test
    public void should_generate_2_sentences() {
        when(sentencesMock.generateSentence()).thenReturn(
                sentence("Viens donc avec ton tamis"),
                sentence("je serais avec ton amis"));
        Lyrics result = sut.generate();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    public void should_have_a_minimum_size() {
        CompositeValidator validator = new CompositeValidator();
        validator.register(new MinLengthValidation(4));
        sut = new ContentGenerator(sentencesMock, validator);

        when(sentencesMock.generateSentence()).thenReturn(
                sentence("Hey toi !"),
                sentence("Viens donc avec ton tamis"),
                sentence("Encore là ?"),
                sentence("je serais avec ton amis"));
        Lyrics result = sut.generate();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).format()).isEqualTo("Viens donc avec ton tamis");
        assertThat(result.get(1).format()).isEqualTo("je serais avec ton amis");
    }

    @Test
    public void should_have_a_maximum_size() {
        CompositeValidator validator = new CompositeValidator();
        validator.register(new MaxLengthValidation(6));
        sut = new ContentGenerator(sentencesMock, validator);

        when(sentencesMock.generateSentence()).thenReturn(
                sentence("Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !Hey toi !"),
                sentence("Viens donc avec ton tamis"),
                sentence("Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?"),
                sentence("je serais avec ton amis"));
        Lyrics result = sut.generate();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).format()).isEqualTo("Viens donc avec ton tamis");
        assertThat(result.get(1).format()).isEqualTo("je serais avec ton amis");
    }
    @Test
    public void should_rhyme() {

        when(sentencesMock.generateSentence()).thenReturn(
                sentence("Viens donc avec ton tamis"),
                sentence("Viens donc avec ton panier"),
                sentence("Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?"),
                sentence("je serais avec ton amis"));
        Lyrics result = sut.generate();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).format()).isEqualTo("Viens donc avec ton tamis");
        assertThat(result.get(1).format()).isEqualTo("je serais avec ton amis");
    }
    @Test
    public void should_have_sentences_of_same_size() {

        when(sentencesMock.generateSentence()).thenReturn(
                sentence("Viens donc avec ton tamis"),
                sentence("Viens donc avec ton panier"),
                sentence("Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?Encore là ?"),
                sentence("je serais sans doute ton amis"),
                sentence("on serait sans doute amis"));
        Lyrics result = sut.generate();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).format()).isEqualTo("Viens donc avec ton tamis");
        assertThat(result.get(1).format()).isEqualTo("on serait sans doute amis");
    }

    @Test
    public void should_not_have_same_end_word() {
        when(sentencesMock.generateSentence()).thenReturn(
                sentence("Viens donc avec ton amis"),
                sentence("on serait sans doute amis"),
                sentence("je serais alors sans abris"));
        Lyrics result = sut.generate();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).format()).isEqualTo("Viens donc avec ton amis");
        assertThat(result.get(1).format()).isEqualTo("je serais alors sans abris");
    }

    @Test
    public void should_not_have_infinite_loop_for_first_sentence() {
        CompositeValidator validator = new CompositeValidator();
        validator.register(new MinLengthValidation(6));
        sut = new ContentGenerator(sentencesMock, validator);

        when(sentencesMock.generateSentence()).thenReturn(sentence("ha"));
        try {
            sut.generate();
            fail("should throw exception");
        } catch(IllegalStateException e) {
            assertThat(e).hasMessageContaining("Cannot find a valid sentence after");
        }
    }

    @Test
    public void should_not_have_infinite_loop_for_second_sentence() {
        when(sentencesMock.generateSentence()).thenReturn(sentence("Viens donc avec ton amis"),sentence("ha"));
        try {
            sut.generate();
            fail("should throw exception");
        } catch(IllegalStateException e) {
            assertThat(e).hasMessageContaining("Cannot find a compatible sentence");
        }
    }

    private Sentence sentence(String sentence) {
        return new Sentence(Arrays.stream(sentence.split(" ")).map(SimpleWord::from).collect(Collectors.toList()));
    }

}