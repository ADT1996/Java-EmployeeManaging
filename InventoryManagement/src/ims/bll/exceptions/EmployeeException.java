/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll.exceptions;

/**
 *
 * @author abc
 */
public class EmployeeException extends Exception {
    public EmployeeException(){
        super();
    }
    
    public EmployeeException(String message) {
        super(message);
    }
    
    public EmployeeException(String message,Throwable cause) {
        super(message,cause);
    }
    
    public EmployeeException(String message, String cause) {
        super(message,new Throwable(cause));
    }
}
