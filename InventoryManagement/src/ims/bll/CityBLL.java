/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.CityJpaController;
import ims.dto.City;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class CityBLL {
    public City[] findCityEntities() {
        return new CityJpaController(UtilClass.getEMF()).findCityEntities().toArray(new City[0]);
    }
}
