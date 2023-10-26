package com.springboot.meetMyLecturer.utils;

import com.springboot.meetMyLecturer.entity.EmptySlot;
import com.springboot.meetMyLecturer.modelDTO.EmptySlotDTO;
import com.springboot.meetMyLecturer.repository.EmptySlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlotUtils {
    @Autowired
    private EmptySlotRepository emptySlotRepository;

    public List<EmptySlotDTO> getSlotByTimeStart(Time timeStart, int slotTimeId) {
//        // Retrieve empty slots from the repository based on timeStart
//        List<EmptySlot> emptySlotListBySlotTime = emptySlotRepository.findEmptySlotBySlotTime_SlotTimeId(slotTimeId);
//
//        if (!emptySlotListBySlotTime.isEmpty()) {
//            // If there are matching empty slots, you can process them here
//            for(int i = 0; i < emptySlotListBySlotTime.size(); i++){
//
//            }
//
//            // You can return the list of processed EmptySlotDTOs if needed
//            return null;
//        }
        return null;
    }
}

//@Component
//public class SlotUtils {
//    @Autowired
//    EmptySlotRepository emptySlotRepository;
//    public EmptySlotDTO getSlotByTimeStart(Time timeStart){
//        List<EmptySlot> emptySlotList = emptySlotRepository.findEmptySlotsByTimeStart(timeStart);
//        if(!emptySlotList.isEmpty()){
//            emptySlotList.stream().map(
//                    emptySlot -> (){
//
//                    }
//            )
//        }
//        return null;
//    }
//}
