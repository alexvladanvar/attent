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
        CriteriaQuery<UserTest> query2 = criteriaBuilder.createQuery(UserTest.class);
        Root<UserTest> roo = query2.from(UserTest.class);
        UserTest user = session.createQuery(query2.where(criteriaBuilder.equal(roo.get("userId"), id))).getSingleResult();

        CriteriaQuery<StudentsTest> query = criteriaBuilder.createQuery(StudentsTest.class);
        Root<StudentsTest> root = query.from(StudentsTest.class);
        StudentsTest studentsTest = session.createQuery(query.where(criteriaBuilder.equal(root.get("userTest"), user))).getSingleResult();
        session.close();
        return studentsTest;
    }

    public TeachersTest getTeacherById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserTest> query2 = criteriaBuilder.createQuery(UserTest.class);
        Root<UserTest> roo = query2.from(UserTest.class);
        UserTest user = session.createQuery(query2.where(criteriaBuilder.equal(roo.get("userId"), id))).getSingleResult();

        CriteriaQuery<TeachersTest> query = criteriaBuilder.createQuery(TeachersTest.class);
        Root<TeachersTest> root = query.from(TeachersTest.class);
        TeachersTest teachersTest = session.createQuery(query.where(criteriaBuilder.equal(root.get("userTest"), user))).getSingleResult();
        session.close();
        return teachersTest;
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

    public void setDefaultAttendance(TransitLesson transitLesson, TeachersTest teachersTest){
        LessonsTest lessonsTest = new LessonsTest();
        GroupsTest tempGroup = getGroupById(transitLesson.getGroupId());
        lessonsTest.setGroup(tempGroup);
        lessonsTest.setName(transitLesson.getLessonName());
        lessonsTest.setTeacher(teachersTest);

        Session session = sessionFactory.openSession();
        session.save(lessonsTest);
        List<StudentsTest> studentsTests = session.createQuery("FROM StudentsTest where group.groupId = :groupId").setParameter("groupId",tempGroup.getGroupId()).list();
        for (StudentsTest st : studentsTests) {
            AttendanceTest at = new AttendanceTest();
            at.setLesson(lessonsTest);
            at.setStudent(st);
            at.setAttended(false);
            session.save(at);
        }
        session.close();
        System.err.println("Раз два три четыре пять, с детства с рифмой я дружу");
    }


    public void updateAttendance(AttendanceTest attendanceTest){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(attendanceTest);
        transaction.commit();
        session.close();
    }

    public AttendanceTest getAttbyIds(int userId, int lessonId) {
        Session session = sessionFactory.openSession();

        System.out.println("qwe");
        AttendanceTest attendanceTest = (AttendanceTest) session.createQuery("" +
                "SELECT at " +
                "FROM AttendanceTest at " +
                "join UserTest ut ON ut.userId = :userId " +
                "join StudentsTest st ON st.userTest.userId = ut.userId " +
                "join LessonsTest ls ON ls.lessonId = :lessonId " +
                "where at.student.userTest.userId = :userId " +
                "and at.lesson.lessonId = :lessonId")
                .setParameter("userId",userId)
                .setParameter("lessonId",lessonId)
                .uniqueResult();

        session.close();
        return attendanceTest;
    }
}
