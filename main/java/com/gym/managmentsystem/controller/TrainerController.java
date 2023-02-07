package com.gym.managmentsystem.controller;

import com.gym.managmentsystem.service.IClientInfoService;
import com.gym.managmentsystem.service.IClientsService;
import com.gym.managmentsystem.service.ISessionsService;
import com.gym.managmentsystem.service.ITrainersService;
import com.gym.managmentsystem.model.ClientInfoModel;
import com.gym.managmentsystem.model.ClientsModel;
import com.gym.managmentsystem.model.TrainersModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
@RequestMapping(value = "/trainer")
public class TrainerController {
    private static Long Client_Id;
    ISessionsService sessionsService;
    IClientInfoService clientInfoService;
    ITrainersService trainersService;
    IClientsService clientsService;

    public TrainerController(ISessionsService sessionsService, IClientInfoService clientInfoService, IClientsService clientsService, ITrainersService trainersService) {
        this.trainersService = trainersService;
        this.clientsService = clientsService;
        this.clientInfoService = clientInfoService;
        this.sessionsService = sessionsService;
    }

    @GetMapping(value = "/getTrainerPage")
    public String getTrainerPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        TrainersModel trainersModel = (TrainersModel) request.getSession().getAttribute("trainer");
        if (trainersModel != null) {

            String name = trainersModel.getName() ;
            //add cookie to response
            Cookie cookie1 = new Cookie("UserInfo",String.valueOf( name));
            cookie1.setMaxAge(1 * 24 * 60 * 60); // expires in 7 days
            cookie1.setSecure(false);
            cookie1.setHttpOnly(false);
            response.addCookie(cookie1);

            //add cookie to response
            Cookie cookie2 = new Cookie("Role", "Trainer");
            cookie2.setMaxAge(1 * 24 * 60 * 60); // expires in 1 days
            cookie2.setSecure(false);
            cookie2.setHttpOnly(false);
            response.addCookie(cookie2);


            List<ClientInfoModel> list = clientInfoService.getAllClientInfo().stream().filter(x -> x.getTrainerId() == (trainersModel.getTrainerId())).collect(Collectors.toList());

            List<ClientsModel> clientList = new ArrayList<>();
            for (ClientInfoModel clientInfoModel : list) {
                if (trainersModel.getTrainerId() == clientInfoModel.getTrainerId()) {
                    clientList.add(clientsService.getClientById(clientInfoModel.getClientsModel().getClientId()));
                }
            }
            model.addAttribute("clientList", clientList);
            return "trainerShow";
        } else {
            return "redirect:/login/Authorization";
        }

    }

    @GetMapping(value = "/getClientInfoPage/{client_id}")
    public String getClientInfoPage(@PathVariable("client_id") Long client_id, Model model, HttpServletRequest request) {
        TrainersModel trainersModel = (TrainersModel) request.getSession().getAttribute("trainer");
        if (trainersModel != null) {
            Client_Id = client_id;

            List<ClientInfoModel> list = clientInfoService.getAllClientInfo().stream().filter(x -> x.getTrainerId() == (trainersModel.getTrainerId())).collect(Collectors.toList());

            List<ClientsModel> clientList = new ArrayList<>();
            for (ClientInfoModel clientInfoModel : list) {
                if (trainersModel.getTrainerId() == clientInfoModel.getTrainerId()) {
                    clientList.add(clientsService.getClientById(clientInfoModel.getClientsModel().getClientId()));
                }
            }
            ClientsModel clientModel = clientList.stream().filter(x -> x.getClientId() == client_id).collect(Collectors.toList()).stream().findFirst().get();

            Stream<ClientInfoModel> infoList = clientInfoService.getClientInfoByClientId(clientModel.getClientId()).stream().filter(x -> x.getTrainerId() == trainersModel.getTrainerId());
            model.addAttribute("infoList", infoList);
            return "trainerClientInfoShow";
        } else {
            return "redirect:/login/Authorization";
        }
    }


    @PostMapping(value = "/postClientInfoCreate")
    public String postStudentInfoCreate(@Valid @ModelAttribute("clientInfoModel") ClientInfoModel clientInfoModel, Model model, HttpServletRequest request) {
        try {
            TrainersModel trainersModel = (TrainersModel) request.getSession().getAttribute("trainer");
            if (trainersModel != null) {
                if (clientInfoModel == null) {
                    return "redirect:/trainer/getTrainerPage";
                }
                clientInfoModel.setTrainerId(trainersModel.getTrainerId().intValue());
                clientInfoModel.setSessionName(sessionsService.getSessionById(trainersModel.getSessionsModel().getSessionId()).getSessionName());
                clientInfoModel.setClientsModel(clientsService.getClientById(Client_Id));
                clientInfoService.saveClientInfo(clientInfoModel);
                return "trainerClientInfoShow";
            } else {
                return "redirect:/login/Authorization";
            }
        } catch (Exception e) {
            return "redirect:/trainer/getTrainerPage";
        }

    }


    @GetMapping(value = "/getClientInfoUpdate/{client_info_id}")
    public String getClientInfoUpdate(@PathVariable("client_info_id") Long client_info_id, Model model, HttpServletRequest request) {
        TrainersModel trainersModel = (TrainersModel) request.getSession().getAttribute("trainer");
        if (trainersModel != null) {
            ClientInfoModel clientInfoModel = clientInfoService.getClientInfoById(client_info_id);
            model.addAttribute("clientInfoModel", clientInfoModel);
            return "trainerClientInfoUpdate";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @PostMapping(value = "/postClientInfoUpdate")
    public String postClientInfoUpdate(@Valid @ModelAttribute("clientInfoModel") ClientInfoModel clientInfoModel, Model model, HttpServletRequest request) {
        try {
            TrainersModel trainersModel = (TrainersModel) request.getSession().getAttribute("trainer");
            if (trainersModel != null) {
                if (clientInfoModel == null) {
                    return "redirect:/trainer/getTrainerPage";
                }
                clientInfoModel.setClientsModel(clientsService.getClientById(Client_Id));
                clientInfoService.saveClientInfo(clientInfoModel);
                return "redirect:/trainer/getClientInfoUpdate/" + clientInfoModel.getClientInfoId();
            } else {
                return "redirect:/login/Authorization";
            }
        } catch (Exception e) {
            return "redirect:/trainer/getTrainerPage";
        }
    }
}
