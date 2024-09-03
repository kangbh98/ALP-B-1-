package com.ninjaTrip.plan.service;

import com.ninjaTrip.plan.dto.Plan;

import java.util.List;

public interface ImageGenerateService {
    String openAiImageUrl(String imageToRequest);
    String makePrompt(List<Plan> plan, String comment);
}
