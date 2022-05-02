package com.cecilia.work.controller.FileControllers;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    @Autowired
    UserMapper userMapper;
    @Value("${server.port}")
    private String port;
    private static final String ip="http://localhost";
    @RequestMapping("/upload/{id}")
    public Result<?> upload(MultipartFile file,@PathVariable("id") int id) throws IOException {
        String filename = file.getOriginalFilename();
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\filess\\resume\\";
        String flag = IdUtil.fastSimpleUUID();
        //        地址加文件名路径
        String path2 = path+id+"-"+flag+"_"+filename;
//        写入
        FileUtil.writeBytes(file.getBytes(),path2);
//        地址
        User user = userMapper.selectById(id);
        String d = ip+":"+port+"/resume/down/"+id+"/"+flag;
//        String d = "/resume/down/"+id+"/"+flag;
        user.setResume(d);
        userMapper.updateById(user);
        return Result.success(d);

    }
    @RequestMapping("/down/{id}/{flag}")
    public void down(HttpServletResponse response, @PathVariable("id") int id, @PathVariable("flag") String flag){
        User user = userMapper.selectById(id);
        if(!user.getResume().equals("") && !user.getResume().equals(null)){
            String basepath = System.getProperty("user.dir")+"\\src\\main\\resources\\filess\\resume\\";
            List<String> s = FileUtil.listFileNames(basepath);
            String filename = s.stream().filter(name->name.contains(id+"-"+flag)).findAny().orElse("");
            try {
                if(StringUtils.isNotEmpty(filename)){

                    response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
                    response.setContentType("application/octet-stream");
                    byte[] bytes = FileUtil.readBytes(basepath + filename);
                    OutputStream out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                    out.close();
                }else{
                    System.out.println("文件找不到！！");
                }
            }catch(Exception e){
                System.out.println("文件下载失败！！");
            }
        }else{
            System.out.println("文件不存在！！");
        }
    }
    @GetMapping("/del/{id}/{flag}")
    public Result<?> deleteFile(@PathVariable("id") int id, @PathVariable("flag")String flag) {
        User user = userMapper.selectById(id);
        if(!user.getResume().equals("") && !user.getResume().equals(null)){
            String basepath = System.getProperty("user.dir")+"\\src\\main\\resources\\filess\\resume\\";
            List<String> s = FileUtil.listFileNames(basepath);
            String filename = s.stream().filter(name->name.contains(user.getId()+"-"+flag)).findAny().orElse("");
            try {
                if(StringUtils.isNotEmpty(filename)){
                    File file = new File(filename);
                    if (file.isFile() && file.exists()) {
                        file.delete();
                        System.out.println("删除单个文件" + filename + "成功！");
                        return Result.success();
                    } else {
                        System.out.println("删除单个文件" + filename + "失败！");
                        return Result.error("-1","删除失败");
                    }
                }else{
                    return Result.error("-1","文件不存在");
                }
            }catch(Exception e){
                System.out.println("数据异常！！");
                return Result.error("-1","数据异常！！");
            }
        }
        return Result.error("-1","文件不存在");

    }
    @GetMapping("/d")
    public void down(HttpServletResponse response){
            String basepath = System.getProperty("user.dir")+"\\src\\main\\resources\\filess\\resume\\";
            List<String> s = FileUtil.listFileNames(basepath);
            String filename = s.stream().filter(name->name.contains("resume")).findAny().orElse("");
            try {
                if(StringUtils.isNotEmpty(filename)){
                    response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
                    response.setContentType("application/octet-stream");
                    byte[] bytes = FileUtil.readBytes(basepath + filename);
                    OutputStream out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                    out.close();
                }else{
                    System.out.println("文件找不到！！");
                }
            }catch(Exception e){
                System.out.println("文件下载失败！！");
            }
    }
    @PostMapping("/dd/{id}")
    public Result<?> ss(@PathVariable int id){
        User user = userMapper.selectById(id);
        if (user!=null){
            user.setResume("");
            userMapper.updateById(user);
        }
        return Result.success();
    }
}
