package fr.punk.words.property;

public enum Tense {
    NONE("-"),
    PRESENT("pres"),
    SIMPLE_PAST("psim"),
    IMPARFAIT("impa"),
    FUTURE("futu");

    private final String template;

    Tense(String template) {
        this.template = template;
    }


    public static Tense from(String detailValue) {
        for (Tense value : values()) {
            if(value.template.equals(detailValue)){
                return value;
            }
        }
        return NONE;
    }


}
