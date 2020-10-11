package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public ResponseDto upload(@RequestParam MultipartFile shard,
                              Integer shardIndex,
                              Integer shardSize,
                              Integer shardTotal,
                              Integer size   ,
                              String use    ,
                              String suffix   ,
                              String key   ,
                              String name) throws IOException {

        LOG.info("文件上传开始：");
        //保存文件到本地
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        //如果文件夹不存在，创建
        String dir = useEnum.getDesc().toLowerCase();
        File fullDir = new File(FILE_PATH+dir);
        if (!fullDir.exists()){
            fullDir.mkdirs();
        }
        String path = dir+File.separator + key + "." + suffix;
        String fullPath = FILE_PATH+path;
        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        LOG.info("保存文件记录开始");
        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(name);
        fileDto.setSize(size);
        fileDto.setSuffix(suffix);
        fileDto.setUse(useEnum.getCode());
        fileDto.setShardIndex(shardIndex);
        fileDto.setShardSize(shardSize);
        fileDto.setShardTotal(shardTotal);
        fileDto.setKey(key);
        fileService.save(fileDto);

        ResponseDto responseDto = new ResponseDto();
        fileDto.setPath(FILE_DOMAIN+path);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    @GetMapping("/merge")
    public ResponseDto merge() throws Exception{
        File newFile = new File(FILE_PATH+"/课程/test1234.mp4");
        FileOutputStream fileOutputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream fileInputStream = null;//分片文件
        byte[] byt = new byte[10*1024*1024];
        int len;

        fileInputStream = new FileInputStream(FILE_PATH+"/课程/UdU8bxdd.blob");//读取第一个分片
        while ((len = fileInputStream.read(byt))!=-1){
            fileOutputStream.write(byt,0,len);
        }
        fileInputStream = new FileInputStream(FILE_PATH+"/课程/qF7nYSki.blob");//读取第二个分片
        while ((len = fileInputStream.read(byt))!=-1){
            fileOutputStream.write(byt,0,len);
        }
        return new ResponseDto();
    }


}
