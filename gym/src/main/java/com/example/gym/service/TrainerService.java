package com.example.gym.service;

import com.example.gym.exception.TrainerNotFoundException;
import com.example.gym.model.Trainer;
import com.example.gym.repositories.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public ResponseEntity<List<Trainer>> getAllTrainers() {
        return ResponseEntity.ok(trainerRepository.findAll());
    }

    public ResponseEntity<Trainer> createTrainer(@RequestBody Trainer trainer) {
        return new ResponseEntity<>(trainerRepository.saveAndFlush(trainer), HttpStatus.CREATED);
    }

    public ResponseEntity<Trainer> getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(() -> new TrainerNotFoundException("Trainer not exist with id :" + id));
        return ResponseEntity.ok(trainer);
    }

    public ResponseEntity<Trainer> updateTrainer(Long id, Trainer trainer) {
        Trainer existingTrainer = trainerRepository.findById(id).orElseThrow(() -> new TrainerNotFoundException("Trainer not exist with id :" + id));
        existingTrainer.setName(trainer.getName());
        existingTrainer.setTitle(trainer.getTitle());
        existingTrainer.setSocial_media_accounts(trainer.getSocial_media_accounts());
        existingTrainer.setBiography(trainer.getBiography());
        Trainer updatedTrainer = trainerRepository.save(trainer);
        return ResponseEntity.ok(updatedTrainer);

    }

    public ResponseEntity<Long> deleteTrainer(@PathVariable Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new TrainerNotFoundException("Trainer not exist with id :" + id));
        trainerRepository.delete(trainer);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}
