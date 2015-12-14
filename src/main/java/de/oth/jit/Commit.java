/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author manueleberhardinger
 */
public class Commit implements IType, Serializable {

    // Represents commit message
    private String _name;
    
    private Path _path;
    
    private IType _directory;
    
    public Commit(String name, String path, IType directory) {
        if(name == null)
            throw new NullPointerException("name");
        if(path == null)
            throw new NullPointerException("path");
        if(directory == null)
            throw new NullPointerException("directory");
        
        _name = name;
        _path = Paths.get(path);
        _directory = directory;
    }
    
    @Override
    public String getType() {
        return "Commit";
    }

    @Override
    public String getHash() throws NoSuchAlgorithmException, IOException {
        return FileUtils.hashObject((this.toString() + _directory.getHash()).getBytes());
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public Path getPath() {
        return _path;
    }
    
}
