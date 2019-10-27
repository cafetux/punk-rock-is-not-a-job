package fr.punk.words.property;

public enum Person {
    NONE("-"),
    FIRST("1"),
    SECOND("2"),
    THIRD("3");

    private final String template;

    Person(String template) {
        this.template = template;
    }

    public static Person from(String detailValue) {
        for (Person value : values()) {
            if(value.template.equals(detailValue)){
                return value;
            }
        }
        return NONE;
    }

}
