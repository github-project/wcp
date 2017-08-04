package mail;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class MailTest extends Thread{

	public static void main(String[] args) throws EmailException, MalformedURLException {
		
		// 创建Mail对象
//				Email mail = new SimpleEmail();
//				
//				// 设置发送邮件的主机服务器
//				mail.setHostName("smtp.126.com");
//				
//				// 设置发送邮件的端口号
//				mail.setSmtpPort(25);
//				
//				// 设置发送者的邮箱地址和密码
//				mail.setAuthentication("zyf_java@126.com", "zyf123");
//				
//				// 设置安全连接
//				mail.setSSLOnConnect(true);
//				
//				
//				//设置发送者的邮件
//				mail.setFrom("zyf_java@126.com");
//				
//				// 设置主题
//				mail.setSubject("test1");
//				
//				// 设置邮件内容
//				mail.setMsg("zhangyfnihao");
//				
//				// 设置接收者邮件
//				mail.addTo("13262678685@163.com");
//				
//				// 发送邮件
//				mail.send();
		
		// 可以发送图片和html片段的Email对象
//				HtmlEmail mail = new HtmlEmail();
//				// 设置发送邮件的主机服务器
//				mail.setHostName("smtp.126.com");
//				// 设置发送邮件的端口号
//				mail.setSmtpPort(25);
//				// 设置发送者的邮箱地址和密码
//				mail.setAuthentication("zyf_java@126.com", "zyf123");
//				// 设置安全连接
//				mail.setSSLOnConnect(true);
//				// 设置发送者的邮件
//				mail.setFrom("zyf_java@126.com");
//				// 设置主题
//				mail.setSubject("test html & Picture");
//				// 设置图片的网络路径
//				URL url = new URL("http://img13.360buyimg.com/n1/jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg");
//				String cid = mail.embed(url, "京东手机图片");
//				// 处理乱码问题 
//				mail.setCharset("utf-8");
//				// 将图片路径添加到邮件内容中
//				mail.setHtmlMsg("<html>京东图片<img src=\"cid:"+cid+"\"></html>");
//				// 设置邮件内容
//				mail.setTextMsg("你的邮件不支持html格式的邮件");
//				// 设置接收者邮件
//				mail.addTo("13262678685@163.com");
//				// 发送邮件
//				mail.send();
			
			ThreadTest thread = new ThreadTest();
			
			thread.start();

}



}
