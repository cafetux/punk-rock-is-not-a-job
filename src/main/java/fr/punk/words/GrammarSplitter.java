package fr.punk.words;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import fr.punk.AutoCorrector;
import fr.punk.validation.MinLengthValidation;
import org.apache.commons.lang3.StringUtils;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.language.French;
import org.marpunk.core.word.SimpleWord;
import org.marpunk.core.word.Word;
import org.marpunk.infra.file.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GrammarSplitter implements Splitter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarSplitter.class);
    private JLanguageTool langTool = new JLanguageTool(new French());
    private AutoCorrector corrector = new AutoCorrector();

    @Override
    public List<Word> split(String line) {
        List<Word> result = new ArrayList<>();
        try {

            List<AnalyzedSentence> analyze = langTool.analyzeText(line);
            boolean isFirst = true;

            for (AnalyzedSentence sentence : analyze) {
                for (AnalyzedTokenReadings token : sentence.getTokens()) {
                    if (StringUtils.isBlank(token.getToken())) {
                        continue;
                    }
                    Word word = null;
                    for (AnalyzedToken reading : token.getReadings()) {
                        if (Objects.equals(reading.getPOSTag(), "R inte")) {
                            continue;
                        }
                        Optional<WordType> tag = WordTag.from(reading.getPOSTag());
                        if (tag.isPresent()) {
                            WordType wordTag = tag.get();
                            if(isFirst) {
                                wordTag = wordTag.toFirstWord();
                            }
                            word = new WordGrammar(wordTag, token.getToken());
                            break;
                        }
                    }
                    if (word == null) {
                        word = SimpleWord.from(token.getToken());
                    }
                    if(!isFirst) {
                        Word previous = result.get(result.size() - 1);
                        if (previous.getValue().equals("'")) {
                            Word previousPrevious = result.get(result.size() - 2);
                            result.remove(result.size() - 1);
                            result.remove(result.size() - 1);
                            word = SimpleWord.from(previousPrevious.getValue().concat(previous.getValue()).concat(word.getValue()));
                        }
                    }
                    isFirst = false;
                    result.add(word);
                }
            }
        } catch (IOException e) {
            LOGGER.error("cannot parse "+line,e);
            Throwables.propagateIfPossible(e, IllegalStateException.class);
        }
        return result;
    }

}
