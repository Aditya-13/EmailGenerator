package EmailApp;

import java.util.Date;
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
    private String email;
    private String emailPassword;

    public SendEmail(String email) {
        this.email = email;
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.imap.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "***************@*****.com";
        final String password = "*********";
        this.emailPassword = setUpdatePassword();
        try{
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress("drunkenmonkey657@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.email,false));
            msg.setSubject("HELLO, THIS IS THE NEW PASSWORD FOR YOUR COMPANY EMAIL.");
            msg.setText("THIS IS YOUR PASSWORD:\n\n\n" + emailPassword + " \nYOUR MAILBOX CAPACITY IS 100GB");
            msg.setSentDate(new Date());
            Transport.send(msg);
            System.out.println("MESSAGE SENT.");
        }catch (MessagingException e){
            System.out.println("ERROR, CAUSE: " + e);
        }
    }

    public String setUpdatePassword(){
        String pwd = "QWERTYUIOPASDFGHJKLZXCVBNM123456789!@#$%^";
        char[] password = new char[12];
        for(int i = 0; i < 12; i++) {
            int val = (int) (Math.random() * pwd.length());
            password[i] = pwd.charAt(val);
        }
        return new String(password);
    }

    public String getUpdatePassword() {
        return emailPassword;
    }
}