/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.DeparmentJpaController;
import ims.dto.Deparment;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class DeparmentBLL {
    public Deparment[] findDeparmentEntities() {
        return new DeparmentJpaController(UtilClass.getEMF()).findDeparmentEntities().toArray(new Deparment[0]);
    }
}
