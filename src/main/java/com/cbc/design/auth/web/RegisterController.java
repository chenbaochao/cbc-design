package com.cbc.design.auth.web;

import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.service.RegisterService;
import com.cbc.design.auth.service.UserService;
import com.cbc.design.common.*;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * Created by cbc on 2018/3/28.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/register")
public class RegisterController {

    private final RegisterService registerService;

    private final UserService userService;
    /**
     *  发送邮件
     * @param req
     * @param res
     * @param phone
     * @param photo
     * @param email
     * @param code
     * @param action
     * @return
     */
    @PostMapping("/send/email")
    public ResponseBean sendEmail(HttpServletRequest req, HttpServletResponse res,
                                        String phone, String photo, String email, String code, String action){
        req.getSession().removeAttribute("codePhone");
        Integer result = registerService.sendEmail(phone, photo, email, code, action, req);

        res.setContentType("text/html;charset=utf-8");
        return ResultUtil.success(result);
    }

    /**
     *  注册校验第一步
     * @param request
     * @param photo
     * @param code
     * @param code_mail
     * @return
     */
    @PostMapping("/check/phone")
    public ResponseBean checkPhoneAndEmail(HttpServletRequest request,
                                  @RequestParam String photo,
                                  @RequestParam String code,
                                  @RequestParam String code_mail){

        Integer result = registerService.checkPhoneAndEmail(request, photo, code, code_mail);
        return ResultUtil.success(result);
    }

    /**
     *  添加用户
     * @param user
     * @param token
     * @param req
     * @return
     */
    @PostMapping("/save")
    public ResponseBean addUser(@RequestBody @Valid User user, @RequestParam String token, HttpServletRequest req){
        String register2_token = (String) req.getSession().getAttribute("register2_token");
        userService.addUser(user,token,register2_token,req);
        return ResultUtil.success();
    }


    @PostMapping("/add/face")
    public ResponseBean addFace(@RequestParam String email,@RequestParam String img){
        registerService.addFace(email,img);
        return ResultUtil.success();
    }

 }
