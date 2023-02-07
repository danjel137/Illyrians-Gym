package com.gym.managmentsystem.serviceImpl;

import com.gym.managmentsystem.model.TrainersModel;
import com.gym.managmentsystem.service.ITrainersService;
import com.gym.managmentsystem.repository.ITrainersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements ITrainersService {
    @Autowired
    private ITrainersRepository trainersRepository;

    public TrainerServiceImpl(ITrainersRepository trainersRepository){
        this.trainersRepository = trainersRepository;
    }

    @Override
    public List<TrainersModel> getAllTrainers() {
       return trainersRepository.findAll();
    }


    @Override
    public TrainersModel saveTrainer(TrainersModel trainersModel) {
        return trainersRepository.save(trainersModel);
    }

    @Override
    public TrainersModel getTrainerById(Long id) {
        return trainersRepository.getById(id);
    }

    @Override
    public TrainersModel updateTrainer(TrainersModel trainersModel) {
        return trainersRepository.save(trainersModel);
    }

    @Override
    public void deleteTrainerById(Long id) {
        trainersRepository.deleteById(id);
    }
}
