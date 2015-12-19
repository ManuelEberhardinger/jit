/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

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
        if (arg == null || arg == "") {
            System.out.println("False commit hash…");
            return false;
        }

        Path commitRoot = Paths.get("./.jit/objects/" + arg);

        if (!Files.exists(commitRoot)) {
            System.out.println("Commit does not exists…");
        }

        // Read commit file and delete the root folders of the added files.
        List<String> lines = Files.readAllLines(commitRoot);

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

    private void restoreFile(List<String> lines, String path) throws IOException {
        for (String line : lines) {
            String[] words = line.split(" ");

            if (words[0].equals("Commit")) {
                String msg = "";
                for(int i = 1; i < words.length; i++) {
                    msg += words[i] + " ";
                }
                
                System.out.println("Checkout commit \"" + msg.trim() + "\"");
            } else if (words[0].equals("Directory") && words.length == 3) {
                Files.createDirectory(Paths.get(path + "/" + words[2]));
                restoreFile(Files.readAllLines(Paths.get("./.jit/objects/" + words[1])), path + "/" + words[2]);
            } else if (words[0].equals("File") && words.length == 3) {
                Files.write(Paths.get(path + "/" + words[2]), Files.readAllBytes(Paths.get("./.jit/objects/" + words[1])));
            }

        }
    }

}
