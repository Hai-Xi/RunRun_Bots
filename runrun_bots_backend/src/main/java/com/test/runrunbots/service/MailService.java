package com.test.runrunbots.service;


import jakarta.mail.MessagingException;

import java.util.Map;

public interface MailService {
    
    /**  
     * 发送简单文本邮件  
     *   
     * @param to 收件人  
     * @param subject 主题  
     * @param content 内容  
     */  
    void sendSimpleMail(String to, String subject, String content);  
    
    /**  
     * 发送HTML格式邮件  
     *   
     * @param to 收件人  
     * @param subject 主题  
     * @param content HTML内容  
     * @throws MessagingException 消息异常  
     */  
    void sendHtmlMail(String to, String subject, String content) throws MessagingException;
    
    /**  
     * 发送带附件的邮件  
     *   
     * @param to 收件人  
     * @param subject 主题  
     * @param content 内容  
     * @param filePath 附件路径  
     * @throws MessagingException 消息异常  
     */  
    void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException;

    /**
     * 发送模板邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param template 模板名称
     * @param variables 模板变量
     * @throws MessagingException 消息异常
     */
    void sendTemplateMail(String to, String subject, String template, Map<String, Object> variables) throws MessagingException;

}  