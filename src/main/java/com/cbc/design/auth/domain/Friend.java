package com.cbc.design.auth.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 *   好友关系
 * Created by cbc on 2018/4/1.
 */
@Data
@Entity
public class Friend implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long friendId;
}
