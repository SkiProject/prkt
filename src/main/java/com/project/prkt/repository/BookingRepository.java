package com.project.prkt.repository;import com.project.prkt.model.Booking;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;import java.util.Date;import java.util.List;/** * @author Nikolai Khriapov */@Repositorypublic interface BookingRepository extends JpaRepository<Booking, Long> {    // ----- show all -----    List<Booking> findAllByOrderById();    // ----- show bookings for the date    List<Booking> findByDateOfArrival(Date date);    // ----- show incomplete bookings -----    List<Booking> findAllByCompletedFalseOrderByDateOfArrivalAsc();}