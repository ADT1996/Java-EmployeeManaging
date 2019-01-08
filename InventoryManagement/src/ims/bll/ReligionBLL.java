/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.ReligionJpaController;
import ims.dto.Religion;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class ReligionBLL {
    public Religion[] findReligionEntities() {
        return new ReligionJpaController(UtilClass.getEMF())
                .findReligionEntities().toArray(new Religion[0]);
    }
}
