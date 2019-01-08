/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.NationalityJpaController;
import ims.dto.Nationality;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class NationalityBLL {
    public Nationality[] findNationalityEntities() {
        return new NationalityJpaController(UtilClass.getEMF())
                .findNationalityEntities().toArray(new Nationality[0]);
    }
}
