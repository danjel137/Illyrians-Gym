package com.gym.managmentsystem.service;

import com.gym.managmentsystem.model.ClientInfoModel;


import java.util.List;

public interface IClientInfoService {
    List<ClientInfoModel> getAllClientInfo();
    ClientInfoModel saveClientInfo(ClientInfoModel infoModel);
    ClientInfoModel getClientInfoById(Long id);
    ClientInfoModel updateClientInfo(ClientInfoModel infoModel  );
    void deleteClientInfoById(Long id);
    List<ClientInfoModel> getClientInfoByClientId(Long client_id);
}
