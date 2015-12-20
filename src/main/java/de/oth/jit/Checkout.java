/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 *
 * Checkout the objects with the given hash of the commit.
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

    // Checkout the commit of the given hash.
    @Override
    public boolean execute(String arg) throws IOException, ClassNotFoundException {
        if (arg == null || arg == "") {
            System.out.println("False commit hash…");
            return false;
        }

        Path commitRoot = Paths.get("./.jit/objects/" + arg);

        if (!Files.exists(commitRoot)) {
            System.out.println("Commit does not exists…");
        }

        // Read commit file.
        List<String> lines = Files.readAllLines(commitRoot);

        // Deletes all entries of the directory, but not the .jit folder.
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("."))) {
            for(Path entry : stream) {
                if(entry.endsWith(".jit")) {
                    continue;
                }
                FileUtils.deleteFolder(entry.toFile());
            }
        }

        // Restore all files
        restoreFile(lines, ".");

        return true;
    }

    // Restores all files and directories of the commit.
    // lines: the content of the objects file
    // path: the path we already re-created.
    private void restoreFile(List<String> lines, String path) throws IOException {
        // Walk through all lines of the file.
        for (String line : lines) {
            String[] words = line.split(" ");

            // If the file is a commit file, console will print info of the hash.
            if (words[0].equals("Commit")) {
                String msg = "";
                for(int i = 1; i < words.length; i++) {
                    msg += words[i] + " ";
                }
                
                System.out.println("Checkout commit \"" + msg.trim() + "\"");
            } else if (words[0].equals("Directory") && words.length == 3) {
                // If line points to a directory, directory will be created.
                Files.createDirectory(Paths.get(path + "/" + words[2]));
                // After that the method will be called again with the content of the next directory and the already created path.
                restoreFile(Files.readAllLines(Paths.get("./.jit/objects/" + words[1])), path + "/" + words[2]);
            } else if (words[0].equals("File") && words.length == 3) {
                // If the line points to a file, the file will be created and the contet will be copied.
                Files.write(Paths.get(path + "/" + words[2]), Files.readAllBytes(Paths.get("./.jit/objects/" + words[1])));
            }

        }
    }

}
