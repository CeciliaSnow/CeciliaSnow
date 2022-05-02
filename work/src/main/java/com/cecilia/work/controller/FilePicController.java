package com.cecilia.work.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.cecilia.work.handle.Result;
import com.cecilia.work.mapper.UserMapper;
import com.cecilia.work.mapper.WorkMapper;
import com.cecilia.work.pojo.User;
import com.cecilia.work.pojo.Work;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/pic")
public class FilePicController {
    @Value("${server.port}")
    private String port;
    private static final String ip="http://localhost";
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/upload/{id}")
    public Result<?> upload(MultipartFile file, @PathVariable("id") int id) throws IOException {
        String filename = file.getOriginalFilename();
        String path =System.getProperty("user.dir")+"\\src\\main\\resources\\filess\\pic\\";
        String flag = IdUtil.fastSimpleUUID();
        //        地址加文件名路径
        String path2 = path+id+"-"+flag+"_"+filename;
//        写入
        FileUtil.writeBytes(file.getBytes(),path2);
        User user = userMapper.selectById(id);
        List<String> s = FileUtil.listFileNames(path);
//        地址
        user.setPic(ip+":"+port+"/pic/down/"+id+"/"+flag);
        userMapper.updateById(user);
        return Result.success(ip+":"+port+"/pic/down/"+id+"/"+flag);
    }
    @RequestMapping("/down/{id}/{flag}")
    public void down(HttpServletResponse response, @PathVariable("id") int id,@PathVariable("flag")String flag){
        User user = userMapper.selectById(id);
        if(!user.getPic().equals("") && !user.getPic().equals(null)){
            String basepath = System.getProperty("user.dir")+"\\src\\main\\resources\\filess\\pic\\";
            List<String> s = FileUtil.listFileNames(basepath);
            String filename = s.stream().filter(name->name.contains(user.getId()+"-"+flag)).findAny().orElse("");
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

                }
            }catch(Exception e){
                System.out.println("文件下载失败！！");
            }
        }else{}
    }
}
