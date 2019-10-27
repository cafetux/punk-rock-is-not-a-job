package fr.punk.words.property;

public enum Mood {
    NONE("-"),
    INDICATIVE("ind"),
    CONDITIONAL("con"),
    SUBJONCTIVE("sub"),
    IMPERATIVE("imp");

    private final String template;

    Mood(String template) {
        this.template = template;
    }


    public static Mood from(String detailValue) {
        for (Mood value : values()) {
            if(value.template.equals(detailValue)){
                return value;
            }
        }
        return NONE;
    }

}