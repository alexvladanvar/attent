package com.myspring.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "attendance_test")
public class Attendance {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @Expose
    @Column(name = "attended")
    private Boolean attended = false;

    @Expose
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "student_id")
    private Student student;

    @Expose
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="lesson_id")
    private Lesson lesson;

    public Attendance() {
    }

    public Attendance(boolean attended, Student student, Lesson lesson) {
        this.attended = attended;
        this.student = student;
        this.lesson = lesson;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

}
