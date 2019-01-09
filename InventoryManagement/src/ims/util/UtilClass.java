/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author abc
 */
public class UtilClass {
    
    public static String EMPTY_STRING = "";
    public static boolean UNCHECKED = false;
    public static Object NOSELECTEDITEM = null;
    public static Object NOTVALUE = null;
    
    public static EntityManagerFactory getEMF() {
        return Persistence.createEntityManagerFactory("InventoryManagementPU");
    }
}
