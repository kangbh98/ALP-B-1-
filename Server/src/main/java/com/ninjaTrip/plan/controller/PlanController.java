package com.ninjaTrip.plan.controller;

import com.ninjaTrip.plan.dto.Diary;
import com.ninjaTrip.plan.dto.Plan;
import com.ninjaTrip.plan.service.DiaryService;
import com.ninjaTrip.plan.service.ImageGenerateService;
import com.ninjaTrip.plan.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    private final ImageGenerateService imageGenerateService;
    private final DiaryService diaryService;

    public PlanController(PlanService planService,ImageGenerateService imageGenerateService, DiaryService diaryService) {
        this.planService = planService;
        this.imageGenerateService = imageGenerateService;
        this.diaryService = diaryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createPlan(@RequestBody Plan plan) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.CREATED;
        try {
            planService.createPlan(plan);
            status = HttpStatus.OK;
            resultMap.put("status", status);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/{userIdx}")
    public ResponseEntity<Map<String, Object>> getPlan(@PathVariable int userIdx) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            List<Plan> plan = planService.getPlan(userIdx);
            status = HttpStatus.OK;
            resultMap.put("planinfo", plan);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/create/diary")
    public String postImage(@RequestParam int userIdx, @RequestParam String date,@RequestParam String comment) {

        List<Plan> plan = planService.getDatePlan(userIdx, date);
        String s = imageGenerateService.makePrompt(plan,comment);
        String imageUrl = imageGenerateService.openAiImageUrl(s);
        Diary diary = new Diary(date,userIdx,imageUrl,comment);
        diaryService.createDiary(diary);
        return imageUrl;
    }

    @GetMapping("/get/diary")
    public Diary getDiary(@RequestParam int userIdx, @RequestParam String date) {
        return diaryService.getDiary(userIdx, date);
    }
}
