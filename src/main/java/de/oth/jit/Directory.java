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
 * @author manueleberhardinger
 */
public class Directory implements IType, Serializable {

    private List<IType> _childern = new ArrayList<>();
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
        if (_childern.remove(node)) {
            return true;
        } else {
            return false;
        }
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

    public List<String> getAllChildernStrings() throws NoSuchAlgorithmException, IOException {
        List<String> allChilderns = _childern.stream()
                .map(IType::getFullString)
                .collect(Collectors.toList());
        return allChilderns;
    }
    
    public List<String> getAllChildernNames() {
        List<String> allNames = _childern.stream()
                                         .map(IType::getName)
                                         .collect(Collectors.toList());
        return allNames;
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
