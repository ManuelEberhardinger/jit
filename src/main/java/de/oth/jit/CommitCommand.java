/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

/**
 *
 * Commit the staging tree and create all necessary files for a checkout.
 */
public class CommitCommand implements ICommand {

    private final File _file;
    private final String _name = "commit";
    private List<String> _lines;
    private Path _path;
    private String _root = "./.jit/objects";

    public CommitCommand(File path) {
        if (path == null) {
            throw new NullPointerException("path");
        }
        _lines = new ArrayList<>();
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

    // Creates the commit and all the files, which are represented by the staging tree.
    @Override
    public boolean execute(String arg) throws Exception {
        if (arg == null) {
            System.out.println("No commit message…");
            return false;
        }

        String path = "./.jit/staging/staging.ser";
        Directory tree;

        // Deserialize the staging tree if it exists, otherwise the method will return.
        if (new File(path).exists()) {
            tree = FileUtils.readStaging(path);
        } else {
            System.out.println("No files added! Nothing to commit…");
            return false;
        }

        // Create a commit for the tree, to get a hash of all childern.
        IType commit = new Commit(arg, _root, tree);

        // Create file for commit with the hash as name.
        _path = Paths.get(_root + "/" + commit.getHash());
        
        // Add the lines to the file so that it looks like the commit file of the description.
        _lines.add(commit.getType() + " " + commit.getName());
        _lines.addAll(tree.getAllChildernStrings());
        
        // Create the new file with the content.
        Files.write(_path, _lines, Charset.forName("UTF-8"));
        System.out.println("Created commit with hash: " + commit.getHash());

        // Creates files for the rest
        for(String childernPath : tree.getAllChildernNames()) {
            writeDirectoryOrFile(tree.getDirectory(childernPath));
        }

        return true;
    }

    // Recursive method to create the files that represent a file or directory.
    private void writeDirectoryOrFile(IType node) throws Exception {
        if (node == null) {
            return;
        }

        // Creates files for the rest
        Directory nodeDirectory = null;

        // Clear lines.
        _lines.clear();
        _path = Paths.get(_root + "/" + node.getHash());

        // If it is a directory, we create a file with the hash as name 
        // and the content is the full string of all childerns.
        if ("Directory".equals(node.getType())) {
            _lines.add(node.getType());
            nodeDirectory = (Directory) node;

            _lines.addAll(nodeDirectory.getAllChildernStrings());
            Files.write(_path, _lines, Charset.forName("UTF-8"));
            List<String> allNames = nodeDirectory.getAllChildernNames();

            // Call the method again with all childern nodes.
            for (String name : allNames) {
                writeDirectoryOrFile(nodeDirectory.getNext(name));
            }
        } else if ("File".equals(node.getType())) {
            // If node is a file, we create a new file, that is copy of file in the tree.
            Files.write(_path, Files.readAllBytes(Paths.get(node.getPath())));
        }
    }

}
