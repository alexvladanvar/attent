package com.myspring.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons_test")
public class LessonsTest {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lessonId;

    @Expose
    @Column(name = "name")
    private String name;

    @Expose
    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    private TeachersTest teacher;

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupsTest group;

    @OneToMany (mappedBy="lesson", fetch=FetchType.EAGER)
    private List<AttendanceTest> attendances;

    public LessonsTest() {
    }

    public LessonsTest(String name, TeachersTest teacher, GroupsTest group, List<AttendanceTest> attendances) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeachersTest getTeacher() {
        return teacher;
    }

    public void setTeacher(TeachersTest teacher) {
        this.teacher = teacher;
    }

    public GroupsTest getGroups() {
        return group;
    }

    public void setGroup(GroupsTest group) {
        this.group = group;
    }

    public List<AttendanceTest> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<AttendanceTest> attendances) {
        this.attendances = attendances;
    }

}
