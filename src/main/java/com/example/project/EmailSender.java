package com.example.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    static Logger logger = LoggerFactory.getLogger( EmailSender.class );

    private EmailSender() {
        throw new IllegalStateException( "Utility class" );
    }

    public static void sendEmail(String subject, String[] toList, String messageBody, boolean asHtml) {

        Properties props = new Properties();
        props.put( "mail.smtp.host", "smtp.mailtrap.io" );
        props.put( "mail.smtp.port", "2525" );
        props.put( "mail.smtp.auth", "true" );

        String username = "YOUR MAIL USERNAME";
        String password = "YOUR MAIL PASSWORD";

        Session session = Session.getInstance( props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication( username, password );
                    }
                } );
        try {

            for (int index = 0; index < toList.length; index++) {

                Message message = new MimeMessage( session );
                message.setFrom( new InternetAddress( "spammer@spammer.com" ) );
                message.setRecipients( Message.RecipientType.TO,
                        InternetAddress.parse( toList[index] ) );
                message.setSubject( subject );


                if (asHtml) {
                    message.setContent( messageBody, "text/html; charset=utf-8" );
                } else {
                    message.setText( messageBody );
                }
                Transport.send( message );
                logger.info( "Done" );
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
