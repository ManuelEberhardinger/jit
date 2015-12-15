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

    private String _path;

    private IType _directory;

    public Commit(String name, String path, IType directory) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (path == null) {
            throw new NullPointerException("path");
        }
        if (directory == null) {
            throw new NullPointerException("directory");
        }

        _name = name;
        _path = path;
        _directory = directory;
    }

    @Override
    public String getType() {
        return "Commit";
    }

    @Override
    public String getHash() throws NoSuchAlgorithmException, IOException {
        return FileUtils.hashObject((this.getType() + _directory.getFullString() +  this.getName()).getBytes());
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getPath() {
        return _path;
    }

    @Override
    public String getFullString() {
        try {
            return getType() + " " + getHash() + " " + getName();
        } catch (Exception ex) {
            return null;
        }
    }
}
