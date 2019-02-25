package com.huilianyi.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "atl_user")
public class User {

    @TableId(value = "id")
    private Long id;

    private String username;
    private String password;

    @TableField(exist = false)
    private List<String> roles;

}
