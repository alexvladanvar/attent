package com.myspring.model;




import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CreateLessonRequest {
    private int subjectId;
    private int groupId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date date;


    public CreateLessonRequest() {
    }

    public CreateLessonRequest(int subjectId, int groupId, Date date) {
        this.subjectId = subjectId;
        this.groupId = groupId;
        this.date = date;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
