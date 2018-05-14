package com.cbc.design.auth.service;

import com.cbc.design.auth.domain.User;
import com.cbc.design.auth.repositories.UserRepository;
import com.cbc.design.common.FaceRecognitionUtil;
import com.cbc.design.common.RepeatSubmit;
import com.cbc.design.common.ResultEnum;
import com.cbc.design.common.SmsService;
import com.cbc.design.common.exception.RepeatSubmitException;
import com.cbc.design.common.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.hibernate.jpa.event.internal.core.HibernateEntityManagerEventListener;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Created by cbc on 2018/3/29.
 */
@Service
@AllArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;

    private final SmsService smsService;

    @PersistenceContext
    private EntityManager entityManager;

    public Integer sendEmail(String phone, String photo, String email, String code, String action, HttpServletRequest req){
        //0为图片验证码错误
        if(!photo.equalsIgnoreCase(code)){
            return 0;
        }

        if("forget".equalsIgnoreCase(action)){
            if(!userRepository.findByEmail(email).isPresent()){
                return -1;
            }
            //
        }else if("register".equalsIgnoreCase(action)){
            if(userRepository.findByPhone(phone).isPresent()){
                 //2为手机号码已经被注册
                return 2;
            }
            if(userRepository.findByEmail(email).isPresent()){
                //3为邮箱已经被注册
                return 3;
            }
        }
        //生成四位随机验证码
        String code_phone = generateCodePhone(req);

        //生成邮件内容
        String content = generateCodeMailContent(code_phone);

        //发送邮件
        smsService.sendSimpleMail(email,"来自CBC毕设的邮件验证码",content);
        return 1;
    }

    private String generateCodeMailContent(String code_phone) {
        StringBuilder sb = new StringBuilder();
        sb.append("【CBC毕业设计的验证码】您的验证码是：");
        sb.append(code_phone);
        sb.append(",请保护好您的验证码。");
        return sb.toString();
    }


    // 生成验证码
    private String generateCodePhone(HttpServletRequest req) {
        Random random = new Random();
        String r = "";
        for (int i = 0; i < 4; i++) {
            r += random.nextInt(10);
        }
        req.getSession().setAttribute("codePhone", r);
        return r;
    }

    public Integer checkPhoneAndEmail(HttpServletRequest request, String photo, String code, String code_mail) {
        if(!code.equalsIgnoreCase(photo)){
            //2为图片验证码错误
            return 2;
        }else {
            String codeMail = (String) request.getSession().getAttribute("codePhone");
            if(!code_mail.equalsIgnoreCase(codeMail)){
              //3为邮箱验证码错误
                return 3;
            }
        }
        return 1;
    }


    @Transactional
    public void addFace(String email, String img) {
        Optional<User> user = userRepository.findByEmail(email);
        HibernateEntityManager manager = (HibernateEntityManager) this.entityManager;
        Session session = manager.getSession();
        session.evict(user.get());
        if (!user.isPresent()){
            throw new UsernameNotFoundException("没有找到相关的用户信息！");
        }

        boolean success = FaceRecognitionUtil.addUserFace(user.get(), img);
        if(!success){
            throw new ValidationException(ResultEnum.FACE_UNKNOW);
        }
    }
}
