/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.ForeignlanguageJpaController;
import ims.dto.Foreignlanguage;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class ForeignLanguageBLL {
    public Foreignlanguage[] findForeignlanguageEntities() {
        return new ForeignlanguageJpaController(UtilClass.getEMF())
                .findForeignlanguageEntities().toArray(new Foreignlanguage[0]);
    }
}
