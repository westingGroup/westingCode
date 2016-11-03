package com.infosys.westing.commerce.controller.demo;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infosys.westing.commerce.shiro.bind.annotation.CurrentUser;
import com.infosys.westing.commerce.shiro.entity.Resource;
import com.infosys.westing.commerce.shiro.entity.User;
import com.infosys.westing.commerce.shiro.service.ResourceService;
import com.infosys.westing.commerce.shiro.service.UserService;
/**
 * 
 * @author Anne_Yan
 * 2016年10月31日
 */
@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


}
