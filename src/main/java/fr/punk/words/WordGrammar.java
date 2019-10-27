package fr.punk.words;

import org.marpunk.core.word.Word;

import java.util.Objects;

public class WordGrammar implements Word {

    private final WordType type;
    private final String value;

    public WordGrammar(WordType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String getKey() {
        return type.toString()+"%"+value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "WordGrammar{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordGrammar that = (WordGrammar) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
