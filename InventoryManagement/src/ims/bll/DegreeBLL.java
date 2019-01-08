/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.DegreeJpaController;
import ims.dto.Degree;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class DegreeBLL {
    public Degree[] findDegreeEntities() {
        return new DegreeJpaController(UtilClass.getEMF())
                .findDegreeEntities().toArray(new Degree[0]);
    }
}
