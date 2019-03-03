package com.myspring.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myspring.beans.DBBean;
import com.myspring.entities.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    DBBean dbbean;

    public static Gson gson = new Gson();
    //public static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

//    @CrossOrigin
//    @ResponseBody
//    @RequestMapping(value = {"index", "/"})
//    public String indexPage(@RequestBody UserTest obj) {
//        System.out.println(obj);
//        System.out.println(obj.getUserId() + " " + obj.getLogin() + " " + obj.getPassword());
//        return gson.toJson(obj);
//    }

        @RequestMapping(value = "index", method = RequestMethod.GET)
        public ModelAndView indexPage() {
            return new ModelAndView("index");
        }





    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@RequestBody UserTest user) {
        try {
            UserTest tempUser = new UserTest(0, user.getLogin(), user.getPassword(), user.getRole());

            dbbean.addUser(tempUser);
            ArrayList<UserTest> users = new ArrayList<UserTest>();

            return gson.toJson(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
        return "";
    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody UserTest user) {
        try {
            UserTest tempUser =  dbbean.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
            if (tempUser == null) {
                return gson.toJson("not found");
            }
            else {
                return gson.toJson("all okay");
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
        return "error occurred";
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
        return "error occurred";
    }
}
