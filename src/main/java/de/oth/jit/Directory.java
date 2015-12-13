/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *
 * @author manueleberhardinger
 */
public class Directory implements IType, Serializable {

    private List<IType> _childern = new ArrayList<>();
    private String _name;

    public Directory(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        _name = name;
    }

    @Override
    public String getType() {
        return "Directory";
    }

    @Override
    public String getHash() throws NoSuchAlgorithmException {
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

    public void remove(IType node) {
        _childern.remove(node);
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

}
