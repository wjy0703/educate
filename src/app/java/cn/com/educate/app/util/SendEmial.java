package cn.com.educate.app.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SendEmial {
	/**
	 * 发送邮件
	 * @param to_email 接受人邮件地址
	 * @param title 邮件标题
	 * @param context 邮件内容
	 * @param filePaths 邮件附件
	 * recipients 抄送邮箱
	 */
	public void sendEmile(String to_email,String title,String context,String[] filePaths,String[] recipients) {			
		Map bb=readEmailXML();
		System.out.println("============= 邮件参数==============--接受人邮件地址==" + to_email+ "--邮件标题==" + title+ "--邮件内容==" + context);
    	String serverStr=(String)bb.get("serverStr");// 发送邮件服务器
    	String userName=(String)bb.get("userName");// 邮件服务器登录用户名
    	String userPwd=(String)bb.get("userPwd");// 邮件服务器登录密码
    	System.out.println("serverStr=="+serverStr+";;userName===="+userName+";;userPwd====" + userPwd);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		String sENDTIME = formatter.format(new Date()); //申请时间
//			String smtphost = serverStr; // 发送邮件服务器
//			String user = userName; // 邮件服务器登录用户名
//			String password = userPwd; // 邮件服务器登录密码
		String from = (String)bb.get("mailAddress"); // 发送人邮件地址
		String to = to_email; // 接受人邮件地址
		String subject =title; // 邮件标题
		String body = context; // 邮件内容
		//发送邮件
		try {			
			Properties props = new Properties();
			props.put("mail.smtp.host", serverStr);
			props.put("mail.smtp.auth", "true");
//			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
//			props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
//			props.setProperty("mail.smtp.port", "465"); 
//			props.setProperty("mail.smtp.socketFactory.port", "465"); 
			Session ssn = Session.getInstance(props, null);
			MimeMessage message = new MimeMessage(ssn);
			InternetAddress fromAddress;
			
			fromAddress = new InternetAddress(from);
			
			message.setFrom(fromAddress);
			InternetAddress toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			// 密件收件人 
			//Address addressBCC = new InternetAddress("","自动发送的测试员"); 

			if(null != recipients && recipients.length > 0){ 
				Address[] ccAdresses = new InternetAddress[recipients.length]; 
				for (int i=0; i<recipients.length; i++){ 
			            ccAdresses[i] = new InternetAddress(recipients[i]); 
			        } 
				// 将抄送者信息设置到邮件信息中 
			message.setRecipients(Message.RecipientType.CC, ccAdresses); 
				} 
			
			// message.setText(body);
			message.setSentDate(new Date());
			//message.setText(body);
			// 创建
			// Mimemultipart，这是包含多个附件是必须创建的。如果只有一个内容，没有附件，
			// 可以直接用message.setText(String
			// str)
			// 去写信的内容，比较方便。附件等于是要创建多个内容，往下看更清晰。
			
			MimeMultipart multi = new MimeMultipart();
			// 创建
			// BodyPart，主要作用是将以后创建的n个内容加入MimeMultipart.也就是可以发n个
			// 附件。我这里有2个BodyPart.
			BodyPart textBodyPart = new MimeBodyPart(); // 第一个BodyPart.主要写一些一般
														// 的信件内容。
			textBodyPart.setText(body);
			multi.addBodyPart(textBodyPart);
			//String filenames[] = tEMAIL.getIS_ATTACHMENT().split(",");
			long files_length=0;//文件大小
			if(filePaths.length!=0){
				for (int i = 0; i < filePaths.length; i++) {
					MimeBodyPart messageBodyPart2 = new MimeBodyPart();
					// 选择出每一个附件名
					String filename = filePaths[i];
					//String displayname = filenames[i].split(",")[0];
					files_length+=new File(filename).length();
					// 得到数据源
					FileDataSource fds = new FileDataSource(filename);
					// 得到附件本身并至入BodyPart
					messageBodyPart2.setDataHandler(new DataHandler(fds));
					// 得到文件名同样至入BodyPart
					String name = filename.substring(filename.lastIndexOf(File.separator)+1
							, filename.length());
					messageBodyPart2.setFileName(MimeUtility.encodeWord(name,"utf-8","Q"));
					//messageBodyPart2.setFileName(displayname);
					//messageBodyPart2.setFileName(fds.getName());
					//messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
					multi.addBodyPart(messageBodyPart2);
				}
			}
			
			message.setContent(multi,"utf-8");
			
			// 设置信件头的发送日期
			message.setSentDate(new Date());
			message.saveChanges();
			Transport transport = ssn.getTransport("smtp");
			//smtp.sina.com.cn------liushuai0801------801801ls
			transport.connect(serverStr.trim(),userName.trim(),userPwd.trim());
			
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			System.out.println("=============发送邮件完成==============");
			//t_SENDEMAILDao.update_T_SENDEMAIL_ByWhere(" set SEND_FLAG='1',SEND_DATE=to_date('"+sENDTIME+"','yyyy-MM-dd hh24:mi:ss') where ID='" + id + "'");
			System.out.println("=============修改邮件表标记完成==============");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读邮件配置文件
	 * @return
	 */
	public Map readEmailXML(){
		try {
			java.net.URL paths=this.getClass().getResource("/Email.xml");		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			String luJings[]=paths.toString().split("/", 3);	
			Document document = builder.parse(new File(luJings[1]+"/"+luJings[2]));
			Element rootElement = document.getDocumentElement();			
			NodeList list = rootElement.getElementsByTagName("serverStr");//服务器名称
			Element element = (Element) list.item(0);
			String serverStr=element.getChildNodes().item(0).getNodeValue();
			NodeList list1 = rootElement.getElementsByTagName("userName");//用户名称
			Element element1 = (Element) list1.item(0);
			String userName=element1.getChildNodes().item(0).getNodeValue();
			NodeList list2 = rootElement.getElementsByTagName("userPwd");//用户密码
			Element element2 = (Element) list2.item(0);
			String userPwd=element2.getChildNodes().item(0).getNodeValue();
			NodeList list3 = rootElement.getElementsByTagName("mailAddress");//用户邮箱地址
			Element element3 = (Element) list3.item(0);
			String mailAddress=element3.getChildNodes().item(0).getNodeValue();
			Map map=new HashMap();
			map.put("serverStr", serverStr);
			map.put("mailAddress", mailAddress);
			map.put("userName", userName);
			map.put("userPwd", userPwd);
			return map;
		} catch (Exception e) {
			return null;
		}
    }
	/*
	 * @param to_user 接受人邮件地址
	 * @param title 邮件标题
	 * @param context 邮件内容
	 * @param filePaths 邮件附件
	 */
	public static void main(String[] args) {
		String to_email = "50055606@qq.com";
		String title = "邮件附件测试";
		String context = "邮件附件测试;邮件附件测试";
		String mouFilePath2 = "E:" + File.separator + "xhFile" + File.separator + "agaeeFile"
				+ File.separator + "zq2013-07-31" + File.separator + "非首期债权转让及受让协议-安建立-001(353751)2013-07-31.doc";
		System.out.println(mouFilePath2);
		String name = mouFilePath2.substring(mouFilePath2.lastIndexOf(File.separator)+1, mouFilePath2.lastIndexOf("."));
		System.out.println(name);
		String[] filePaths = {mouFilePath2};
		SendEmial se = new SendEmial();
		se.sendEmile(to_email, title, context, filePaths,null);
		
	}
}
