package fr.punk.words;

import fr.punk.words.property.*;
import fr.punk.words.property.Number;

import java.io.Serializable;
import java.util.Objects;

public class WordType implements Serializable {

    private final WordTag type;
    private final Gender gender;
    private final Number number;
    private final Person person;
    private final Mood mood;
    private final Tense tense;
    private final To to;
    private final boolean firstWord;

    private WordType(WordTag type, Gender gender, Number number, Person person, Mood mood, Tense tense, To to, boolean firstWord) {
        this.type = type;
        this.gender = gender;
        this.number = number;
        this.person = person;
        this.mood = mood;
        this.tense = tense;
        this.to = to;
        this.firstWord = firstWord;
    }

    public WordTag getType() {
        return type;
    }

    public Gender getGender() {
        return gender;
    }

    public Number getNumber() {
        return number;
    }

    public Person getPerson() {
        return person;
    }

    public Mood getMood() {
        return mood;
    }

    public Tense getTense() {
        return tense;
    }

    public To getTo() {
        return to;
    }

    public boolean isFirstWord() {
        return firstWord;
    }

    public WordType toFirstWord() {
        WordType word = new WordType(type,gender,number,person,mood,tense,to,true);
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordType wordType = (WordType) o;
        return firstWord == wordType.firstWord &&
                type == wordType.type &&
                gender == wordType.gender &&
                number == wordType.number &&
                person == wordType.person &&
                mood == wordType.mood &&
                tense == wordType.tense &&
                to == wordType.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, gender, number, person, mood, tense, to, firstWord);
    }

    @Override
    public String toString() {
        return "WordType{" +
                "type=" + type +
                (gender==Gender.NONE?"":", gender=" + gender) +
                (number==Number.NONE?"":", number=" + number) +
                (person==Person.NONE?"":", person=" + person) +
                (mood==Mood.NONE?"":", mood=" + mood) +
                (tense==Tense.NONE?"":", tense=" + tense) +
                (to==To.NONE?"":", to=" + to) +
                (firstWord ? ", firstWord" : "")+
                '}';
    }

    public static class WordTypeBuilder{

        private WordTag type;
        private Gender gender = Gender.NONE;
        private Number number = Number.NONE ;
        private Person person = Person.NONE;
        private Mood mood = Mood.NONE;
        private Tense tense = Tense.NONE;
        private To to = To.NONE;
        private boolean first = false;

        private WordTypeBuilder(WordTag type) {
            this.type = type;
        }

        public static WordTypeBuilder from(WordTag type) {
            return new WordTypeBuilder(type);
        }

        public WordTypeBuilder with(Gender gender) {
            this.gender = gender;
            return this;
        }

        public WordTypeBuilder with(Person person) {
            this.person = person;
            return this;
        }

        public WordTypeBuilder with(Number number) {
            this.number = number;
            return this;
        }

        public WordTypeBuilder with(Tense tense) {
            this.tense = tense;
            return this;
        }

        public WordTypeBuilder with(To to) {
            this.to = to;
            return this;
        }

        public WordTypeBuilder with(Mood mood) {
            this.mood = mood;
            return this;
        }
        public WordTypeBuilder isFirstWord() {
            this.first = true;
            return this;
        }

        public WordType build() {
            return new WordType(type,gender,number,person,mood,tense,to, first);
        }
    }

}
