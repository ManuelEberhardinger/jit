/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.util.*;
import java.io.*;

/**
 *
 * @author manueleberhardinger
 */
public class Jit {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File path = new File(".");
        String toExecute = null;
        String parameter = null;

        if (args.length < 1 || args.length > 2) {
            displayHelp();
            return;
        } else if (args.length == 1) {
            toExecute = args[0];
        } else {
            if(args[0].equals("init")) {
                displayHelp();
                return;
            }
            toExecute = args[0];
            parameter = args[1];
        }

        List<ICommand> commandList = new ArrayList<>();
        commandList.add(new Init(path));
        commandList.add(new Add(path));

        for(ICommand command : commandList) {
            if(toExecute.equals(command.getName())){
                command.execute(parameter);
                return;
            }
        }
        
        System.out.println("Command was not found!");
        displayHelp();
    }

    private static void displayHelp() {
        System.out.println("Usage:");
        System.out.println("    jit init");
        System.out.println("or:");
        System.out.println("    jit [ add | remove | commit | checkout ] [ argument ]");
        System.out.println("First init has to be called after that files can be added.");
    }

}
