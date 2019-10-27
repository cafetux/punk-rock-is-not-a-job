package fr.punk.validation;

import org.languagetool.JLanguageTool;
import org.languagetool.language.French;
import org.languagetool.rules.RuleMatch;
import org.marpunk.core.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class GrammarModerator implements SentenceValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrammarModerator.class);
    private JLanguageTool langTool = new JLanguageTool(new French());


    @Override
    public boolean isValid(Sentence sentence) {
        try {

            List<RuleMatch> matches = langTool.check(sentence.format());
            if(matches.size()>3){
                LOGGER.warn("rejected: too many errors ("+matches.size()+")");
                return false;
            }
            for (RuleMatch match : matches) {
                LOGGER.debug("Potential error at characters " +
                        match.getFromPos() + "-" + match.getToPos() + ": " +
                        match.getMessage());
                LOGGER.debug("Suggested correction(s): " +
                        match.getSuggestedReplacements());

                switch(match.getMessage()) {
                    case "n’est pas un verbe":
                    case "Vérifiez l’accord":
                    case "Voulez-vous dire":
                    case "s’élide en":
                    case "devrait être à l’impératif":
                        LOGGER.warn("reject "+sentence+" cause: "+match.getMessage());
                        return false;
                    default:
                }

            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}