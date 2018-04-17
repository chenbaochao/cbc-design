package com.cbc.design.auth.domain;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;
import org.springframework.security.core.CredentialsContainer;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 *  用户
 * Created by cbc on 2017/12/21.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class User implements Comparable,Serializable,CredentialsContainer {


    private static final long serialVersionUID = -656075117215734549L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String username;

    @NotNull
    @Column(nullable = false, unique = true)
    private String phone;

    @NotNull
    private String password;

    // 昵称
    @NotEmpty
    private String nickname;

    @Builder.Default
    private String sex = "男";

    // 头像
    private String avatar;

    // 加入时间
    private LocalDate joinDate;

    // 简介
    private String autograph;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
        joinColumns =
            @JoinColumn(name="user_id",referencedColumnName = "id"),
          inverseJoinColumns =
            @JoinColumn(name="role_id",referencedColumnName = "id")
    )
    private Set<Role> roles;


    public User(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    /**
     * 初始化：头像 + 注册时间
     */
    public void init() {
        try {
            Document document = Jsoup.connect("http://www.woyaogexing.com/touxiang/new/").get();
            Elements elements = document.select("div.txList");
            int size = elements.size();
            List<String> avatars = new ArrayList<>(size);
            elements.forEach(element -> avatars.add(element.select("img.lazy").attr("src")));
            Random random = new Random();
            this.avatar = avatars.get(random.nextInt(size));
            this.joinDate = LocalDate.now();
            this.sex = "男";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return JSON.toJSONString(this);
    }

    @Override
    public int compareTo(@Nullable Object o) {
        if(o instanceof User){
            return this.username.compareTo(((User) o).username);
        }
        return 0;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
