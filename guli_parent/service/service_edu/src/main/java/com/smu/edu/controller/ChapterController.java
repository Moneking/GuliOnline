package com.smu.edu.controller;


import com.smu.edu.domain.Chapter;
import com.smu.edu.service.ChapterService;
import com.smu.edu.vo.ChapterList;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {
    @Resource
    ChapterService chapterService;

    @GetMapping("/listChapter/{courseId}")
    public Result listChapter(@PathVariable("courseId") String courseId){
        List<ChapterList> list = chapterService.listChapter(courseId);
        return Result.success().data("list",list);
    }

    @PostMapping("/addChapter")
    public Result addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return Result.success();
    }

    @GetMapping("/getChapter/{chapterId}")
    public Result getChapter(@PathVariable String chapterId){
        Chapter chapter = chapterService.getById(chapterId);
        return Result.success().data("chapter",chapter);
    }

    @PostMapping("/updateChapter")
    public Result updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return Result.success();
    }

    @DeleteMapping("/deleteChapter/{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return Result.success();
        }else {
            return Result.error();
        }
    }
}

