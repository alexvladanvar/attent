package com.myspring.model;


public class TransitLesson {
    private int groupId;
    private String lessonName;

    public TransitLesson(int groupId, String lessonName) {
        this.groupId = groupId;
        this.lessonName = lessonName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}
