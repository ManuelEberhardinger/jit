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
    public static void main(String[] args) throws IOException {
        
        File path = new File(".");
        
        List<ICommand> commandList = new ArrayList<>();
        commandList.add(new Init(path));
        
        commandList.get(0).execute(null);
        
    }
}
