package com.myspring.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "teachers_test")
public class TeachersTest {
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
    private UserTest userTest;

    public TeachersTest() {
    }

    public TeachersTest(String lastName, String firstName, UserTest userTest) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userTest = userTest;
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

    public UserTest getUserTest() {
        return userTest;
    }

    public void setUserTest(UserTest userTest) {
        this.userTest = userTest;
    }
}
