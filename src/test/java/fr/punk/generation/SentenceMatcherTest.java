package fr.punk.generation;

import fr.punk.words.WordGrammar;
import fr.punk.words.WordTag;
import fr.punk.words.WordType;
import fr.punk.words.property.Gender;
import fr.punk.words.property.Number;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.marpunk.core.Sentence;
import org.marpunk.core.word.SimpleWord;
import org.marpunk.core.word.Word;
import org.marpunk.core.word.Words;

import java.util.Arrays;
import java.util.List;

import static fr.punk.words.WordTag.*;

/**
 *
 */
public class SentenceMatcherTest {

    private SentenceMatcher matcher = new SentenceMatcher();


    @Test
    public void should_not_match_when_not_rhyms(){
        Sentence s1 = new Sentence(Arrays.asList(SimpleWord.from("coucou"),SimpleWord.from("les"),SimpleWord.from("gens")));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("HA"),SimpleWord.from("HA"),SimpleWord.from("HI"),SimpleWord.from("HO"),SimpleWord.from("HU"),SimpleWord.from("HOU")));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void should_match_when_rhyms(){
        Sentence s1 = new Sentence(Arrays.asList(SimpleWord.from("coucou"),SimpleWord.from("les"),SimpleWord.from("gens")));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("Quand"),SimpleWord.from("tu"),SimpleWord.from("mens")));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void should_not_match_when_same_end_word(){
        Sentence s1 = new Sentence(Arrays.asList(SimpleWord.from("coucou"),SimpleWord.from("les"),SimpleWord.from("gens")));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("Quand"),SimpleWord.from("les"),SimpleWord.from("gens")));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void should_not_match_when_same_end_word_until_is_small(){
        Sentence s1 = new Sentence(Arrays.asList(SimpleWord.from("la"),SimpleWord.from("voix"),SimpleWord.from("de"),SimpleWord.from("leurs"),SimpleWord.from("nez")));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("Du"),SimpleWord.from("sang"),SimpleWord.from("de"),SimpleWord.from("ton"),SimpleWord.from("nez")));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void with_end_word(){
        Sentence s1 = new Sentence(Arrays.asList(SimpleWord.from("coucou"), Words.END));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("Quand"),Words.END));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void should_reject_too_small_sentence(){
        Sentence s1 = new Sentence(Arrays.asList(SimpleWord.from("coucou"),SimpleWord.from("jeunes"), SimpleWord.from("gens")));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("3")));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void should_not_match_ponctuation(){
        Sentence s1 = new Sentence(Arrays.asList(word(ADJECTIVE, "Inférieur"),word(MARKER,","), word(DETERMINER,"le"),word(VERB_PAST_PARTICIPE,"passé"),word(VERB_ETRE,"est"),word(VERB_PAST_PARTICIPE,"terminé"),word(MARKER,",")));
        Sentence s2 = new Sentence(Arrays.asList(SimpleWord.from("Mère"),SimpleWord.from("chose"), SimpleWord.from(","),SimpleWord.from("aie"),word(MARKER,","),word(CONJONCTION,"que"),word(MARKER,",")));
        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @NotNull
    private WordGrammar word(WordTag type, String value) {
        return new WordGrammar(WordType.WordTypeBuilder.from(type).build(), value);
    }

    @Test
    public void should_not_match_ponctuation_when_use_grammar_word(){
        /**
            Sentence{words=[
                WordGrammar{type=WordType{type=PREPOSITION, firstWord}, value='en'},
                WordGrammar{type=WordType{type=ADJECTIVE, gender=FEMININE, number=SINGULAR}, value='grande'},
                WordGrammar{type=WordType{type=NOUN, gender=FEMININE, number=SINGULAR}, value='assemblée'},
                WordGrammar{type=WordType{type=PRONOUN}, value='se'},
                Word{value='trouve-t-il'},
                WordGrammar{type=WordType{type=MARKER}, value='?'}]
         }
         *
         */
        List<Word> words = Arrays.asList(
                new WordGrammar(WordType.WordTypeBuilder.from(PREPOSITION).isFirstWord().build(),"en"),
                new WordGrammar(WordType.WordTypeBuilder.from(ADJECTIVE).with(Gender.FEMININE).with(Number.SINGULAR).build(),"grande"),
                new WordGrammar(WordType.WordTypeBuilder.from(NOUN).with(Gender.FEMININE).with(Number.SINGULAR).build(),"assemblée"),
                new WordGrammar(WordType.WordTypeBuilder.from(PRONOUN).build(),"se"),
                SimpleWord.from("trouve-t-il"),
                new WordGrammar(WordType.WordTypeBuilder.from(MARKER).build(),"?")
                );
        Sentence s1 = new Sentence(words);

        /**
         * Sentence{words=[
         *      WordGrammar{type=WordType{type=ADJECTIVE, gender=FEMININE, number=SINGULAR, firstWord}, value='hideuse'},
         *      WordGrammar{type=WordType{type=MARKER}, value=','},
         *      WordGrammar{type=WordType{type=ADJECTIVE, gender=MASCULINE, number=SINGULAR}, value='dépendant'},
         *      WordGrammar{type=WordType{type=PREPOSITION}, value='dans'},
         *      Word{value='l'humanité'},
         *      WordGrammar{type=WordType{type=MARKER}, value='?'}]}
         */
        List<Word> words2 = Arrays.asList(
                new WordGrammar(WordType.WordTypeBuilder.from(ADJECTIVE).with(Gender.FEMININE).with(Number.SINGULAR).isFirstWord().build(),"hideuse"),
                new WordGrammar(WordType.WordTypeBuilder.from(MARKER).build(),","),
                new WordGrammar(WordType.WordTypeBuilder.from(ADJECTIVE).with(Gender.MASCULINE).with(Number.SINGULAR).build(),"dépendant"),
                new WordGrammar(WordType.WordTypeBuilder.from(PREPOSITION).build(),"dans"),
                SimpleWord.from("l'humanité"),
                new WordGrammar(WordType.WordTypeBuilder.from(MARKER).build(),"?")
                );
        Sentence s2 = new Sentence(words2);

        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void should_deal_with_small_end_word(){
        List<Word> words = Arrays.asList(
                new WordGrammar(WordType.WordTypeBuilder.from(PREPOSITION).isFirstWord().build(),"en"),
                new WordGrammar(WordType.WordTypeBuilder.from(ADJECTIVE).with(Gender.FEMININE).with(Number.SINGULAR).build(),"grande"),
                new WordGrammar(WordType.WordTypeBuilder.from(NOUN).with(Gender.FEMININE).with(Number.SINGULAR).build(),"assemblée"),
                new WordGrammar(WordType.WordTypeBuilder.from(PRONOUN).build(),"sans"),
                SimpleWord.from("thé"),
                new WordGrammar(WordType.WordTypeBuilder.from(MARKER).build(),"?")
        );
        Sentence s1 = new Sentence(words);

        List<Word> words2 = Arrays.asList(
                new WordGrammar(WordType.WordTypeBuilder.from(ADJECTIVE).with(Gender.FEMININE).with(Number.SINGULAR).isFirstWord().build(),"hideuse"),
                new WordGrammar(WordType.WordTypeBuilder.from(MARKER).build(),","),
                new WordGrammar(WordType.WordTypeBuilder.from(ADJECTIVE).with(Gender.MASCULINE).with(Number.SINGULAR).build(),"dépendant"),
                new WordGrammar(WordType.WordTypeBuilder.from(PREPOSITION).build(),"du"),
                SimpleWord.from("pi"),
                new WordGrammar(WordType.WordTypeBuilder.from(MARKER).build(),"?")
        );
        Sentence s2 = new Sentence(words2);

        boolean result = matcher.match(s1, s2);
        Assertions.assertThat(result).isFalse();
    }

}