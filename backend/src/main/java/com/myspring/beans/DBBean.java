package com.myspring.beans;

import com.myspring.entities.*;
import com.myspring.model.CreateLessonRequest;
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

    public User addUser(User user){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (int) session.save(user);
        user.setUserId(id);
        transaction.commit();
        session.close();
        return user;
    }

    public void addStudent(Student student) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(student);
        transaction.commit();
        session.close();
    }

    public void addTeacher(Teacher teacher) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(teacher);
        transaction.commit();
        session.close();
    }

    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<User> allUsers = session.createQuery(query).getResultList();
        session.close();
        return allUsers;
    }


    public User getUserByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root root = criteriaQuery.from(User.class);
        Predicate predicate = builder.and(builder.equal(root.get("login"), login), builder.equal(root.get("password"), password)); //-
        User userList = session.createQuery(criteriaQuery.where(predicate)).uniqueResult();
        return (userList!=null?userList:null);
    }

    public User getUserById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        User users = session.createQuery(query.where(criteriaBuilder.equal(root.get("userId"), id))).getSingleResult();
        session.close();
        return users;
    }

    public void removeUser(User user){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public void updateUser(User user){
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            session.close();
    }

    public Group getGroupByName(String name) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> query = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = query.from(Group.class);
        Group group = session.createQuery(query.where(criteriaBuilder.equal(root.get("groupName"), name))).getSingleResult();
        session.close();
        return group;
    }

    public Group getGroupById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> query = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = query.from(Group.class);
        Group group = session.createQuery(query.where(criteriaBuilder.equal(root.get("groupId"), id))).getSingleResult();
        session.close();
        return group;
    }

    public Student getStudentById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query2 = criteriaBuilder.createQuery(User.class);
        Root<User> roo = query2.from(User.class);
        User user = session.createQuery(query2.where(criteriaBuilder.equal(roo.get("userId"), id))).getSingleResult();

        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);
        Student student = session.createQuery(query.where(criteriaBuilder.equal(root.get("user"), user))).getSingleResult();
        session.close();
        return student;
    }

    public Teacher getTeacherById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query2 = criteriaBuilder.createQuery(User.class);
        Root<User> roo = query2.from(User.class);
        User user = session.createQuery(query2.where(criteriaBuilder.equal(roo.get("userId"), id))).getSingleResult();

        CriteriaQuery<Teacher> query = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);
        Teacher teacher = session.createQuery(query.where(criteriaBuilder.equal(root.get("user"), user))).getSingleResult();
        session.close();
        return teacher;
    }

    public List<Attendance> getAllAttendanceByStudentId(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Attendance> query = criteriaBuilder.createQuery(Attendance.class);
        Root<Attendance> root = query.from(Attendance.class);
        List<Attendance> attendance = session.createQuery(query.where(criteriaBuilder.equal(root.get("student"), id))).getResultList();
        session.close();
        return attendance;
    }

    public void setDefaultAttendance(CreateLessonRequest data, Teacher teacher){
        Lesson lesson = new Lesson();
        Group tempGroup = getGroupById(data.getGroupId());
        lesson.setGroup(tempGroup);
        Subject subject = getSubjectById(data.getSubjectId());
        lesson.setSubject(subject);
        lesson.setTeacher(teacher);
        lesson.setDate(data.getDate());


        Session session = sessionFactory.openSession();
        session.save(lesson);
        List<Student> students = session.createQuery("FROM Student where group.groupId = :groupId").setParameter("groupId",tempGroup.getGroupId()).list();
        for (Student st : students) {
            Attendance at = new Attendance();
            at.setLesson(lesson);
            at.setStudent(st);
            at.setAttended(false);
            session.save(at);
        }
        session.close();
    }


    public void updateAttendance(Attendance attendance){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(attendance);
        transaction.commit();
        session.close();
    }

    public Attendance getAttbyIds(int userId, int lessonId) {
        Session session = sessionFactory.openSession();

        System.out.println("qwe");
        Attendance attendance = (Attendance) session.createQuery("" +
                "SELECT at " +
                "FROM Attendance at " +
                "join User ut ON ut.userId = :userId " +
                "join Student st ON st.user.userId = ut.userId " +
                "join Lesson ls ON ls.lessonId = :lessonId " +
                "where at.student.user.userId = :userId " +
                "and at.lesson.lessonId = :lessonId")
                .setParameter("userId",userId)
                .setParameter("lessonId",lessonId)
                .uniqueResult();

        session.close();
        return attendance;
    }


    public Subject getSubjectById(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = criteriaBuilder.createQuery(Subject.class);
        Root<Subject> root = query.from(Subject.class);
        Subject subject = session.createQuery(query.where(criteriaBuilder.equal(root.get("subjectId"), id))).getSingleResult();
        session.close();
        return subject;
    }

    public List<Subject> getAllSubjects() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Subject> query = criteriaBuilder.createQuery(Subject.class);
        Root<Subject> root = query.from(Subject.class);
        List<Subject> allSubjects = session.createQuery(query).getResultList();
        session.close();
        return allSubjects;
    }

    public List<Group> getAllGroups() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Group> query = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = query.from(Group.class);
        List<Group> allGroups = session.createQuery(query).getResultList();
        session.close();
        return allGroups;
    }

    public List<Lesson> getLessons(int id) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = criteriaBuilder.createQuery(Teacher.class);
        Root<Teacher> root = query.from(Teacher.class);
        Teacher teacher = session.createQuery(query.where(criteriaBuilder.equal(root.get("teacherId"), id))).getSingleResult();


        CriteriaQuery<Lesson> query2 = criteriaBuilder.createQuery(Lesson.class);
        Root<Lesson> root2 = query2.from(Lesson.class);
        List<Lesson> lessons = session.createQuery(query2.where(criteriaBuilder.equal(root2.get("teacher"), teacher))).getResultList();
        session.close();
        return lessons;
    }

    public List<Attendance> getAttendacesByLessonId(int lessonId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Lesson> query = criteriaBuilder.createQuery(Lesson.class);
        Root<Lesson> root = query.from(Lesson.class);
        Lesson lesson = session.createQuery(query.where(criteriaBuilder.equal(root.get("lessonId"), lessonId))).getSingleResult();

        CriteriaQuery<Attendance> query2 = criteriaBuilder.createQuery(Attendance.class);
        Root<Attendance> root2 = query2.from(Attendance.class);
        List<Attendance> attendances = session.createQuery(query2.where(criteriaBuilder.equal(root2.get("lesson"), lesson))).getResultList();


        session.close();
        return attendances;
    }
}
