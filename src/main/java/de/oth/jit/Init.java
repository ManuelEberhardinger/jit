/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.*;

/**
 *
 * @author manueleberhardinger
 */
public class Init implements ICommand {

    private final File _file;
    private final String _name = "init";

    public Init(File path) {
        if (path == null) {
            throw new NullPointerException("path");
        }

        _file = path;
    }
    
    @Override
    public File getFile() {
        return _file;
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public boolean execute(String arg) throws IOException {
        if (!_file.isDirectory()) {
            System.out.println("Path points to no directory. Can not initialize repository…");
            return false;
        }

        String pathToFolder = _file.getCanonicalPath();

        File jitFolder = new File(pathToFolder + "/.jit");

        if (jitFolder.exists()) {
            System.out.println("Repository already exists.");
            FileUtils.deleteFolder(jitFolder);
            System.out.println("Repository will be re-initialized…");
        }

        File objects = new File(pathToFolder + "/.jit/objects");
        File staging = new File(pathToFolder + "/.jit/staging");

        if (objects.mkdirs() && staging.mkdirs()) {
            System.out.println("Repository has been successfully created.");
        }
        else {
            System.out.println("Failure initializing the repository.");
            return false;
        }
        
        return true;

    }

}
