package com.gym.managmentsystem.serviceImpl;

import com.gym.managmentsystem.model.ClientInfoModel;
import com.gym.managmentsystem.service.IClientInfoService;
import com.gym.managmentsystem.repository.IClientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoServiceImpl implements IClientInfoService {
    @Autowired
    private IClientInfoRepository clientInfoRepository;

    public ClientInfoServiceImpl(IClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }

    @Override
    public List<ClientInfoModel> getAllClientInfo() {
        return clientInfoRepository.findAll();
    }

    @Override
    public ClientInfoModel saveClientInfo(ClientInfoModel infoModel) {
        return clientInfoRepository.save(infoModel);
    }

    @Override
    public ClientInfoModel getClientInfoById(Long id) {
        return clientInfoRepository.findById(id).get();
    }

    @Override
    public ClientInfoModel updateClientInfo(ClientInfoModel infoModel) {
        return clientInfoRepository.save(infoModel);
    }

    @Override
    public void deleteClientInfoById(Long id) {
        clientInfoRepository.deleteById(id);
    }

    @Override
    public List<ClientInfoModel> getClientInfoByClientId(Long client_id) {
        return clientInfoRepository.findByClientId(client_id);
    }


}
