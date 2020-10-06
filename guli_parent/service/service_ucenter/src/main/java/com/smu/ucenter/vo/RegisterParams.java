package com.smu.ucenter.vo;

import lombok.Data;

@Data
public class RegisterParams {
    private String nickname;
    private String mobile;
    private String password;
    private String code;
}
