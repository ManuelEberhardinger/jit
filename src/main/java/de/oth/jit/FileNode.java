/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author manueleberhardinger
 */
public class FileNode implements IType, Serializable {
    
    private String _name;
    
    public FileNode(String name) {
        if(name == null)
            throw new NullPointerException("name");
        
        _name = name;
    }

    @Override
    public String getType() {
        return "File";
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
