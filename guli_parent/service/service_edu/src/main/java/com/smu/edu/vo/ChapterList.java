package com.smu.edu.vo;

import com.smu.edu.domain.Chapter;
import com.smu.edu.domain.Video;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterList extends Chapter {
    private List<Video> children = new ArrayList<>();
}
