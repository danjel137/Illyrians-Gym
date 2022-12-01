package com.example.gym.controller;

import com.example.gym.model.Gym;
import com.example.gym.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")

public class GymController {

    private final  GymService gymService;

    @Autowired
    public GymController(GymService gymService) {
        this.gymService = gymService;
    }

    @GetMapping("/gyms")
    public ResponseEntity<List<Gym> >getAllGyms() {
        return gymService.getAllGyms();
    }

    @PostMapping("/gyms")
    public ResponseEntity<Gym> createGym(@RequestBody Gym gym) {
        return gymService.createGym(gym);
    }

    @GetMapping("/gyms/{id}")
    public ResponseEntity<Gym> getGymById(@PathVariable Long id) {
        return gymService.getGymById(id);
    }

    @PutMapping("/gyms/{id}")
    public ResponseEntity<Gym> updateGym(@PathVariable Long id, @RequestBody Gym gym) {
        return gymService.updateGym(id, gym);
    }

    @DeleteMapping("/gyms/{id}")
    public ResponseEntity<Long> deleteGym(@PathVariable Long id) {
        return gymService.deleteGym(id);
    }

}
