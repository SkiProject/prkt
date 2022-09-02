package com.project.prkt.service;

import com.project.prkt.model.Snowboard;
import com.project.prkt.repository.SnowboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nikolai Khriapov
 */

@Service
public class SnowboardService {

    private final SnowboardRepository snowboardRepository;

    @Autowired
    public SnowboardService(SnowboardRepository snowboardRepository) {
        this.snowboardRepository = snowboardRepository;
    }

    // ----- show all -----
    public List<Snowboard> showAllSnowboards() {
        return snowboardRepository.findAllByOrderById();
    }

    // ----- add new -----
    public void addNewSnowboardToDB(Snowboard snowboard) {
        snowboardRepository.save(snowboard);
    }

    // ----- edit -----
    public Snowboard showOneSnowboardById(Long id) {
        return snowboardRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Snowboard with id = " + id + " not found!"));
    }

    public void updateSnowboardById(Long id, Snowboard updatedSnowboard) {
        Snowboard snowboardToBeUpdated = showOneSnowboardById(id);

        snowboardToBeUpdated.setName(updatedSnowboard.getName());
        snowboardToBeUpdated.setAvailable(updatedSnowboard.isAvailable());
        snowboardToBeUpdated.setCondition(updatedSnowboard.getCondition());
        snowboardToBeUpdated.setSize(updatedSnowboard.getSize());
        snowboardToBeUpdated.setStiffness(updatedSnowboard.getStiffness());
        snowboardToBeUpdated.setArch(updatedSnowboard.getArch());
        snowboardToBeUpdated.setBindingSize(updatedSnowboard.getBindingSize());

        snowboardRepository.save(snowboardToBeUpdated);
    }

    // ----- delete -----
    public void deleteSnowboardById(Long id) {
        snowboardRepository.deleteById(id);
    }

    // ----- search -----
    public List<Snowboard> showSnowboardsByPartOfName(String partOfName) {
        return snowboardRepository.findAllByNameContaining(partOfName);
    }

    // ----- sort -----
    public List<Snowboard> sortAllSnowboardsByParameter(String parameter, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(parameter).ascending() : Sort.by(parameter).descending();
        return snowboardRepository.findAll(sort);
    }

//    public void setBooking(Long snowboardId, Booking booking) {
//        showOneSnowboardById(snowboardId).setBooking(booking);
//    }
}
