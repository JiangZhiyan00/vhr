package com.jiangzhiyan.mail_server.receiver;

import com.jiangzhiyan.vhr.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @author JiangZhiyan
 */
@Component
public class MailReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 员工入职成功,发送邮件
     */
    @RabbitListener(queues = "vhr.mail.welcome")
    public void handler(Employee employee){
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(msg);
        try {
            messageHelper.setTo(employee.getEmail());
            messageHelper.setFrom(mailProperties.getUsername());
            //邮件主题
            messageHelper.setSubject("欢迎新员工入职");
            messageHelper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition());
            context.setVariable("jobLevelName",employee.getJobLevel());
            context.setVariable("departmentName",employee.getDepartment());
            String mail = templateEngine.process("mail", context);
            messageHelper.setText(mail,true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
            //邮件发送失败,打印日志
            LOGGER.error(new Date() +"-邮件发送失败:"+e.getMessage());
        }
    }
}
