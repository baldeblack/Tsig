package Extras;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EnvioMail {

    public  EnvioMail(){}

    public void EnvioMailto(String destinatario,String asunto, String mensaje){
        
        Properties properties = System.getProperties();
        // SET UP CORREO
        String  email_sistema = "tsig2015grupo15@gmail.com";
        String  nombre_sistema = "Inmobiliaria";
        String  password_sistema = "tsig123456";
        String  host_correo = "smtp.gmail.com";
        String  puerto_correo  = "465"; //465,587
        String  email_destinatario = destinatario;
        //String  email_asunto = "Inmobiliaria";
        //String  email_texto = "Consulta de inmueble.";
        
        // Propiedades Correo
        properties.put("mail.smtp.user", email_sistema);
        properties.put("mail.smtp.host", host_correo);
        properties.put("mail.smtp.port", puerto_correo);
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", puerto_correo);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        AuthMail auth = new AuthMail();
        Session session = Session.getInstance(properties, auth);
        session.setDebug(true);

        MimeMessage mensaje_correo = new MimeMessage(session);
        try {
                mensaje_correo.setText(mensaje);
                mensaje_correo.setSubject(asunto);
                mensaje_correo.setFrom(new InternetAddress(email_sistema));
                mensaje_correo.addRecipient(Message.RecipientType.TO, new InternetAddress(email_destinatario));

                Transport enviar = session.getTransport("smtps");
                enviar.connect(host_correo, 465, nombre_sistema, password_sistema);
                enviar.sendMessage(mensaje_correo, mensaje_correo.getAllRecipients());
                enviar.close();
        } catch (MessagingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
}