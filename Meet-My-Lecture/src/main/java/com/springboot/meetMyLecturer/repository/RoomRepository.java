package com.springboot.meetMyLecturer.repository;

import com.springboot.meetMyLecturer.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room,String> {

    List<Room> findRoomsByStatus(String status);

    Room findRoomByRoomIdAndAddress(String roomId, String address);

    Page<Room> findRoomByStatus(String status, Pageable pageable);

}
