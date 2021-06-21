package ink.songsong.mall.service.impl;/*
 *
 * @ClassName MemberServiceImpl
 * @Author shensongpeng
 * @Date 2021/6/17 :20:31
 * @Version 1.0
 * */

import ink.songsong.mall.common.exception.BusinessException;
import ink.songsong.mall.config.properties.RedisKeyPrefixConfig;
import ink.songsong.mall.domain.Register;
import ink.songsong.mall.domain.UmsMember;
import ink.songsong.mall.domain.UmsMemberExample;
import ink.songsong.mall.mapper.UmsMemberMapper;
import ink.songsong.mall.service.MemberService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisKeyPrefixConfig redisKeyPrefixConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public String getOtpCode(String telphone) {
        //1 查询是否注册
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(telphone);
        List<UmsMember> umsMembers = umsMemberMapper.selectByExample(example);
        if (umsMembers.size() > 0){
            throw new BusinessException("用户已经注册!不能重复注册");
        }
        //2校验60s有没有再次发送
        if (redisTemplate.hasKey(redisKeyPrefixConfig.getPrefix().getOtpCode()+telphone)){
            throw new BusinessException("请60s后再试！");
        }
        //3 生产随机校验码
        Random random = new Random();
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stb.append(random.nextInt(10));
        }
        log.info("otpcode{}",stb.toString());
        redisTemplate.opsForValue().set(redisKeyPrefixConfig.getPrefix().getOtpCode()+telphone,stb.toString()
                ,redisKeyPrefixConfig.getExpire().getOtpCode(), TimeUnit.SECONDS);
        return stb.toString();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int regite(Register register) throws BusinessException {
        String otpCode = (String) redisTemplate.opsForValue()
                .get(redisKeyPrefixConfig.getPrefix().getOtpCode()+register.getPhone());
        if (StringUtils.isEmpty(otpCode) || !otpCode.equals(register.getOtpCode())) {
            throw new BusinessException("验证码不正确");
        }
        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(register,umsMember);
        umsMember.setStatus(1);
        umsMember.setMemberLevelId(4l);
        umsMember.setPassword(passwordEncoder.encode(register.getPassword()));
        return umsMemberMapper.insertSelective(umsMember);
    }
    public UmsMember login(String username ,String password) throws BusinessException {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username)
                .andStatusEqualTo(1);
        List<UmsMember> results = umsMemberMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(results)) throw new BusinessException("用户名或密码不正确");
        if (results.size() > 1) throw new BusinessException("用户名被注册多次，请联系客服");
        UmsMember member = results.get(0);
        if (!passwordEncoder.matches(password,member.getPassword())) {
            throw new BusinessException("用户名或密码不正确");
        }
        return member;
    }
}
