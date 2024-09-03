package com.ninjaTrip.plan.mapper;


import com.ninjaTrip.plan.dto.Diary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryMapper {
    void createDiary(Diary diary);
    Diary getDiaryByUserAndDate(int userIdx, String date);
}
