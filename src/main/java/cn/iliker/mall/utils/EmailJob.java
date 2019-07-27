package cn.iliker.mall.utils;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

public class EmailJob {
    private JavaMailSenderImpl mailSender;
    private SimpleMailMessage mailMessage;
    private FreeMarkerConfigurer freeMarkerConfigurer;
    private URL imgPath;


    public void setImgPath(URL imgPath) {
        this.imgPath = imgPath;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }


    public void setMailMessage(SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }


    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    // 发送简单文本内容邮件
    public void sendSimpleMail(String content, String email) {
        this.mailMessage.setFrom(this.mailMessage.getFrom());
        this.mailMessage.setTo(email);// 邮件接收者  
        this.mailMessage.setText(content);// 邮件内容  
        this.mailSender.send(mailMessage);// 发送  
    }

    // 发送HTML内容邮件
    public void sendHtmlMail(String uName, String validateCode, long outDate, String toEmail) throws RuntimeException {
        String html = "亲爱的爱芬儿同事：" + uName + "\n 您好！感谢您的信任与支持！您需要通过下面的链接设置您的新密码链接,\nhttp://localhost/MallService/findPwd.do?uname=" + uName + "&validateCode=" + validateCode + "&tamp=" + outDate + "\n如果链接不能打开或打开出错请复制完整链接地址到浏览器打开！";
        try {
            mailMessage.setTo(toEmail);
            mailMessage.setText(html);
            mailMessage.setSentDate(new Date());
            mailMessage.setSubject("找回密码");
            this.mailSender.send(mailMessage);// 发送
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 发送邮件内容采用模板
    public void sendTemplateMail(String uName, String validateCode, long outDate, String toEmail) throws Exception {
        try {
            MimeMessage mailMsg = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMsg, true, "UTF-8");
            messageHelper.setTo(toEmail);// 接收邮箱
            messageHelper.setFrom(this.mailMessage.getFrom());// 发送邮箱
            messageHelper.setSentDate(new Date());// 发送时间
            messageHelper.setSubject("找回密码");// 邮件标题
            // true 表示启动HTML格式的邮件
            // 添加邮件附件
            FileSystemResource rarfile = new FileSystemResource(new File(imgPath.getPath()));
            messageHelper.addInline("file", rarfile);
            messageHelper.setText(this.getMailHtml(uName, validateCode, outDate), true);// 邮件内容
            this.mailSender.send(mailMsg);// 发送
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取模板并将内容输出到模板
     */
    private String getMailHtml(String uName, String validateCode, long outDate) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("userName", uName);
            map.put("validateCode", validateCode);
            map.put("outDate", outDate + "");
            // 装载模板
            Template tpl = this.freeMarkerConfigurer.getConfiguration()
                    .getTemplate("forgetPassword.html");
            // 加入map到模板中 输出对应变量
            return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 发送邮件内容采用模板
    public void sendEableTemplateMail(int id, String storeName, String validateCode, String toEmail) throws Exception {
        try {
            MimeMessage mailMsg = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMsg, true, "UTF-8");
            messageHelper.setTo(toEmail);// 接收邮箱
            messageHelper.setFrom(this.mailMessage.getFrom());// 发送邮箱
            messageHelper.setSentDate(new Date());// 发送时间
            messageHelper.setSubject("激活邮箱账户");// 邮件标题
            // true 表示启动HTML格式的邮件
            messageHelper.setText(this.getEnableMailHtml(id, storeName, validateCode), true);// 邮件内容
            this.mailSender.send(mailMsg);// 发送
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取模板并将内容输出到模板
     */
    private String getEnableMailHtml(int id, String storeName, String validateCode) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("id", id + "");
            map.put("storeName", storeName);
            map.put("validateCode", validateCode);
            // 装载模板
            Template tpl = this.freeMarkerConfigurer.getConfiguration()
                    .getTemplate("enableEmail.html");
            // 加入map到模板中 输出对应变量
            return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    // 发送邮件内容采用模板  
    public void sendTemplateMail(String uName, String toEmail) throws Exception {
        try {
            MimeMessage mailMsg = this.mailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMsg,
                    true, "UTF-8");
            messageHelper.setTo(toEmail);// 接收邮箱
            messageHelper.setFrom(this.mailMessage.getFrom());// 发送邮箱
            messageHelper.setSentDate(new Date());// 发送时间
            messageHelper.setSubject(this.mailMessage.getSubject());// 邮件标题
            // true 表示启动HTML格式的邮件
            messageHelper.setText(this.getMailText(uName), true);// 邮件内容
            // 添加邮件附件
            FileSystemResource rarfile = new FileSystemResource(new File(imgPath.getPath()));
            messageHelper.addInline("file", rarfile);
            this.mailSender.send(mailMsg);// 发送
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取模板并将内容输出到模板
     */
    private String getMailText(String uName) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("userName", uName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            map.put("sendTime", sdf.format(new Date()));
            // 装载模板
            Template tpl = this.freeMarkerConfigurer.getConfiguration()
                    .getTemplate("mailMsg.ftl");
            // 加入map到模板中 输出对应变量
            return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, map);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
