/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.ComputingJpaController;
import ims.dto.Computing;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class ComputingBLL {
    public Computing[] findComputingEntities() {
        return new ComputingJpaController(UtilClass.getEMF())
                .findComputingEntities().toArray(new Computing[0]);
    }
}
