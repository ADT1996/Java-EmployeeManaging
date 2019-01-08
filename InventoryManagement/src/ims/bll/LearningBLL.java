/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.LearningJpaController;
import ims.dto.Learning;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class LearningBLL {
    public Learning[] findLearningEntities() {
        return new LearningJpaController(UtilClass.getEMF())
                .findLearningEntities().toArray(new Learning[0]);
    }
}
