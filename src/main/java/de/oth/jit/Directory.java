/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manueleberhardinger
 */
public class Directory implements IType, Serializable {

    private List<IType> _childern = new ArrayList<>();
    private String _name;
    private Path _path;

    public Directory(String name, String path) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if(path == null)
            throw new NullPointerException("path");

        _name = name;
        _path = Paths.get(path);
    }
    
    @Override
    public Path getPath() {
        return _path;
    }

    @Override
    public String getType() {
        return "Directory";
    }

    @Override
    public String getHash() throws NoSuchAlgorithmException, IOException {
        String allPaths = "";
        for (IType node : _childern) {
            allPaths += node.getHash();
        }
        return FileUtils.hashObject(allPaths.getBytes());
    }

    @Override
    public String getName() {
        return _name;
    }

    public void add(IType node) {
        if (node.getType().equals("File")) {
            for (int i = 0; i < _childern.size(); i++) {
                if (_childern.get(i).getName().equals(node.getName())) {
                    _childern.remove(i);
                    i--;
                }
            }
        }
        _childern.add(node);
    }

    public boolean remove(IType node) {
        if(_childern.remove(node))
            return true;
        else return false;
    }

    public boolean exists(String name) {
        if (_childern.stream().anyMatch((node) -> (node.getName().equals(name)))) {
            return true;
        }
        return false;
    }

    public Directory getDirectory(String name) {
        for (IType node : _childern) {
            if (node.getName().equals(name)) {
                if (node.getType().equals("Directory")) {
                    return (Directory) node;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public IType getNext(String name) {
        for (IType node : _childern) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }
}
