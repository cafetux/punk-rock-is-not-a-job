package fr.punk;

import org.languagetool.JLanguageTool;
import org.languagetool.language.French;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.SuggestedReplacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class AutoCorrector {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoCorrector.class);
    private JLanguageTool langTool = new JLanguageTool(new French());


    public String correct(String sentence) {
        String result = sentence;
        try {

            List<RuleMatch> matches = langTool.check(sentence);

            matches.sort((o1,o2)-> Integer.compare(o2.getFromPos(),o1.getFromPos()));

            for (RuleMatch match : matches) {
                if(match.toString().contains("anglicisme")){
                    continue;
                }
                if(match.toString().contains("Faute de frappe possible trouvée")){
                    continue;
                }
                int start = match.getFromPos();
                int end = match.getToPos();
                if(!match.getSuggestedReplacementObjects().isEmpty()) {
                    SuggestedReplacement replacement = match.getSuggestedReplacementObjects().get(0);
                    result = result.substring(0, start) + replacement.getReplacement() + result.substring(end);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();

        }

        LOGGER.info("sentence '"+sentence+"' is corrected to '"+result+"'");
        return result;
    }
}