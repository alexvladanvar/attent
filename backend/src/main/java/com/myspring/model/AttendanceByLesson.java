package com.myspring.model;

public class AttendanceByLesson {
    private Boolean attended;
    private int lessonId;
    private String lessonName;
    private String studentName;
    private String groupName;

    public AttendanceByLesson() {
    }

    public AttendanceByLesson(Boolean attended, int lessonId, String lessonName, String studentName, String groupName) {
        this.attended = attended;
        this.lessonId = lessonId;
        this.lessonName = lessonName;
        this.studentName = studentName;
        this.groupName = groupName;
    }

    public Boolean getAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
