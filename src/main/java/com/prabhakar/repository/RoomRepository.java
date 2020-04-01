package com.prabhakar.repository;

import org.springframework.data.repository.CrudRepository;

import com.prabhakar.entity.Room;

public interface RoomRepository extends CrudRepository<Room, Long> {
	Room findByNumber(String number);
}
