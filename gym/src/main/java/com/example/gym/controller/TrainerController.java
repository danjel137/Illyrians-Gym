package com.example.gym.controller;

import com.example.gym.model.Trainer;
import com.example.gym.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class TrainerController {
    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<Trainer>> getAllATrainers() {
        return trainerService.getAllTrainers();
    }

    @PostMapping("/trainers")
    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        return trainerService.createTrainer(trainer);
    }

    @GetMapping("/trainers/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        return trainerService.getTrainerById(id);
    }

    @PutMapping("/trainers/{id}")
    public ResponseEntity<Trainer> updateTrainer(@PathVariable Long id, @RequestBody Trainer trainer) {
        return trainerService.updateTrainer(id, trainer);
    }

    @DeleteMapping("/trainers/{id}")
    public ResponseEntity<Long> deleteTrainer(@PathVariable Long id) {
        return trainerService.deleteTrainer(id);
    }


}
