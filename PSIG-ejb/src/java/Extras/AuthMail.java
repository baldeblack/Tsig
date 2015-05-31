package Extras;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class AuthMail extends Authenticator{

   public PasswordAuthentication getPasswordAuthentication()
       {
           return new PasswordAuthentication("tsig2015grupo15@gmail.com", "tsig123456");
       }
}

