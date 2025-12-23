package com.finance.financesystembackend.supplier.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("supplier")
public class Supplier {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;
    private String name;
    private String contact;
    private String phone;
    private String address;
    private Integer status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
