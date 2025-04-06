package com.test.runrunbots.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.core.io.FileSystemResource;  
import org.springframework.mail.SimpleMailMessage;  
import org.springframework.mail.javamail.JavaMailSender;  
import org.springframework.mail.javamail.MimeMessageHelper;  
import org.springframework.stereotype.Service;  


import java.io.File;
import java.util.Map;

@Service  
public class MailServiceImpl implements MailService {  
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired  
    private JavaMailSender mailSender;  
    
    @Value("${spring.mail.username}")  
    private String from;  
    
    @Override  
    public void sendSimpleMail(String to, String subject, String content) {  
        SimpleMailMessage message = new SimpleMailMessage();  
        message.setFrom(from);  
        message.setTo(to);  
        message.setSubject(subject);  
        message.setText(content);  
        
        try {  
            mailSender.send(message);  
            logger.info("简单邮件已发送。");  
        } catch (Exception e) {  
            logger.error("发送简单邮件时发生异常！", e);  
        }  
    }  
    
    @Override  
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        
        try {  
            // true表示需要创建一个multipart message  
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
            helper.setFrom(from);  
            helper.setTo(to);  
            helper.setSubject(subject);  
            helper.setText(content, true);  
            
            mailSender.send(message);  
            logger.info("HTML邮件已发送。");  
        } catch (MessagingException e) {  
            logger.error("发送HTML邮件时发生异常！", e);  
            throw e;  
        }  
    }  
    
    @Override  
    public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException {  
        MimeMessage message = mailSender.createMimeMessage();  
        
        try {  
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
            helper.setFrom(from);  
            helper.setTo(to);  
            helper.setSubject(subject);  
            helper.setText(content, true);  
            
            FileSystemResource file = new FileSystemResource(new File(filePath));  
            String fileName = file.getFilename();  
            helper.addAttachment(fileName, file);  
            
            mailSender.send(message);  
            logger.info("带附件的邮件已发送。");  
        } catch (MessagingException e) {  
            logger.error("发送带附件的邮件时发生异常！", e);  
            throw e;  
        }  
    }

    /**
     * 发送模板邮件
     *
     * @param to        收件人
     * @param subject   主题
     * @param template  模板名称
     * @param variables 模板变量
     * @throws MessagingException 消息异常
     */
    @Override
    public void sendTemplateMail(String to, String subject, String template, Map<String, Object> variables) throws MessagingException {

    }

}  