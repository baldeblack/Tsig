/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extras;

/**
 *
 * @author nacho
 */
public class TestMail {
    
    public static void main(String[] argv) {
        
        
        EnvioMail mail = new EnvioMail();
        
        mail.EnvioMailto("tsig2015grupo15@gmail.com", "TSIG PRUEBA", "Holaaaa");
    }
}
