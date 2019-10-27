package fr.punk.words.property;

public enum To {

    NONE("-"),
    SUBJECT("suj"),
    OBJECT("obj");
    
    private final String template;

    To(String template) {
        this.template = template;
    }


    public static To from(String detailValue) {
        for (To value : values()) {
            if(value.template.equals(detailValue)){
                return value;
            }
        }
        return NONE;
    }

}