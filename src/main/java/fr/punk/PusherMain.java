package fr.punk;

import fr.punk.generation.ContentGenerator;
import fr.punk.generation.Lyrics;
import fr.punk.validation.*;
import fr.punk.words.GrammarSplitter;
import org.jetbrains.annotations.NotNull;
import org.marpunk.core.Sentence;
import org.marpunk.core.SentenceGenerator;
import org.marpunk.core.word.Words;
import org.marpunk.infra.file.SentenceFilesLoader;
import org.marpunk.infra.word.InMemoryWords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final SentencesSerializer datas = new SentencesSerializer();
    private static final AutoCorrector autocorrect = new AutoCorrector();

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Twitter twitter = TwitterFactory.getSingleton();

    public static void main (String[] args) throws Exception {
        LOGGER.info("Start");

        Words words = buildTree();

        SentenceValidator validator = new CompositeValidator()
                .register(new MinLengthValidation(5))
                .register(new MaxLengthValidation(7))
                .register(new NaziModerator())
                .register(new GrammarModerator());
        ContentGenerator generator = new ContentGenerator(new SentenceGenerator(words), validator);


        scheduler.scheduleAtFixedRate(() -> {
            Lyrics output = generator.generate();
            String corrected1 = autocorrect.correct(output.get(0).format());
            String corrected2 = autocorrect.correct(output.get(1).format());
            post(corrected1,corrected2);
        }, 0, 4, TimeUnit.HOURS);

        LOGGER.info("End");

    }


    private static void post(String line1, String line2) {
        try {
            twitter.updateStatus(line1+"\n"+line2);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private static List<Sentence> load(String s) throws Exception {
        Path existingFiles = Paths.get(ClassLoader.getSystemResource(s).toURI());
        SentenceFilesLoader loader = new SentenceFilesLoader(existingFiles, new GrammarSplitter());
        return loader.load();
    }

    @NotNull
    private static Words buildTree() throws Exception {
        List<Sentence> sentences = datas.load();
        Words words = new InMemoryWords();
        if(sentences.isEmpty()) {
            LOGGER.warn("no existing data, load files");
            load("files/songs/les rats").forEach(words::save);
            load("files/songs/charly fiasco").forEach(words::save);
            load("files/songs/rap").forEach(words::save);
            load("files/songs/oth").forEach(words::save);
            load("files/songs/gxp").forEach(words::save);
            load("files/songs/justine").forEach(words::save);
            load("files/texts").forEach(words::save);
//            datas.save(sentences);
        }
        LOGGER.info("load done");
        sentences.forEach(words::save);
        LOGGER.info("tree done");
        return words;
    }


}

