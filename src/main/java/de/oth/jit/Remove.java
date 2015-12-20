/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import java.io.IOException;

/**
 * Remove a file from the staging tree.
 * 
 */
public class Remove implements ICommand {

    private final String _name = "remove";
    private final File _file;

    public Remove(File path) {
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

    // Remove a file from the staging tree.
    @Override
    public boolean execute(String arg) throws IOException, ClassNotFoundException {
        if (arg.equals("") || arg == null) {
            System.out.println("Path points to no file or directory…");
            return false;
        }

        String path = "./.jit/staging/staging.ser";
        Directory node, tree;
        IType toDel;

        // If the staging file does not exists nothing will happen.
        if (new File(path).exists()) {
            tree = FileUtils.readStaging(path);
        } else {
            System.out.println("No files are added for source control…");
            return false;
        }
        node = tree;
        
        // Split the path in the single folders.
        String[] folders = arg.split("/");
        
        // Walk through the existing tree.
        for (int i = 0; i < folders.length; i++) {
            if(folders[i].equals(""))
                continue;
            
            // If we reach the last element, we have to delete it, otherwise we will get the next node of the tree.
            if (i == folders.length-1 && node.getNext(folders[folders.length - 1]) != null) {
                // get the next node of the tree to delete it.
                toDel = node.getNext(folders[folders.length - 1]);
                if (toDel.getName().equals(folders[folders.length - 1])) {
                    if (node.remove(toDel)) {
                        System.out.println("File removed.");
                        FileUtils.writeStaging(tree);
                        return true;
                    } else {
                        System.out.println("File doesn't exists.");
                    }
                    
                    // Serialize the tree.
                    FileUtils.writeStaging(tree);
                    return true;
                }
            } else {
                node = node.getDirectory(folders[i]);
                if(node == null) 
                    break;
            }
        }

        System.out.println("File doesn't exists.");

        return false;
    }

}
