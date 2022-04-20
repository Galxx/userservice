package com.gorokhov.example.controller;

import com.gorokhov.example.dto.RoleDTO;
import com.gorokhov.example.dto.UserDTO;
import com.gorokhov.example.service.RoleService;
import com.gorokhov.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {

    private final UserService userService;
    private final RoleService roleService;

    public HomeController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("api_user", createCURL("http://localhost:3000/api/user",principal));
            model.addAttribute("api_admin", createCURL("http://localhost:3000/api/admin",principal));
            model.addAttribute("api_listuser",createCURL("http://localhost:3000/api/listuser",principal));
            model.addAttribute("api_listuser2", createCURL("http://localhost:3001/api/listuser",principal));
        }
        return "index";
    }

    @RequestMapping(value = "add")
    public String addUser(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "addUser";
    }

    @RequestMapping(value = "save")
    public String saveUser(UserDTO userDTO){

        userService.saveUser(userDTO);

        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editUser(@PathVariable("id") Long userId, Model model){
        model.addAttribute("userDTO", new UserDTO(userService.findById(userId)));
        return "editUser";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") Long userId) {
        userService.deleteById(userId);
        return "redirect:/";
    }

    @RequestMapping(value = "addrole")
    public String addRole(Model model){
        model.addAttribute("roleDTO", new RoleDTO());
        return "addRole";
    }

    @RequestMapping(value = "saverole")
    public String saveRole(RoleDTO roleDTO){

        roleService.saveRole(roleDTO);

        return "redirect:/";
    }

    @RequestMapping(value = "/editrole/{id}")
    public String editRole(@PathVariable("id") Long roleId, Model model){
        model.addAttribute("roleDTO", new RoleDTO(roleService.findById(roleId)));
        return "editRole";
    }

    @RequestMapping(value = "/deleterole/{id}", method = RequestMethod.GET)
    public String deleteRole(@PathVariable("id") Long roleId) {
        roleService.deleteById(roleId);
        return "redirect:/";
    }

    private String createCURL(String URL,OidcUser principal){

        return "curl --location --request GET '" + URL + "' \n" +
                "--header 'Authorization: Bearer " + principal.getIdToken().getTokenValue()+"'";
    }


}