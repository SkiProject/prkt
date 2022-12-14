package com.project.prkt.repository;

import com.project.prkt.model.KneeProtection;
import com.project.prkt.model.Snowboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nikolai Khriapov
 */

@Repository
public interface KneeProtectionRepository extends JpaRepository<KneeProtection, Long> {

    // ----- show all -----
    List<KneeProtection> findAllByOrderById();

    // ----- search -----
    List<KneeProtection> findAllByNameContainingIgnoreCase(String partOfName);

    //// ----- edit booking info / assign equipment to riders -----
    List<KneeProtection> findAllByOrderBySize();
}
