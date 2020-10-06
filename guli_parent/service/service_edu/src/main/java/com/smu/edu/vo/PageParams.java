package com.smu.edu.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageParams {
    @ApiModelProperty(value = "页数")
    private Long current;

    @ApiModelProperty(value = "条数")
    private Long size;
}
