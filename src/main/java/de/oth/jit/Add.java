/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.*;

/**
 *
 * Adds a file to the staging tree.
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

    // Adds a file to the staging tree.
    @Override
    public boolean execute(String arg) throws IOException, ClassNotFoundException {
        if (arg.equals("") || arg == null || !new File("./" + arg).exists()) {
            System.out.println("Path points to no file or directory…");
            return false;
        }

        String path = "./.jit/staging/staging.ser";
        Directory tree;

        // Checks if the staging file already exists, otherwise it will be created new.
        if (new File(path).exists()) {
            tree = FileUtils.readStaging(path);
        } else {
            tree = new Directory(".", ".");
        }

        // Split the path into the folders.
        String[] folders = arg.split("/");
        // The path we are in at the moment.
        String pathToFile = ".";

        File tmpFile;
        Directory node = tree;

        // Walk through all the folders.
        for (int i = 0; i < folders.length; i++) {
            if(folders[i].equals(""))
                continue;
            
            // Add the folder to the current path.
            pathToFile += "/" + folders[i];
            tmpFile = new File(pathToFile);

            // If file is a directory and does not exists in the tree we add it.
            if (tmpFile.isDirectory() && !node.exists(folders[i])) {
                node.add(new Directory(folders[i], pathToFile));
            } else if (tmpFile.isFile()) {
                // If the file is a file, we add it and serialize the tree.
                // Because a file is always the end of the path.
                node.add(new FileNode(folders[i], pathToFile));
                FileUtils.writeStaging(tree);
                System.out.println("File added.");
                return true;
            }
            
            // Get the next folder of the path.
            node = node.getDirectory(folders[i]);

        }
        FileUtils.writeStaging(tree);
        return true;
    }

}
