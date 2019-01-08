/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.JobJpaController;
import ims.dto.Job;
import ims.util.UtilClass;

/**
 *
 * @author abc
 */
public class JobBLL {
    public Job[] findJobEntities() {
        return new JobJpaController(UtilClass.getEMF()).findJobEntities().toArray(new Job[0]);
    }
}
