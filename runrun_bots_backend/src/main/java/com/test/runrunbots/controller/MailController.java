package com.test.runrunbots.controller;


import com.test.runrunbots.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;  

@RestController  
@RequestMapping("/mail")  
public class MailController {  
    
    @Autowired  
    private MailService mailService;

    @Value("${mail-username}")
    private String SendEmailAddress;

    @GetMapping("/simple")
    public String sendSimpleMail() {  
        mailService.sendSimpleMail(SendEmailAddress, "测试简单邮件", "这是一封简单邮件的内容");
        return "简单邮件发送成功！";  
    }  
    
    @GetMapping("/html")  
    public String sendHtmlMail() {  
        String content = "<html><body><h3>这是一封HTML邮件</h3><p style='color:red'>红色文字</p></body></html>";  
        try {  
            mailService.sendHtmlMail(SendEmailAddress, "测试HTML邮件", content);
            return "HTML邮件发送成功！";  
        } catch (MessagingException e) {
            e.printStackTrace();  
            return "HTML邮件发送失败！";  
        }  
    }  
    
    @GetMapping("/attachment")  
    public String sendAttachmentMail() {  
        try {  
            String filePath = "D:/temp/test.jpg"; // 替换为您自己的文件路径
            mailService.sendAttachmentMail(SendEmailAddress, "测试带附件邮件", "这是一封带附件的邮件", filePath);
            return "带附件邮件发送成功！";  
        } catch (MessagingException e) {  
            e.printStackTrace();  
            return "带附件邮件发送失败！";  
        }  
    }  
    
    @GetMapping("/template")  
    public String sendTemplateMail() {  
        try {  
            Map<String, Object> variables = new HashMap<>();  
            variables.put("username", "test");
            mailService.sendTemplateMail(SendEmailAddress, "欢迎注册", "mail/welcome", variables);
            return "模板邮件发送成功！";  
        } catch (MessagingException e) {  
            e.printStackTrace();  
            return "模板邮件发送失败！";  
        }  
    }  
}  