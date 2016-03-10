package me.laudukang.spring.controller;

import me.laudukang.persistence.model.OsAdmin;
import me.laudukang.persistence.service.IAdminService;
import me.laudukang.spring.domain.AdminLoginDomain;
import me.laudukang.util.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/2/27
 * <p>Time: 0:12
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        model.addAttribute("adminLoginDomain", new AdminLoginDomain());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid @ModelAttribute AdminLoginDomain adminLoginDomain, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("adminLoginDomain", adminLoginDomain);
            return "redirect:login";
        }
        Object[] osAdmin = adminService.login(adminLoginDomain.getAccount(), adminLoginDomain.getPassword());
        if (null != osAdmin && osAdmin.length > 0) {
            session.setAttribute("adminid", osAdmin[0]);//admin.id,admin.account,admin.name
            session.setAttribute("account", osAdmin[2]);
            session.setAttribute("name", osAdmin[3]);
            return "welcome";
        }
        model.addAttribute("msg", "账号不存在或密码错误");
        return "redirect:login";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePasswordPage() {
        return "";
    }

    @RequestMapping(value = "updatePassword", method = RequestMethod.POST)
    public String updatePassword(String password, String newPassword1, String newPassword2, HttpSession session, Model model) {
        boolean check;
        boolean isSame = !isNullOrEmpty(newPassword1) && !isNullOrEmpty(newPassword2) && newPassword1.equals(newPassword2);
        int tmp = 0;
        if (check = !isNullOrEmpty(password)) {
            int id = Integer.valueOf(String.valueOf(session.getAttribute("adminid")));
            OsAdmin osAdmin = adminService.findOne(id);
            if (null != osAdmin && osAdmin.getPassword().equals(password) && isSame) {
                tmp = adminService.updatePassword(id, newPassword1);
            }
        }
        model.addAttribute("success", check && 1 == tmp ? true : false);
        model.addAttribute("msg", check && 1 == tmp ? "成功修改密码" : "修改密码失败");
        return "";
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.GET)
    public String newAdminPage(Model model) {
        model.addAttribute("osAdmin", new OsAdmin());
        return "";
    }

    @RequestMapping(value = "newAdmin", method = RequestMethod.POST)
    public String newAdmin(@ModelAttribute OsAdmin osAdmin, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osAdmin", osAdmin);
            return "redirect:newAdmin";
        }
        if (adminService.existAccount(osAdmin.getAccount())) {
            bindingResult.reject("accountExist", "账号已存在");
            return "redirect:newAdmin";
        }
        adminService.save(osAdmin);
        return "";
    }

    @RequestMapping(value = "deleteAdmin", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@RequestParam("id") int id) {
        if (1 == id) {
            return MapUtil.forbiddenOperationMap;
        }
        adminService.deleteById(id);
        return MapUtil.deleteMap();
    }

    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.GET)
    public String updateAdminPage(Model model) {
        model.addAttribute("osAdmin", new OsAdmin());
        return "";
    }

    @RequestMapping(value = "updateAdminInfo", method = RequestMethod.POST)
    public String updateAdminInfo(@ModelAttribute OsAdmin osAdmin, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("osAdmin", osAdmin);
            return "redirect:";
        }
        adminService.updateById(osAdmin);
        return "";
    }

    @RequestMapping(value = "findAllAdmin", method = RequestMethod.GET)
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("success", true);
        map.put("msg", "");
        return map;
        // TODO: 分页查询 2016/3/9
    }

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        request.getSession().setAttribute("adminid", 1);
    }
}