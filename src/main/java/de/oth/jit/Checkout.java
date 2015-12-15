/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author manueleberhardinger
 */
public class Checkout implements ICommand {

    private final File _file;
    private final String _name = "checkout";

    public Checkout(File path) {
        if (path == null) {
            throw new NullPointerException("path");
        }

        _file = path;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public File getFile() {
        return _file;
    }

    @Override
    public boolean execute(String arg) throws IOException, ClassNotFoundException {
        return true;
    }

}
