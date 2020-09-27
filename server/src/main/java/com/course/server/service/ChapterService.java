package com.course.server.service;

import com.course.server.domain.Chapter;
import com.course.server.domain.ChapterExample;
import com.course.server.dto.ChapterDto;
import com.course.server.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    private ChapterMapper chaprerMapper;

    public List<ChapterDto> list(){
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria();
        List<Chapter> chapterList = chaprerMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtos = new ArrayList<>();
        for (int i = 0; i <chapterList.size() ; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto );
            chapterDtos.add(chapterDto);
        }
        return chapterDtos;
    }
}
