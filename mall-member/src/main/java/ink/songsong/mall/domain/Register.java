package ink.songsong.mall.domain;/*
 *
 * @ClassName Register
 * @Author shensongpeng
 * @Date 2021/6/18 :17:24
 * @Version 1.0
 * */

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Register {
    @NotBlank(message = "电话号码不能为空")
    @Length(max = 11,min = 11,message = "电话号必须是11字符")
    @Pattern(regexp = "^1[3|4|5|8][0-9]\\d{8}$",message = "电话号码格式不正确")
    private String phone;

    @NotBlank(message = "动态校验码不能为空")
    @Length(min = 6,max = 6,message = "验证码必须是6字符")
    private String otpCode;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 4, max = 20,message = "用户名长度必须在4-20字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 20,message = "密码长度必须在8-20字符之间")
    private String password;
}
