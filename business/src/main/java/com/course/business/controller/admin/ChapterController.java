package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("/chapter")
    public List<ChapterDto> chapter(){
        List<ChapterDto> list = chapterService.list();
        return list;
    }
}
