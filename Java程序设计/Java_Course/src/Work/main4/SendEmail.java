package Work.main4;

//向服务器发送一条信息，实现java发送邮件

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    public static void main(String[] args) {
        String to="ykx001104@163.com";//收件人邮箱
        String title="来自CSG编写的自动邮件骚扰程序";//邮件主题
        String content="你爱我我爱你蜜雪冰城甜蜜蜜";//邮件内容
        boolean flag =true;

        //类Properties（Java.util.Properties），主要用于读取Java的配置文件，方便用户，让用户能够脱离程序本身去修改相关的变量设置。
        // Step1，创建Properties属性对象，并设置一些邮件的属性。创建键值对，类似于Python字典
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.163.com"); // 设置邮箱服务器，利用smtp协议发送到smtp服务器
        props.setProperty("mail.transport.protocol", "SMTP"); // 设置邮箱发送邮件所使用的协议
        props.setProperty("mail.smtp.auth", "true"); // 设置认证方式（smtp协议认证方式）
        props.put("mail.smtp.starttls.enable", "true"); //设置使用ssl连接

        //Step2，创建认证对象authenticator。其参数是邮件和账号及相应的授权码，以便认证
        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Hbcsg_speed@163.com", "JYRYQUYDQVFEIDRJ");
            }
        };

        //Step3，创建会话对象session。需要认证与发信息相关字典类型传参
        Session session = Session.getInstance(props, authenticator);

        //Step4，创建邮件消息对象，设置发送人、接收人、邮件主题、邮件内容
        MimeMessage mess = new MimeMessage(session);
        try {
            mess.setFrom(new InternetAddress("Hbcsg_speed@163.com")); // 设置邮件的发件人
            mess.setRecipients(Message.RecipientType.TO, to); // 设置收件人
            mess.setSubject(title); // 设置邮件标题
            mess.setContent(content, "text/html;charset=utf-8"); // 设置邮件内容和格式
            // Step5，发送邮件
            for(int i=0;i<50;i++) {
                Transport.send(mess);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            flag=false;
            System.out.println("发送邮件失败, 原因是:" + e.getMessage());
        }
        if(flag)
            System.out.println("Congratulations！发送邮件成功！接收人：" + to+"\n主题为："+title+"\n内容为："+content);
    }

}
