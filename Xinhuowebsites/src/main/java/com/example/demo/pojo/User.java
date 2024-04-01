package com.example.demo.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private int id;
    private String username;
    private String password;
    private String contact;
    private String level;
    private boolean isSick;


}
