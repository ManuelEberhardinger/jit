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

    public static void main(String[] args) throws Exception {
        File path = new File(".");
        String toExecute = null;
        String parameter = null;

        // Make sure that we have the correct number of parameters.
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

        // Initialize the list of all commands.
        List<ICommand> commandList = new ArrayList<>();
        commandList.add(new Init(path));
        commandList.add(new Add(path));
        commandList.add(new Remove(path));
        commandList.add(new CommitCommand(path));
        commandList.add(new Checkout(path));

        // Search for the the correct comman and execute it with the parameters.
        for(ICommand command : commandList) {
            if(toExecute.equals(command.getName())){
                command.execute(parameter);
                return;
            }
        }
        
        System.out.println("Command was not found!");
        displayHelp();
    }

    // Help message for usage of Jit.
    private static void displayHelp() {
        System.out.println("Usage:");
        System.out.println("    jit init");
        System.out.println("or:");
        System.out.println("    jit [ add | remove | commit | checkout ] [ argument ]");
        System.out.println("First init has to be called after that files can be added.");
    }

}
