package com.project.prkt.repository;

import com.project.prkt.model.AssignedEquipment;
import com.project.prkt.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedEquipmentRepository extends JpaRepository<AssignedEquipment, Long> {

    AssignedEquipment findByRider(Rider rider);
}