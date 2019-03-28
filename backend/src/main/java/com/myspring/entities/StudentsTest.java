package com.myspring.entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "students_test")
public class StudentsTest {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    @Expose
    @Column(name = "first_name")
    private String firstName;

    @Expose
    @Column(name = "last_name")
    private String lastName;

    @Expose
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private GroupsTest group;

    @OneToOne
    private UserTest userTest;

    public StudentsTest() {
    }

    public StudentsTest(String firstName, String lastName, GroupsTest group, UserTest userTest) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.userTest = userTest;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GroupsTest getGroup() {
        return group;
    }

    public void setGroup(GroupsTest group) {
        this.group = group;
    }

    public UserTest getUserTest() {
        return userTest;
    }

    public void setUserTest(UserTest userTest) {
        this.userTest = userTest;
    }
}
