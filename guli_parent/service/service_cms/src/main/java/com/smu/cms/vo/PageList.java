package com.smu.cms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PageList<T> {
    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "记录")
    private List<T> records;
}
