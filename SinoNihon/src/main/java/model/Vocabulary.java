package model;

public class Vocabulary {
    private int vocabID;
    private String word;
    private String meaning;
    private String pronunciation;

    public Vocabulary(int vocabID, String word, String meaning, String pronunciation) {
        this.vocabID = vocabID;
        this.word = word;
        this.meaning = meaning;
        this.pronunciation = pronunciation;
    }

    public int getVocabID() { return vocabID; }
    public String getWord() { return word; }
    public String getMeaning() { return meaning; }
    public String getPronunciation() { return pronunciation; }
}