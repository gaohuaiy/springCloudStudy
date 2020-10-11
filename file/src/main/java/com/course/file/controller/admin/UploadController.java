package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping("/admin")
public class UploadController {
    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);
    public static final String BUSINESS_NAME = "文件上传";
    @Value("${file.path}")
    private String FILE_PATH;
    @Value("${file.domain}")
    private String FILE_DOMAIN;
    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file,String use) throws IOException {
        LOG.info("文件上传开始：{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));
        //保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        //如果文件夹不存在，创建
        String dir = useEnum.getDesc().toLowerCase();
        File fullDir = new File(FILE_PATH+dir);
        if (!fullDir.exists()){
            fullDir.mkdirs();
        }
        String path = dir+File.separator + key + "." + filename;
        String fullPath = FILE_PATH+path;
        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(filename);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse(useEnum.getCode());
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN+path);
        responseDto.setContent(fileDto);
        return responseDto;
    }

}
