package com.myspring.beans;

import com.myspring.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class DBBean {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DBBean() {

    }

    public void addUser(UserTest user){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void addStudent(StudentsTest studentsTest) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(studentsTest);
        transaction.commit();
        session.close();
    }

    public void addTeacher(TeachersTest teachersTest) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(teachersTest);
        transaction.commit();
        session.close();
    }

    public List<UserTest> getAllUsers() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserTest> query = criteriaBuilder.createQuery(UserTest.class);
        Root<UserTest> root = query.from(UserTest.class);
        List<UserTest> allUsers = session.createQuery(query).getResultList();
        session.close();
        return allUsers;
    }

    public UserTest getUserByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserTest> criteriaQuery = builder.createQuery(UserTest.class);
        Root root = criteriaQuery.from(UserTest.class);
        Predicate predicate = builder.and(builder.equal(root.get("login"), login), builder.equal(root.get("password"), password)); //-
        UserTest userList = session.createQuery(criteriaQuery.where(predicate)).uniqueResult();
        return (userList!=null?userList:null);
    }

    public UserTest getUserById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserTest> query = criteriaBuilder.createQuery(UserTest.class);
        Root<UserTest> root = query.from(UserTest.class);
        UserTest users = session.createQuery(query.where(criteriaBuilder.equal(root.get("userId"), id))).getSingleResult();
        session.close();
        return users;
    }

    public void removeUser(UserTest user){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public void updateUser(UserTest user){
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
    }

    public GroupsTest getGroupByName(String name) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GroupsTest> query = criteriaBuilder.createQuery(GroupsTest.class);
        Root<GroupsTest> root = query.from(GroupsTest.class);
        GroupsTest group = session.createQuery(query.where(criteriaBuilder.equal(root.get("groupName"), name))).getSingleResult();
        session.close();
        return group;
    }

    public GroupsTest getGroupById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GroupsTest> query = criteriaBuilder.createQuery(GroupsTest.class);
        Root<GroupsTest> root = query.from(GroupsTest.class);
        GroupsTest group = session.createQuery(query.where(criteriaBuilder.equal(root.get("groupId"), id))).getSingleResult();
        session.close();
        return group;
    }

    public StudentsTest getStudentById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<StudentsTest> query = criteriaBuilder.createQuery(StudentsTest.class);
        Root<StudentsTest> root = query.from(StudentsTest.class);
        StudentsTest studentsTest = session.createQuery(query.where(criteriaBuilder.equal(root.get("studentId"), id))).getSingleResult();
        session.close();
        return studentsTest;
    }

    public List<AttendanceTest> getAllAttendanceByStudentId(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<AttendanceTest> query = criteriaBuilder.createQuery(AttendanceTest.class);
        Root<AttendanceTest> root = query.from(AttendanceTest.class);
        List<AttendanceTest> attendanceTest = session.createQuery(query.where(criteriaBuilder.equal(root.get("student"), id))).getResultList();
        session.close();
        return attendanceTest;
    }


}
