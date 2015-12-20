/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 *
 * Represents a File for the staging tree. 
 */
public class FileNode implements IType, Serializable {

    private String _name;
    private String _path;

    public FileNode(String name, String path) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (path == null) {
            throw new NullPointerException("path");
        }

        _name = name;
        _path = path;
    }

    @Override
    public String getType() {
        return "File";
    }

    // Hashes the object with the method of FileUtils.
    // The hash is created with the content of the file so that the hash is unique.
    @Override
    public String getHash() throws NoSuchAlgorithmException, IOException {
        return FileUtils.hashObject(Files.readAllBytes(Paths.get(_path)));
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getPath() {
        return _path;
    }

    // Returns a full string, like the one that is saved in the object files.
    @Override
    public String getFullString() {
        try {
            return getType() + " " + getHash() + " " + getName();
        } catch (Exception ex) {
            return null;
        }
    }

}
