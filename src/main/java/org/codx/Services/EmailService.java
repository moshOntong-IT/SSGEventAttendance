package org.codx.Services;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailService {

    private  String userMaill;
    private String passMail;
    private Session session;

    public EmailService(String userMaill, String passMail) {
        this.userMaill = userMaill;
        this.passMail = passMail;

        initMailService();
    }

    private void initMailService(){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp-relay.sendinblue.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userMaill, passMail);
                    }
                });
    }

    public void sendMessage(String receiver,String name,String qrFilePath){
        try {
            String htmlContent = "<em>Greetings!, </em>"+
                    "<strong>"+name+"</strong>";
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userMaill));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiver,false)
            );
            message.setSubject("Attendance Qr Code");

            BodyPart bodyPart1 = new MimeBodyPart();
            bodyPart1.setContent(htmlContent,"text/html");


            MimeBodyPart bodyPart2 = new MimeBodyPart();
            DataSource source = new FileDataSource(qrFilePath);
            bodyPart2.setDataHandler(new DataHandler(source));
            bodyPart2.setFileName(name+"jpg");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart1);
            multipart.addBodyPart(bodyPart2);


            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            System.out.println("[ERROR]: Sending mail failed");
            e.printStackTrace();

        }
    }

    public String getUserMaill() {
        return userMaill;
    }

    public void setUserMaill(String userMaill) {
        this.userMaill = userMaill;
    }

    public String getPassMail() {
        return passMail;
    }

    public void setPassMail(String passMail) {
        this.passMail = passMail;
    }
}
