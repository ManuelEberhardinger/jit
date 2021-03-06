/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * Class represents helper mehtods.
 */
public class FileUtils {

    // Deletes a complete directory, with all folders and files in it.
    public static boolean deleteFolder(File path) throws FileNotFoundException {
        if (!path.exists()) {
            throw new FileNotFoundException(path.getAbsolutePath());
        }
        boolean ret = true;
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                ret = ret && FileUtils.deleteFolder(f);
            }
        }
        return ret && path.delete();
    }
    
    // Serializes the staging tree with all added files.
    public static void writeStaging(final Directory tree) throws IOException {
        File file = new File("./.jit/staging/staging.ser");
        file.delete();
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        out.writeObject(tree);
        out.close();
    }

    // Deserializes the saved staging tree and returns it.
    public static Directory readStaging(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream("./.jit/staging/staging.ser")));
        
        Directory obj = (Directory) in.readObject();
        
        in.close();
        
        return obj;

    }

    // Hash the object with the example algorithm of GRIPS.
    public static String hashObject(byte[] content) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digested = md.digest(content);

        StringBuilder s = new StringBuilder();
        for (byte b : digested) {
            int value = b & 0xFF; // & 0xFF to treat byte as "unsigned"
            s.append(Integer.toHexString(value & 0x0F));
            s.append(Integer.toHexString(value >>> 4));
        }
        return s.toString();
    }
}
