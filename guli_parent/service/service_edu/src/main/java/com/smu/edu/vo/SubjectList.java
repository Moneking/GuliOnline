package com.smu.edu.vo;

import com.smu.edu.domain.Subject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectList {
    private String id;
    private String title;
    private List<Subject> children = new ArrayList<>();
}
