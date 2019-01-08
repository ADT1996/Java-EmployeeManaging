/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.FolkJpaController;
import ims.dto.Folk;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class FolkBLL {
    public Folk[] findFolkEntities()  {
        return new FolkJpaController(UtilClass.getEMF()).findFolkEntities().toArray(new Folk[0]);
    }
}
