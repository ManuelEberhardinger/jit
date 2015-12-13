/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author manueleberhardinger
 */
public class Commit implements IType, Serializable {

    // Represents commit message
    private String _name;
    
    public Commit(String name) {
        if(name == null)
            throw new NullPointerException("name");
        
        _name = name;
    }
    
    @Override
    public String getType() {
        return "Commit";
    }

    @Override
    public String getHash() throws NoSuchAlgorithmException {
        return FileUtils.hashObject(this.toString().getBytes());
    }

    @Override
    public String getName() {
        return _name;
    }
    
}
