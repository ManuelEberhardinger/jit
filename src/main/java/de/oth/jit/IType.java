/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.oth.jit;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author manueleberhardinger
 */
public interface IType extends Serializable{
    public String getPath();
    public String getType();
    public String getHash() throws NoSuchAlgorithmException, IOException;
    public String getName();
    public String getFullString();
}
