/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.FolkJpaController;
import ims.dal.TypestaffJpaController;
import ims.dto.Folk;
import ims.dto.Typestaff;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class TypeStaffBLL {
    public Typestaff[] findTypeStaffEntities()  {
        return new TypestaffJpaController(UtilClass.getEMF()).findTypestaffEntities().toArray(new Typestaff[0]);
    }
}
