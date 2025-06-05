package com.wangjingran.mall.order.malluser.entity;
import com.wangjingran.mallcommon.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private String email;
    private Integer status;//0:禁用 1.启用
}
