/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.*;

/**
 *
 * Interface for all commands so that we can save it in a list.
 * It follows the Command pattern, so you can easily extend the commands.
 */
public interface ICommand  {

    public String getName();

    public File getFile();

    public boolean execute(String arg) throws Exception;
}
