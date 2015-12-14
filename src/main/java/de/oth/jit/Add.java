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
public class Add implements ICommand {

    private final File _file;
    private final String _name = "add";

    public Add(File path) {
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
    public boolean execute(String arg) throws IOException, ClassNotFoundException {
        if (arg.equals("") || arg == null || !new File("./" + arg).exists()) {
            System.out.println("Path points to no file or directory…");
            return false;
        }

        String path = "./.jit/staging/staging.ser";
        Directory tree;

        if (new File(path).exists()) {
            tree = FileUtils.readStaging(path);
        } else {
            tree = new Directory(".", ".");
        }

        String[] folders = arg.split("/");
        String pathToFile = ".";

        File tmpFile;
        Directory node = tree;

        for (int i = 1; i < folders.length; i++) {
            if(folders[i].equals(""))
                continue;
            
            pathToFile += "/" + folders[i];
            tmpFile = new File(pathToFile);

            if (tmpFile.isDirectory() && !node.exists(folders[i])) {
                node.add(new Directory(folders[i], pathToFile));
            } else if (tmpFile.isFile()) {
                node.add(new FileNode(folders[i], pathToFile));
                FileUtils.writeStaging(tree);
                System.out.println("File added.");
                return true;
            }

            node = node.getDirectory(folders[i]);

        }
        FileUtils.writeStaging(tree);
        return true;
    }

}
