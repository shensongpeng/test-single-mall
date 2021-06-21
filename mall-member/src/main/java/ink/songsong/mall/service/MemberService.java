package ink.songsong.mall.service;

import ink.songsong.mall.common.exception.BusinessException;
import ink.songsong.mall.domain.Register;
import ink.songsong.mall.domain.UmsMember;

public interface MemberService {

    String getOtpCode(String telphone);

    public int regite(Register register) throws BusinessException;
    public UmsMember login(String username, String password) throws BusinessException;
}
