package fr.punk.words.property;

public enum Number {
    NONE("-"),
    SINGULAR("s"),
    PLURAL("p"),
    SINGULAR_OR_PLURAL("sp");

    private final String template;

    Number(String template) {
        this.template = template;
    }

    public static Number from(String detailValue) {
        for (Number value : values()) {
            if(value.template.equals(detailValue)){
                return value;
            }
        }
        return NONE;
    }

}