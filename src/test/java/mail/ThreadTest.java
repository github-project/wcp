package mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class ThreadTest extends Thread{

	
	@Override
	public void run() {
		try {
			
			/**
			 * 3.3.1.	发送简单邮件代码实现
			 * 
			 */
			// 创建Mail对象
//			Email mail = new SimpleEmail();
//			
//			// 设置发送邮件的主机服务器
//			mail.setHostName("smtp.sina.com");
//			
//			// 设置发送邮件的端口号
//			mail.setSmtpPort(25);
//			
//			// 设置发送者的邮箱地址和密码
//			mail.setAuthentication("qb_sh_test@sina.com", "2015qazwsx");
//			
//			// 设置安全连接
//			mail.setSSLOnConnect(true);
//			
//			//设置发送者的邮件
//			mail.setFrom("qb_sh_test@sina.com");
//			
//			// 设置主题
//			mail.setSubject("test");
//			
//			// 设置邮件内容
//			mail.setMsg("ceshi数据");
//			
//			// 设置接收者邮件
//			mail.addTo("qubo_323@163.com");
//			
//			// 发送邮件
//			mail.send();

			
			/**
			 * 发送html和图片
			 * 
			 */
//			// 可以发送图片和html片段的Email对象
//			HtmlEmail mail = new HtmlEmail();
//			// 设置发送邮件的主机服务器
//			mail.setHostName("smtp.sina.com");
//			// 设置发送邮件的端口号
//			mail.setSmtpPort(25);
//			// 设置发送者的邮箱地址和密码
//			mail.setAuthentication("qb_sh_test@sina.com", "2015qazwsx");
//			// 设置安全连接
//			mail.setSSLOnConnect(true);
//			// 设置发送者的邮件
//			mail.setFrom("qb_sh_test@sina.com");
//			// 设置主题
//			mail.setSubject("test html & Picture");
//			// 设置图片的网络路径
//			URL url = new URL("http://img13.360buyimg.com/n1/jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg");
//			String cid = mail.embed(url, "京东手机图片");
//			// 处理乱码问题 
//			mail.setCharset("utf-8");
//			// 将图片路径添加到邮件内容中
//			mail.setHtmlMsg("<html>京东图片<img src=\"cid:"+cid+"\"></html>");
//			// 设置邮件内容
//			mail.setTextMsg("你的邮件不支持html格式的邮件");
//			// 设置接收者邮件
//			mail.addTo("qubo_323@163.com");
//			// 发送邮件
//			mail.send();

			/**
			 * 
			 * 发送超大附件
			 */
			
			EmailAttachment attachment = new EmailAttachment();
			// 附件内容 path是磁盘本地文件路径和名称
			attachment.setPath("d:/2.pdf");
			// 将网络中的文件作为附件
			//attachment.setURL(new URL("http://img13.360buyimg.com/n1/jfs/t2302/16/135479564/94882/c76da045/55f0e877N3c24faa3.jpg"));
			// 表示当前设置的是附件
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("附件描述");
			attachment.setName("附件名称.pdf");

			// 创建可以发送附件的Email对象
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName("smtp.126.com");
			email.setSmtpPort(25);
			// 设置发送者的邮箱地址和密码
			email.setAuthentication("zyf_java@126.com", "zyf123");
			// 设置安全连接
			email.setSSLOnConnect(true);
			// 设置发送者的邮件
			email.setFrom("zyf_java@126.com");
  //email.addTo("zhangyf11@asiainfo-sec.com");
			
			//String[] str=new String[]{"a","b"};
			String[] str1=new String[]{"13262678685@163.com","zhangyf11@asiainfo-sec.com","zyf_java@126.com"};
			
			email.addTo(str1);
			email.setSubject("测试附件邮件");
			email.setMsg("测试带有附件的邮件------这时邮件的正文内容！！！");
			// 添加附件
			email.attach(attachment);
			// 发送邮件
			System.out.println("邮件发送开始");
			
			email.send();
   
			System.out.println("邮件发送成功");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
