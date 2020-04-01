package com.prabhakar.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prabhakar.business.domain.RoomReservation;
import com.prabhakar.entity.Guest;
import com.prabhakar.entity.Reservation;
import com.prabhakar.entity.Room;
import com.prabhakar.repository.GuestRepository;
import com.prabhakar.repository.ReservationRepository;
import com.prabhakar.repository.RoomRepository;

@Service
public class ReservationService {

	private final RoomRepository roomRepository;
	private final GuestRepository guestRepository;
	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
			ReservationRepository reservationRepository) {
		super();
		this.roomRepository = roomRepository;
		this.guestRepository = guestRepository;
		this.reservationRepository = reservationRepository;
	}

	public List<RoomReservation> getRoomReservationsForDate(Date date) {
		Iterable<Room> rooms = this.roomRepository.findAll();
		Map<Long, RoomReservation> roomReservationMap = new HashMap<Long, RoomReservation>();
		rooms.forEach(room -> {
			RoomReservation roomReservation = new RoomReservation();
			roomReservation.setRoomId(room.getId());
			roomReservation.setRoomName(room.getName());
			roomReservation.setRoomNumber(room.getNumber());
			roomReservationMap.put(room.getId(), roomReservation);
		});

		Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
		reservations.forEach(reservation -> {
			Optional<Guest> guestResponse = this.guestRepository.findById(reservation.getGuestId());
			if (guestResponse.isPresent()) {
				Guest guest = guestResponse.get();
				RoomReservation roomReservation = roomReservationMap.get(reservation.getId());
				roomReservation.setDate(date);
				roomReservation.setFirstName(guest.getFirstName());
				roomReservation.setLastName(guest.getLastName());
				roomReservation.setGuestId(guest.getId());
			}

		});

		List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		for (Long roomId : roomReservationMap.keySet()) {
			roomReservations.add(roomReservationMap.get(roomId));
		}

		return roomReservations;
	}

}
