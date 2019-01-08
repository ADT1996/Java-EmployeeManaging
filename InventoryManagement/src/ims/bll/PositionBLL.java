/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.EmployeePositionJpaController;
import ims.dto.EmployeePosition;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class PositionBLL {
    public EmployeePosition[] findEmployeePositionEntities() {
        return new EmployeePositionJpaController(UtilClass.getEMF())
                .findEmployeePositionEntities().toArray(new EmployeePosition[0]);
    }
}
