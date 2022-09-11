package com.project.prkt.repository;

import com.project.prkt.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Nikolai Khriapov
 */

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // ----- show all -----
    List<Client> findAllByOrderById();

    // ----- search -----
    List<Client> findAllBySurnameContaining(String parameter);
    List<Client> findAllByPhone1Containing(String parameter);
    List<Client> findAllByPhone2Containing(String parameter);
}