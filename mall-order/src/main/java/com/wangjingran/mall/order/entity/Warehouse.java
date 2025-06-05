package com.wangjingran.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/*
 * 仓库实体类
 * */
@Data
@TableName("warehouse")
public class Warehouse {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
/*
* 仓库名称
* */
    private String name;
    /*
    * 仓库编码
    * */
    private String code;
    /*
    * 仓库地址
    * */
    private String address;
    /*
    * 省份/直辖市
    * */
    private String province;
    /*
    * 城市
    * */
    private String city;
    /*
    * 区/县
    * */
    private String district;
    /*
    * 详细地址
    * */
    private String detailAddress;
    /*
    * 经度
    * */
    private String longitude;
    /*
    * 纬度
    * */
    private String latitude;
    /*
    * 联系人
    * */
    private String contactName;
    /*
    * 联系人电话
    * */
    private String contactPhone;
    /*
    * 仓库状态
    * */
    private Integer status;
    /*
    * 创建时间
    * */
    private LocalDateTime createTime;
    /*
    * 更新时间
    * */
    private LocalDateTime updateTime;
}
