/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class PassEnc {
    private static MessageDigest md;
    static StringBuffer sb=null;
   public static String passen(String pass){
    try {
        md = MessageDigest.getInstance("MD5");
        byte[] passBytes = pass.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
         sb= new StringBuffer();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        
    } catch (Exception ex) { }
        return sb.toString();
 }
   
    public static void main(String[] args) {
        String pass="niit";
        System.out.println("new password: "+passen(pass));
        
    }
}
