package com.finance.financesystembackend.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("customer") //
public class Customer {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;

    private String contact;

    private String phone;

    private String address;

    private Integer status;
}
