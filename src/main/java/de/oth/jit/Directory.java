/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Represents a directory or folder of the staging tree.
 * The directory is able to save other directories and files in his _childern field.
 */
public class Directory implements IType, Serializable {

    private final List<IType> _childern = new ArrayList<>();
    private String _name;
    private String _path;

    public Directory(String name, String path) {
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
    public String getPath() {
        return _path;
    }

    @Override
    public String getType() {
        return "Directory";
    }

    // Hashes the directory with all the full strings of the _childern of the directory.
    // So you get a unique hash.
    @Override
    public String getHash() throws NoSuchAlgorithmException, IOException {
        String allPaths = "";
        for (IType node : _childern) {
            allPaths += node.getFullString();
        }
        return FileUtils.hashObject(allPaths.getBytes());
    }

    @Override
    public String getName() {
        return _name;
    }

    // add a child to the directory. If the child already exists it will be replaced.
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

    // remove a child.
    public boolean remove(IType node) {
        if (_childern.remove(node)) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if a child with the given name exists.
    public boolean exists(String name) {
        if (_childern.stream().anyMatch((node) -> (node.getName().equals(name)))) {
            return true;
        }
        return false;
    }

    // Returns a child, that is a directory if it exists, otherwise it will be returned null.
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

    // get the next child by name. 
    public IType getNext(String name) {
        for (IType node : _childern) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    // get all full strings of the childern of the directory as a list.
    public List<String> getAllChildernStrings() throws NoSuchAlgorithmException, IOException {
        List<String> allChilderns = _childern.stream()
                .map(IType::getFullString)
                .collect(Collectors.toList());
        return allChilderns;
    }
    
    // get all names of the childern as a list.
    public List<String> getAllChildernNames() {
        List<String> allNames = _childern.stream()
                                         .map(IType::getName)
                                         .collect(Collectors.toList());
        return allNames;
    }

    // Returns a string with type, hash and name.
    @Override
    public String getFullString() {
        try {
            return getType() + " " + getHash() + " " + getName();
        } catch (Exception ex) {
            return null;
        }
    }
}
