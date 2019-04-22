package com.myspring.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myspring.beans.DBBean;

import com.myspring.db.TimeDB;
import com.myspring.entities.*;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.util.MatcherAssertionErrors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    DBBean dbbean;

    public static Gson gson = new Gson();
    public static Gson gson2 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = {"index", "/"})
    public String indexPage(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
  
        if (session == null) {
            return gson.toJson(new JsonResponse(false));
        }
        else {
            UserTest user = (UserTest)session.getAttribute("user");
            return gson.toJson(user);
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@RequestBody TransitUser user) {
        try {
            UserTest tempUser = new UserTest(user.getLogin(), user.getPassword(), user.getRole());
            dbbean.addUser(tempUser);
            if (tempUser.getRole() == 1) {
                GroupsTest groupsTest = null;
                groupsTest = dbbean.getGroupByName(user.getGroupName());
                StudentsTest studentsTest = new StudentsTest(user.getFirstName(),user.getLastName(),groupsTest, tempUser);
                dbbean.addStudent(studentsTest);
            }
            else if(tempUser.getRole() == 2){
                TeachersTest teachersTest = new TeachersTest(user.getFirstName(),user.getLastName(), tempUser);
                dbbean.addTeacher(teachersTest);
            }

            ArrayList<UserTest> users = new ArrayList<UserTest>();
            users.add(tempUser);

            return gson.toJson(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public String getUsers() {
        try {
            List<UserTest> users = dbbean.getAllUsers();
            return gson.toJson(users);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserTest user, HttpServletRequest req) {
        try {
            UserTest tempUser =  dbbean.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
            if (tempUser == null) {
                return gson.toJson(new JsonResponse(false));
            }
            else {
                HttpSession s = req.getSession(true);
                s.setAttribute("USER", tempUser);
                return gson.toJson(new JsonResponse(true)); // session
            }
        }catch (Exception e ) {
            e.printStackTrace();
        }
        return "";
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser (@RequestBody UserTest user) {
        try {
            dbbean.removeUser(user);
            return gson.toJson("deleted");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser (@RequestBody UserTest user) {
        try {
            dbbean.updateUser(user);
            return gson.toJson(user);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String getUserById (HttpServletRequest req) {
        try {
            UserTest tempUser = (UserTest)req.getSession(false).getAttribute("USER");
            return gson.toJson(tempUser);
        } catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    public String logout(HttpServletRequest req) {

        if (req.getSession() != null) {
            req.getSession().invalidate();
            return gson.toJson(new JsonResponse(true));
        }
        return gson.toJson(new JsonResponse(false));

    }

    @RequestMapping(value = "/getGroup/{groupId}", method = RequestMethod.GET)
    @CrossOrigin
    @ResponseBody
    public String getGroupById(@PathVariable("groupId") String groupId) {
        try {
            GroupsTest tempGroup = dbbean.getGroupById(Integer.parseInt(groupId));
            return gson.toJson(tempGroup);
        }catch (Exception e ) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }

    @GetMapping(path = "/attendances")
    @CrossOrigin
    @ResponseBody
    public String getMyAttendaces(HttpServletRequest request) {
        try {
            UserTest tempUser = (UserTest)request.getSession(false).getAttribute("USER");
            System.err.println(tempUser.getLogin());
            StudentsTest tempStudentTest = dbbean.getStudentById(tempUser.getUserId());
            System.err.println(tempStudentTest.getFirstName() + ' ' + tempStudentTest.getLastName());
            List<AttendanceTest> attendances = dbbean.getAllAttendanceByStudentId(tempStudentTest.getStudentId());
            return  gson2.toJson(attendances);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @PostMapping(path = "/generateQR")
    @CrossOrigin
    @ResponseBody
    public String generateQRCode(@RequestBody QRClass qrClass) {
        try {
            String generatedString = RandomStringUtils.randomAlphabetic(10);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(generatedString.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

            System.out.println(generatedString);
            System.out.println(myHash);
            qrClass.setHash(myHash);
            return gson.toJson(qrClass);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }


    @GetMapping(path = "/getUserData")
    @CrossOrigin
    @ResponseBody
    public String getUserData(HttpServletRequest req) {
        try {
            UserTest tempUser = (UserTest)req.getSession(false).getAttribute("USER");
            if (tempUser.getRole() == 1) {
                StudentsTest studentsTest = dbbean.getStudentById(tempUser.getUserId());
                return gson2.toJson(studentsTest);
            }
            else {
                TeachersTest teachersTest = dbbean.getTeacherById(tempUser.getUserId());
                return gson2.toJson(teachersTest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }


    @PostMapping(path = "/setDefaultAtt")
    @CrossOrigin
    @ResponseBody
    public String setDefaultAtt(@RequestBody TransitLesson transitLesson, HttpServletRequest req) {
        try {
            UserTest tempUser = (UserTest)req.getSession(false).getAttribute("USER");
            TeachersTest teachersTest = dbbean.getTeacherById(tempUser.getUserId());

            dbbean.setDefaultAttendance(transitLesson, teachersTest);

            System.err.println(transitLesson);
            return gson.toJson(transitLesson);
        }
        catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }


    @GetMapping(path = "/checkAtt/{lessonId}")
    @CrossOrigin
    @ResponseBody
    public String checkAttendance(@PathVariable("lessonId") int lessonId, HttpServletRequest req) {
        try {
            UserTest tempUser = (UserTest) req.getSession(false).getAttribute("USER");
            AttendanceTest attendanceTest = dbbean.getAttbyIds(tempUser.getUserId(), lessonId);
            attendanceTest.setAttended(true);
            dbbean.updateAttendance(attendanceTest);
            return gson.toJson(new JsonResponse(true));
        }
        catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }
}
