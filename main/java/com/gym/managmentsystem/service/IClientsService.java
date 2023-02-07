package com.gym.managmentsystem.service;

import com.gym.managmentsystem.model.ClientsModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IClientsService extends UserDetailsService {

    ClientsModel save(ClientsModel registrationDto);
    List<ClientsModel> getAllClients();
    ClientsModel saveClient(ClientsModel clientsModel);
    ClientsModel getClientById(Long id);
    ClientsModel updateClient(ClientsModel clientsModel);
    void deleteClientById(Long id);
}
