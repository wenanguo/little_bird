package com.cmtt.base.controller;

import com.cmtt.base.entity.R;
import com.cmtt.base.service.impl.TencentServiceImpl;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付宝 前端控制器
 * </p>
 *
 * @author Andrew.Wen
 * @since 2020-11-25
 */
@Controller
@RequestMapping("/api/tencent")
@Api(tags = "支付相关")
public class TencentController {


    private final Logger logger = LoggerFactory.getLogger(TencentController.class);

    @Autowired
    private TencentServiceImpl tencentService;


    @GetMapping("/upload.html")
    public String upload() {
        return "upload";
    }


    @PostMapping({"/images/upload"})
    @ResponseBody
    public R upload(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return R.err().setMessage("上传失败，请选择文件");
        } else {


            try {


                File file = new File(multipartFile.getOriginalFilename());
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);

                String url =tencentService.uploadImg(file);
                // 会在本地产生临时文件，用完后需要删除
                if (file.exists()) {
                    file.delete();
                }



                logger.info("上传成功");

                Map<String,String> map=new HashMap<>();
                map.put("url",url);

                return R.ok().setResult(map);
            } catch (Exception var6) {
                logger.error(var6.toString(), var6);
                return R.err().setMessage("上传失败！");
            }
        }
    }


}
