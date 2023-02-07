package com.gym.managmentsystem.serviceImpl;

import com.gym.managmentsystem.repository.IClientRepository;
import com.gym.managmentsystem.service.IClientsService;
import com.gym.managmentsystem.model.ClientsModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientsService {
    @Autowired
    private IClientRepository clientRepository;

    public ClientServiceImpl(IClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientsModel save(ClientsModel registrationDto) {
        return null;
    }

    @Override
    public List<ClientsModel> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public ClientsModel saveClient(ClientsModel clientsModel) {
        return clientRepository.save(clientsModel);
    }

    @Override
    public ClientsModel getClientById(Long id) {
        return clientRepository.getById(id);
    }

    @Override
    public ClientsModel updateClient(ClientsModel clientsModel) {
        return clientRepository.save(clientsModel);
    }

    @Override
    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
