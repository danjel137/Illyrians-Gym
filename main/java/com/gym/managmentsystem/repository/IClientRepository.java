package com.gym.managmentsystem.repository;

import com.gym.managmentsystem.model.ClientsModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<ClientsModel,Long> {

}
