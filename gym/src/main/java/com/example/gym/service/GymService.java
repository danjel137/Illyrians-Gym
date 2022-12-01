package com.example.gym.service;

import com.example.gym.exception.GymNotFoundException;
import com.example.gym.model.Gym;
import com.example.gym.repositories.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GymService {

    private  final GymRepository gymRepository;

    @Autowired
    public GymService(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    public ResponseEntity<List<Gym>> getAllGyms() {
        return ResponseEntity.ok(gymRepository.findAll());
    }

    public ResponseEntity<Gym> createGym(@RequestBody Gym gym) {
        return new ResponseEntity<>(gymRepository.saveAndFlush(gym), HttpStatus.CREATED);
    }

    public ResponseEntity<Gym> getGymById(Long id) {
        Gym gym = gymRepository.findById(id).orElseThrow(() -> new GymNotFoundException("Gym not exist with id :" + id));
        return ResponseEntity.ok(gym);
    }

    public ResponseEntity<Gym> updateGym(Long id, Gym gym) {
        Gym existingGym = gymRepository.findById(id).orElseThrow(() -> new GymNotFoundException("Gym not exist with id :" + id));
        existingGym.setName(gym.getName());
        existingGym.setLocation(gym.getLocation());
        existingGym.setSchedule(gym.getSchedule());
        existingGym.setDescription(gym.getDescription());
        Gym updatedGym = gymRepository.save(gym);
        return ResponseEntity.ok(updatedGym);

    }

    public ResponseEntity<Long> deleteGym(@PathVariable Long id) {
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new GymNotFoundException("Gym not exist with id :" + id));
        gymRepository.delete(gym);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
