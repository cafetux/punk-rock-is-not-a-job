package fr.punk;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.punk.generation.ContentGenerator;
import fr.punk.generation.Lyrics;
import fr.punk.validation.*;
import fr.punk.words.GrammarSplitter;
import fr.punk.words.MemoryWords;
import org.junit.Test;
import org.marpunk.core.Sentence;
import org.marpunk.core.SentenceGenerator;
import org.marpunk.core.word.Words;
import org.marpunk.infra.file.SentenceFilesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class TestAsMain {


    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final AutoCorrector autocorrect = new AutoCorrector();

    @Test
    public void should_generate_lyrics() throws Exception {

        Words words = new MemoryWords();
        load("files/songs/les rats").forEach(words::save);
        load("files/songs/oth").forEach(words::save);
        load("files/songs/rap").forEach(words::save);
        load("files/songs/zabriskie point").forEach(words::save);
        load("files/songs/charly fiasco").forEach(words::save);
        load("files/songs/gxp").forEach(words::save);
        load("files/songs/justine").forEach(words::save);
        load("files/texts").forEach(words::save);


        SentenceValidator validator = new CompositeValidator()
                .register(new MinLengthValidation(5))
                .register(new MaxLengthValidation(7))
                .register(new NaziModerator())
                .register(new GrammarModerator());
        ContentGenerator generator = new ContentGenerator(new SentenceGenerator(words), validator);

        Optional<Lyrics> output = generator.generate();
        if(output.isPresent()) {
            String corrected1 = autocorrect.correct(output.get().get(0).format());
            String corrected2 = autocorrect.correct(output.get().get(1).format());

            LOGGER.info("--------------------------------------------");
            LOGGER.info("{}", corrected1);
            LOGGER.info("{}", corrected2);
            LOGGER.info("--------------------------------------------");
        }
    }

    private List<Sentence> load(String s) throws URISyntaxException {
        Path existingFiles = Paths.get(ClassLoader.getSystemResource(s).toURI());
        SentenceFilesLoader loader = new SentenceFilesLoader(existingFiles, new GrammarSplitter());
        return loader.load();
    }

}
