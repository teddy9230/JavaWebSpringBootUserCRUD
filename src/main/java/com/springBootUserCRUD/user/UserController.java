package com.springBootUserCRUD.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model){
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "新增使用者");
        return "user_form";

    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes){
       System.out.println(user.getPassword());
        service.save(user);
        redirectAttributes.addFlashAttribute("message", "新增使用者成功!!");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "修改使用者 (使用者的 ID 是 " + id + ")");

            return "user_form";
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "使用者資料 修改成功!!");
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        try{
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", "使用者ID = " + id + " 已刪除成功!!");
        }catch(UserNotFoundException ex){
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }
}
