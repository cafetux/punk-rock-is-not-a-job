package fr.punk.words;

import fr.punk.words.property.*;
import fr.punk.words.property.Number;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**

  TAGS FOR LANGUAGETOOL

  http://www.languagetool.org/
  http://community.languagetool.org/
  http://languagetool.wikidot.com/

 **/
public enum WordTag {


    /**
     * --  GRAMMATICAL WORDS  --
     */
    PREPOSITION("P"),
    CONJONCTION("C"),
    CONJONCTION_SUBORDINATING("C sub"),
    CONJONCTION_COORDINATING("C coor"),
    DETERMINER("D","[gender] [number]"),
    PRONOUN("R","[gender] [number]"),
    PRONOUN_RELATIVE("R rel","[gender] [number]"),
    PRONOUN_DEMONSTRATIVE("R dem","[gender] [number]"),
    PRONOUN_REFLEXIVE("R refl","[person] [gender] [number]"),
    PERSONNAL_PRONOUN("R pers","[to] [gender] [number]"),
    /**
        --  NOUNS AND ADJECTIVES  --
    **/
    NOUN("N","[gender] [number]"),
    ADJECTIVE("J","[gender] [number]"),
    /**
     * --  MISC  --
     */
    ADVERB("A"),
    INTERJECTION("I"),
    ONOMATOPEIA("O"),
    CARDINAL_NUMBER("K"),
    ABBREVIATION("S"),
    PROPER_NAME("Z"),
    MARKER("M"),
    /**
     * --  VERBS  --
     */
    VERB_INF("V inf"),
    VERB_PRESENT_PARTICIPE("V ppr"),
    VERB_PAST_PARTICIPE("V ppa","[gender] [number]"),
    VERB_AVOIR("V avoir"),
    VERB_ETRE("V etre"),
    VERB_CONJUGATION("V", "[mood] [tense] [person] [number]");

    private static final Logger LOGGER = LoggerFactory.getLogger(WordTag.class);

    private final String template;
    private final String details;

    WordTag(String template) {
        this(template,"");
    }

    WordTag(String template, String details) {
        this.template = template;
        this.details=details;
    }

    public static Optional<WordType> from(String posTag) {
        if(posTag==null || posTag.isEmpty()){
            return Optional.empty();
        }
        for (WordTag tag : values()) {
            if(posTag.startsWith(tag.template)) {
                WordType.WordTypeBuilder from = WordType.WordTypeBuilder.from(tag);
                if(tag.details.equals("")) {
                    return Optional.of(from.build());
                }
                String[] posTagParts = posTag.split(" ");
                String[] detailsTemplate = tag.details.split(" ");
                for (int i = 0; i < detailsTemplate.length; i++) {

                    String detailTemplate = detailsTemplate[i];
                    String detailValue;
                    try {
                        detailValue = posTagParts[i+1];
                    } catch (Exception e) {
                        LOGGER.error("invalid grammar tag "+posTag,e);
                        LOGGER.error("invalid match "+ Arrays.toString(detailsTemplate));
                        LOGGER.error("invalid match "+ Arrays.toString(posTagParts));
                        return Optional.of(from.build());
                    }
                    if(detailTemplate.equals("[gender]")) {
                        from = from.with(Gender.from(detailValue));
                    }
                    if(detailTemplate.equals("[mood]")) {
                        from = from.with(Mood.from(detailValue));
                    }
                    if(detailTemplate.equals("[to]")) {
                        from = from.with(To.from(detailValue));
                    }
                    if(detailTemplate.equals("[number]")) {
                        from = from.with(Number.from(detailValue));
                    }
                    if(detailTemplate.equals("[tense]")) {
                        from = from.with(Tense.from(detailValue));
                    }
                    if(detailTemplate.equals("[person]")) {
                        from = from.with(Person.from(detailValue));
                    }
                }
                return Optional.of(from.build());
            }        }
        return Optional.empty();
    }
}



