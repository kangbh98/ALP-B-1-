package com.ninjaTrip.plan.service;

import com.ninjaTrip.plan.dto.Plan;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ImageGenerateServiceImpl implements ImageGenerateService {

    @Value("${openai-key}")
    private String OPENAI_KEY;

    public String openAiImageUrl(String imageToRequest) {
        System.out.println(imageToRequest);
        System.out.println("OpenAI Key: " + OPENAI_KEY);
        OpenAiService openAiService = new OpenAiService(OPENAI_KEY,Duration.ofSeconds(300));

        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(imageToRequest)
                .model("dall-e-3")
                .size("1024x1024")
                .n(1)
                .build();

        return openAiService.createImage(createImageRequest).getData().get(0).getUrl();


    }

    public String makePrompt(List<Plan> plan, String comment) {
        String s = "";
        for (Plan p : plan) {
            s += " [2024.05.22."+p.getTime()+"] "+p.getPlaceName()+"방문.";
        }
        s += comment+"}"+"\n 위의 일정을 바탕으로 만화 형식의이미지 생성해줘. 귀여운 느낌으로";
        return s;
    }
}
