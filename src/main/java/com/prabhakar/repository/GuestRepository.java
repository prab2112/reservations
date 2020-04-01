package com.prabhakar.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.prabhakar.entity.Guest;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends PagingAndSortingRepository<Guest, Long> {

}