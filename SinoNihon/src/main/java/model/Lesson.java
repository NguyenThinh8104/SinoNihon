package model;

public class Lesson {
    private int lessonID;
    private int courseID;
    private String title;

    public Lesson(int lessonID, int courseID, String title) {
        this.lessonID = lessonID;
        this.courseID = courseID;
        this.title = title;
    }

    public int getLessonID() { return lessonID; }
    public int getCourseID() { return courseID; }
    public String getTitle() { return title; }
}