package com.ninjaTrip.plan.service;

import com.ninjaTrip.plan.dto.Plan;

import java.util.List;

public interface PlanService {
    void createPlan(Plan plan);
    List<Plan> getPlan(int userIdx);
    List<Plan> getDatePlan(int userIdx, String date);
}
