package ink.songsong.mall.controller;/*
 *
 * @ClassName MemberController
 * @Author shensongpeng
 * @Date 2021/6/17 :12:26
 * @Version 1.0
 * */


import ink.songsong.mall.common.api.CommonResult;
import ink.songsong.mall.common.exception.BusinessException;
import ink.songsong.mall.domain.Register;
import ink.songsong.mall.domain.UmsMember;
import ink.songsong.mall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sso")
public class MemberController extends BaseController{

    @Autowired
    private MemberService memberService;

    @PostMapping("/getOtpCode")
    public CommonResult getOptCOde(@RequestParam String telphone){
        String optCode =memberService.getOtpCode(telphone);
        return CommonResult.success(optCode);
    }

    @PostMapping("/registe")
    public CommonResult regite(@Validated @RequestBody Register register) throws BusinessException {
        int result = memberService.regite(register);
        if (result > 0 )return CommonResult.success(null);
        return CommonResult.failed();
    }
    @PostMapping("/login")
    public CommonResult login(@RequestParam String username, @RequestParam  String password) throws BusinessException {
        UmsMember umsMember = memberService.login(username, password);
        if (umsMember != null){
            getHttpSeeion().setAttribute("member",umsMember);
            Object member = getHttpSeeion().getAttribute("member");
            return CommonResult.success(username+"登陆成功");
        }
        return CommonResult.failed();
    }
}
