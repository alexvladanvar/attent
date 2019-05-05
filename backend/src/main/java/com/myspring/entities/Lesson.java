package com.myspring.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lessons_test")
public class Lesson {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lessonId;


    @Expose
    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany (mappedBy="lesson", fetch=FetchType.EAGER)
    private List<Attendance> attendances;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_test_id")
    @Expose
    Subject subject;

    @JoinColumn(name = "date")
    private Date date;

    public Lesson() {
    }

    public Lesson(Teacher teacher, Group group, List<Attendance> attendances) {
        this.teacher = teacher;
        this.group = group;
        this.attendances = attendances;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Group getGroups() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public Group getGroup() {
        return group;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
