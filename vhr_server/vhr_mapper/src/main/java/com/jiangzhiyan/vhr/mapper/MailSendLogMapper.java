package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.model.MailSendLog;

public interface MailSendLogMapper {
    int insert(MailSendLog record);

    int insertSelective(MailSendLog record);
}