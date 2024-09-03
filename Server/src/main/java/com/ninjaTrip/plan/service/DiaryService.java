package com.ninjaTrip.plan.service;

import com.ninjaTrip.plan.dto.Diary;

public interface DiaryService {
    void createDiary(Diary diary);
    Diary getDiary(int userIdx , String date);
}
