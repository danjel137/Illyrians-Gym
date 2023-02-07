package com.gym.managmentsystem.repository;

import com.gym.managmentsystem.model.ClientInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IClientInfoRepository extends JpaRepository<ClientInfoModel, Long> {

    @Query(value = "SELECT * FROM clientinfo WHERE client_id = ?1", nativeQuery = true)
    public List<ClientInfoModel> findByClientId(Long clientId);

}
