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
 * @author manueleberhardinger
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

    @Override
    public boolean execute(String arg) throws Exception {
        if (arg == null) {
            System.out.println("No commit message…");
            return false;
        }

        String path = "./.jit/staging/staging.ser";
        Directory tree;

        if (new File(path).exists()) {
            tree = FileUtils.readStaging(path);
        } else {
            System.out.println("No files added! Nothing to commit…");
            return false;
        }

        IType commit = new Commit(arg, _root, tree);

        // Create file for commit 
        _path = Paths.get(_root + "/" + commit.getHash());
        _lines.add(commit.getType() + " " + commit.getName());
        _lines.addAll(tree.getAllChildernStrings());
        Files.write(_path, _lines, Charset.forName("UTF-8"));

        // Creates files for the rest
        for(String childernPath : tree.getAllChildernNames()) {
            writeDirectoryOrFile(tree.getDirectory(childernPath));
        }

        return true;
    }

    private void writeDirectoryOrFile(IType node) throws Exception {
        if (node == null) {
            return;
        }

        // Creates files for the rest
        Directory nodeDirectory = null;

        _lines.clear();
        _path = Paths.get(_root + "/" + node.getHash());

        if ("Directory".equals(node.getType())) {
            _lines.add(node.getType());
            nodeDirectory = (Directory) node;

            _lines.addAll(nodeDirectory.getAllChildernStrings());
            Files.write(_path, _lines, Charset.forName("UTF-8"));
            List<String> allNames = nodeDirectory.getAllChildernNames();

            for (String name : allNames) {
                writeDirectoryOrFile(nodeDirectory.getNext(name));
            }
        } else if ("File".equals(node.getType())) {
            Files.write(_path, Files.readAllBytes(Paths.get(node.getPath())));
        }
    }

}
