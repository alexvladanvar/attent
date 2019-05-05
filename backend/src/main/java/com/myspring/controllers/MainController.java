package com.myspring.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myspring.beans.DBBean;
import com.myspring.entities.*;
import com.myspring.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    DBBean dbbean;


    public static Gson gson = new GsonBuilder().setDateFormat("MMM d HH:mm").create();
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
            User user = (User)session.getAttribute("user");
            return gson.toJson(user);
        }
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@RequestBody TransitUser user) {
        try {
            User tempUser = new User(user.getLogin(), user.getPassword(), user.getRole());
            dbbean.addUser(tempUser);
            if (tempUser.getRole() == 1) {
                Group group = null;
                group = dbbean.getGroupByName(user.getGroupName());
                Student student = new Student(user.getFirstName(),user.getLastName(), group, tempUser);
                dbbean.addStudent(student);
            }
            else if(tempUser.getRole() == 2){
                Teacher teacher = new Teacher(user.getFirstName(),user.getLastName(), tempUser);
                dbbean.addTeacher(teacher);
            }

            ArrayList<User> users = new ArrayList<User>();
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
            List<User> users = dbbean.getAllUsers();
            return gson.toJson(users);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user, HttpServletRequest req) {
        try {
            User tempUser =  dbbean.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
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
    public String deleteUser (@RequestBody User user) {
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
    public String editUser (@RequestBody User user) {
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
            User tempUser = (User)req.getSession(false).getAttribute("USER");
            return gson.toJson(new JsonResponse(true));
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
            Group tempGroup = dbbean.getGroupById(Integer.parseInt(groupId));
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
            User tempUser = (User)request.getSession(false).getAttribute("USER");
            System.err.println(tempUser.getLogin());
            Student tempStudent = dbbean.getStudentById(tempUser.getUserId());
            System.err.println(tempStudent.getFirstName() + ' ' + tempStudent.getLastName());
            List<Attendance> attendances = dbbean.getAllAttendanceByStudentId(tempStudent.getStudentId());
            return  gson2.toJson(attendances);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }

    @GetMapping(path = "/attendancesV2")
    @CrossOrigin
    @ResponseBody
    public String getMyAttendacesV2(HttpServletRequest request) {
        try {
            User tempUser = (User)request.getSession(false).getAttribute("USER");
            System.err.println(tempUser.getLogin());
            Student tempStudent = dbbean.getStudentById(tempUser.getUserId());
            System.err.println(tempStudent.getFirstName() + ' ' + tempStudent.getLastName());
            List<Attendance> rawAttendances = dbbean.getAllAttendanceByStudentId(tempStudent.getStudentId());
            List<AttendanceData> attendances = rawAttendances.stream().map(a ->
                    new AttendanceData(a.isAttended(), a.getLesson().getLessonId(), a.getLesson().getSubject().getName(), a.getLesson().getTeacher().getFirstName() + " " + a.getLesson().getTeacher().getLastName(), a.getLesson().getGroups().getGroupName())
            ).collect(Collectors.toList());
            return  gson.toJson(attendances);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(new JsonResponse(false));
    }


    @GetMapping(path = "/getUserData")
    @CrossOrigin
    @ResponseBody
    public String getUserData(HttpServletRequest req) {
        try {
            User tempUser = (User)req.getSession(false).getAttribute("USER");
            if (tempUser.getRole() == 1) {
                Student student = dbbean.getStudentById(tempUser.getUserId());
                return gson2.toJson(student);
            }
            else {
                Teacher teacher = dbbean.getTeacherById(tempUser.getUserId());
                return gson2.toJson(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }

    @GetMapping(path = "/getLessons")
    @CrossOrigin
    @ResponseBody
    public String getLessons(HttpServletRequest req) {
        try {
            User tempUser = (User)req.getSession(false).getAttribute("USER");
            Teacher teacher = dbbean.getTeacherById(tempUser.getUserId());

            List<Lesson> lessons = dbbean.getLessons(teacher.getTeacherId());
            List<LessonData> lessonDatas = lessons.stream().map(l ->
                    new LessonData(l.getLessonId(), l.getSubject().getName(), l.getGroup().getGroupName(), l.getDate())
            ).collect(Collectors.toList());

            return gson.toJson(lessonDatas);

        } catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }

    @PostMapping(path = "/createLesson")
    @CrossOrigin
    @ResponseBody
    public String createLesson(@RequestBody CreateLessonRequest data, HttpServletRequest req) {
        try {
            User tempUser = (User)req.getSession(false).getAttribute("USER");
            Teacher teacher = dbbean.getTeacherById(tempUser.getUserId());


            dbbean.setDefaultAttendance(data, teacher);

            System.err.println(data);
            return gson.toJson(new JsonResponse(true));
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
            User tempUser = (User) req.getSession(false).getAttribute("USER");
            Attendance attendance = dbbean.getAttbyIds(tempUser.getUserId(), lessonId);
            attendance.setAttended(true);
            dbbean.updateAttendance(attendance);
            return gson.toJson(new JsonResponse(true));
        }
        catch (Exception e) {
            e.printStackTrace();
            return gson.toJson(new JsonResponse(false));
        }
    }
}
