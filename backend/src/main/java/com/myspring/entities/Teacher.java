package com.myspring.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "teachers_test")
public class Teacher {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;

    @Expose
    @Column(name = "last_name")
    private String lastName;

    @Expose
    @Column(name = "first_name")
    private String firstName;

    @Expose
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Teacher() {
    }

    public Teacher(String lastName, String firstName, User user) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.user = user;
    }

    public int getTeaherId() {
        return teacherId;
    }

    public void setTeaherId(int teaherId) {
        this.teacherId = teaherId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
