/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.expandedcohort.web.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.expandedcohort.api.CohortDefinitionDataService;
import org.openmrs.module.expandedcohort.api.model.CohortDefinitionData;
import org.openmrs.module.expandedcohort.web.utils.WebConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/module/expandedcohort/cohortdefinition.json")
public class CohortDefinitionController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCohortDefinitionData(final @RequestParam(value = "uuid") String uuid) {
        CohortDefinitionDataService expandedCohortDataService = Context.getService(CohortDefinitionDataService.class);
        CohortDefinitionData cohortDefinitionData = expandedCohortDataService.getCohortDefinitionDataByUuid(uuid);
        return WebConverter.convertCohortDefinitionData(cohortDefinitionData);
    }
    @RequestMapping(method = RequestMethod.POST)
    public void saveCohortDefinition(final @RequestBody Map<String, Object> map){
        if (Context.isAuthenticated()) {
            String uuid = (String) map.get("uuid");
            String definition = (String) map.get("definition");
            Integer cohortId = (Integer) map.get("cohortid");
            boolean isScheduled = (Boolean) map.get("isscheduled");

            CohortDefinitionDataService expandedCohortDataService = Context.getService(CohortDefinitionDataService.class);

            if (StringUtils.isNotBlank(uuid)) {
                CohortDefinitionData cohortDefinitionData = expandedCohortDataService.getCohortDefinitionDataByUuid(uuid);
                cohortDefinitionData.setCohortId(cohortId);
                cohortDefinitionData.setDefinition(definition);
                cohortDefinitionData.setScheduled(isScheduled);
                expandedCohortDataService.saveCohortDefinitionData(cohortDefinitionData);
            } else {
                CohortDefinitionData cohortDefinitionData = new CohortDefinitionData();
                cohortDefinitionData.setCohortId(cohortId);
                cohortDefinitionData.setDefinition(definition);
                cohortDefinitionData.setScheduled(isScheduled);
                expandedCohortDataService.saveCohortDefinitionData(cohortDefinitionData);
            }
        }

    }
}
