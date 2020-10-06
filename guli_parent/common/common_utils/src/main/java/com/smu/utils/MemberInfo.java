package com.smu.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberInfo implements Serializable {
    private String id;

    private String openid;

    private String mobile;

    private String password;

    private String nickname;

    private Integer sex;

    private Integer age;

    private String avatar;

    private String sign;

    private Boolean isDisabled;

    private Boolean isDeleted;

    private Date gmtCreate;

    private Date gmtModified;
}
