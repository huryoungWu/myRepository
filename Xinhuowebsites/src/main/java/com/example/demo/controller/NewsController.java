package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.Utils.R;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.pojo.News;
import com.example.demo.pojo.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/user/News")
public class NewsController {

    @Autowired
    private NewsMapper newsMapper;


    @RequestMapping(value="listAll/{start}/{pageSize}",method = {RequestMethod.GET,RequestMethod.POST})
    public R<?> ListAll(@PathVariable int start, @PathVariable int pageSize,String questionSort){
        Page<News> page=new Page<>(start,pageSize);//查询第start页，显示pageSize条数据

        QueryWrapper<News> wrapper= new QueryWrapper<>();
        //查询条件
        IPage<News> iPage = newsMapper.selectPage(page, wrapper);
        System.out.println("数据总条数："+iPage.getTotal());
        System.out.println("数据总页数："+iPage.getPages());
        System.out.println("当前的页数："+iPage.getCurrent());
        //获取该页数的数据
        List<News> records = iPage.getRecords();
        Show<News,Long> newsShow = new Show<>(records, iPage.getTotal());
        return R.ok(newsShow);
    }

    @GetMapping("/listByDate/{start}/{pageSize}")
    public R<?> listByDate(@PathVariable int start, @PathVariable int pageSize,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime begin, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        Page<News> page=new Page<>(start,pageSize);//查询第start页，显示pageSize条数据

        QueryWrapper<News> wrapper= new QueryWrapper<>();
        wrapper.between("date",Date.from(begin.atZone(ZoneOffset.ofHours(8)).toInstant()),Date.from(end.atZone(ZoneOffset.ofHours(8)).toInstant()));
        IPage<News> iPage = this.newsMapper.selectPage(page, wrapper);
        System.out.println("数据总条数："+iPage.getTotal());
        List<News> records = iPage.getRecords();
        Show<News,Long> newsShow = new Show<>(records, iPage.getTotal());
        return R.ok(newsShow);
    }

    @PostMapping("/addNews")
    public R<?> addNews(News News) {
        int i = newsMapper.insert(News);
        if (i == 1) {
            return R.ok(News);
        } else {
            return R.error().message("发生错误");
        }
    }
    @PutMapping("/updateNews")
    public R<?> updateNews(News News) {
        int i = newsMapper.updateById(News);
        if (i == 1) {
            return R.ok();
        } else {
            return R.error().message("出现错误");
        }

    }
    @RequestMapping(value="deleteNews/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public R<?> deleteNews(@PathVariable int id) {
        int i = newsMapper.deleteById(id);
        if (i == 1) {
            return R.ok();
        } else {
            return R.error().message("删除出现错误");
        }

    }

    @PostMapping("/upload")
    public R<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        // 保存文件
        Path path = Paths.get("C:\\Users\\吴赫洋\\IdeaProjects\\jdbc03\\src\\main\\resources\\uploaded" + file.getOriginalFilename());
        Files.write(path, file.getBytes());
        return R.ok(path + "文件上传成功");
    }

    /**
     * @param path     指想要下载的文件的路径
     * @param response
     * @功能描述 下载文件:将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存
     */
    @RequestMapping("/download")
    public R<?> download(String path, HttpServletResponse response) throws IOException {
        // 读到流中
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        String filename = new File(path).getName();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
        return R.ok(path);
    }


}
