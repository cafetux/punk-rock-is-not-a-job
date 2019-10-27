package fr.punk.words;

import org.junit.Test;
import org.marpunk.core.word.Word;
import org.marpunk.infra.file.Splitter;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class GrammarSplitterTest {

    private Splitter splitter;

    @Test
    public void should_not_return_blanck_fields() {

        splitter = new GrammarSplitter();
        List<Word> sentence = splitter.split("bonjours à tous");
        assertThat(sentence).hasSize(3);
    }

    @Test
    public void should_keep_unknown_words() {

        splitter = new GrammarSplitter();
        List<Word> sentence = splitter.split("Ca tombait bien ma mine aussi");
        assertThat(sentence).hasSize(6);
    }

    @Test
    public void should_not_split_apostrophe() {

        splitter = new GrammarSplitter();
        List<Word> sentence = splitter.split("j'étais malade mamie aussi");
        assertThat(sentence).as("Should return [j'étais,malade,mamie,aussi]").hasSize(4);
    }

    @Test
    public void should_not_split_apostrophe_multiple_times_on() {

        splitter = new GrammarSplitter();
        List<Word> sentence = splitter.split("j'étais malade mamie aussi et c'était pas cool");
        assertThat(sentence).as("Should return [j'étais,malade,mamie,aussi,et,c'était,pas,cool]").hasSize(8);
    }

    @Test
    public void should_ignore_uknow_type_R_int() {

        splitter = new GrammarSplitter();
        List<Word> sentence = splitter.split("tu étais malade mamie aussi");
        assertThat(sentence).hasSize(5);
    }

    @Test
    public void should_have_different_key_when_first_word() {

        splitter = new GrammarSplitter();
        List<Word> sentence1 = splitter.split("toi aussi jeune mouton");
        List<Word> sentence2 = splitter.split("à toi aussi jeune mouton");
        assertThat(sentence1.get(1)).isEqualTo(sentence2.get(2));
        assertThat(sentence1.get(0)).isNotEqualTo(sentence2.get(1));
    }


}