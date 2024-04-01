package com.example.demo.pojo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class News {
    private int id;
    private String content;
    private String title;
    private Date date = new Date();

    @TableField(exist = false)
    private long timestamp = date.getTime();

}
