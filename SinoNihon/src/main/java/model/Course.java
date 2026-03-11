package model;

public class Course {
    private int courseID;
    private String title;
    private String language;
    private String description;

    public Course(int courseID, String title, String language, String description) {
        this.courseID = courseID;
        this.title = title;
        this.language = language;
        this.description = description;
    }

    public int getCourseID() { return courseID; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public String getDescription() { return description; }
}