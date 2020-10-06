package com.smu.msm.domain;

import com.smu.utils.RandomUtil;
import lombok.Data;

@Data
public class TemplateParam {
    private String templateParam;

    private String code;

    public TemplateParam() {}
    public TemplateParam(String phone){
        this.templateParam = phone;
        this.code = RandomUtil.getFourBitRandom();
    }
}
