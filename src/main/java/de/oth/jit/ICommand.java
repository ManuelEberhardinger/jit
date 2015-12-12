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
public interface ICommand {
    public String getName();
    public File getPath();
    public boolean execute(String arg) throws IOException;
}
