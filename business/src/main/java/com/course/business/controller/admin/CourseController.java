package com.course.business.controller.admin;

import com.course.server.domain.CourseCategory;
import com.course.server.dto.CourseContentDto;
import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseCategoryService;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "课程";

    @Resource
    private CourseService courseService;

    @Resource
    private CourseCategoryService courseCategoryService;

    /**
     * 列表查询
     */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto) {
        // 保存校验
        ValidatorUtil.require(courseDto.getName(), "名称");
        ValidatorUtil.length(courseDto.getName(), "名称", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "概述", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "封面", 1, 100);

        ResponseDto responseDto = new ResponseDto();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }

    /**
     * 获取当前课程的分类
     * @param courseId
     * @return
     */
    @GetMapping("/list-category/{courseId}")
    public ResponseDto listCategory(@PathVariable(value = "courseId") String courseId){
        ResponseDto responseDto = new ResponseDto();
        List<CourseCategory> courseCategoryList = courseCategoryService.listByCourse(courseId);
        responseDto.setContent(courseCategoryList);
        return responseDto;
    }

    /**
     * 查询课程内容
     * @param id
     * @return
     */
    @GetMapping("/find-content/{id}")
    public ResponseDto findContend(@PathVariable(value = "id") String id){
        ResponseDto responseDto = new ResponseDto();
        CourseContentDto conent = courseService.findConent(id);
        responseDto.setContent(conent);
        return responseDto;
    }

    @PostMapping("/save-content")
    public ResponseDto saveContend(@RequestBody CourseContentDto courseContentDto) {

        ResponseDto responseDto = new ResponseDto();
        courseService.saveContent(courseContentDto);
        responseDto.setContent(courseContentDto);
        return responseDto;
    }


}
