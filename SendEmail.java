import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.sql.DataSource;

public class SendEmail {
    /**
     * @param <Session>
     * @param args
     */
    public static <Session> void main(String[] args) {
        final String username = "your_email@example.com";
        final char[] password = "your_email_password";
        String recipient = "hr@ignitershub.com";
        String subject = "Challenge 3 Completed";
        String body = "Your name: John Doe\nSemester: 3\nBranch: Computer Science\nRoll Number: 12345\n\nPlease find the attached image.\nBest regards, John Doe";
        String imagePath = "image.png";

        Properties props = new Properties();
        props.put("mail.smtp.host", "your_smtp_server.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            MimeBodyPart imagePart = new MimeBodyPart();
            DataSource source = new FileDataSource(imagePath);
            imagePart.setDataHandler(new DataHandler(source));
            imagePart.setFileName(imagePath);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Email sending failed.");
        }
    }
}
