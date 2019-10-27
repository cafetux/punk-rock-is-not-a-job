package fr.punk.words.property;

public enum Gender {

    NONE("-"),
    EPICENE("e"),
    FEMININE("f"),
    MASCULINE("m");

    private final String template;

    Gender(String template) {
        this.template = template;
    }

    public static Gender from(String detailValue) {
        for (Gender value : values()) {
            if(value.template.equals(detailValue)){
                return value;
            }
        }
        return NONE;
    }
}
