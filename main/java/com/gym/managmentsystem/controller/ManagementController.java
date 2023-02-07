package com.gym.managmentsystem.controller;

import com.gym.managmentsystem.dto.*;
import com.gym.managmentsystem.model.*;
import com.gym.managmentsystem.service.*;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/management")
public class ManagementController {
    IManagerService managerService;
    IClientsService clientsService;
    ITrainersService trainersService;
    IClientInfoService clientInfoService;
    ISessionsService sessionsService;
    private static ClientsModel clientsModel;

    public ManagementController(ISessionsService sessionsService, IClientInfoService clientInfoService, IManagerService managerService, IClientsService clientsService, ITrainersService trainersService) {
        this.managerService = managerService;
        this.clientsService = clientsService;
        this.trainersService = trainersService;
        this.clientInfoService = clientInfoService;
        this.sessionsService = sessionsService;
    }

    @GetMapping(value = "/getManagemetPage")
    public String managementHomePage(Model model, HttpServletResponse response, HttpServletRequest request) {

        try {
            ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
            if (sessionAdmin != null) {
                //add cookie to response
                Cookie cookie1 = new Cookie("UserInfo", sessionAdmin.getManagerName());
                cookie1.setMaxAge(1 * 24 * 60 * 60);
                cookie1.setSecure(false);
                cookie1.setHttpOnly(false);
                response.addCookie(cookie1);

                //add cookie to response
                Cookie cookie2 = new Cookie("Role", "Manager");
                cookie2.setMaxAge(1 * 24 * 60 * 60);
                cookie2.setSecure(false);
                cookie2.setHttpOnly(false);
                response.addCookie(cookie2);

                List<ClientsModel> clientsModelList = clientsService.getAllClients();
                clientsModelList = clientsService.getAllClients();
                model.addAttribute("clientsModelList", clientsModelList);

                return "managementClientCreate";
            } else {
                return "redirect:/login/Authorization";
            }


        } catch (Exception e) {
            return "redirect:/management/getManagemetPage";
        }
    }

    @GetMapping(value = "/getClientUpdate/{client_id}")
    public String getClientUpdate(Model model, @PathVariable(value = "client_id") Long client_id, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (client_id == null || client_id == 0) {
                return "redirect:/management/getManagemetPage";
            }
            ClientsModel clientsModel = clientsService.getClientById(client_id);
            model.addAttribute("clientsModel", clientsModel);
            return "managementClientUpdate";
        } else {
            return "redirect:/login/Authorization";
        }

    }

    @PostMapping(value = "/postClientUpdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String postClientUpdate(@Valid @ModelAttribute("clientsModel") ClientsModel clientsModel, BindingResult bindingResult, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            String errorDate = (String) bindingResult.getFieldError("recordTime").getRejectedValue();
            if (clientsModel == null) {
                return "redirect:/management/getManagemetPage";
            }
            clientsModel.setManagerModel((ManagerModel) request.getSession().getAttribute("admin"));
            clientsService.updateClient(clientsModel);
            return "redirect:/management/getManagemetPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @PostMapping(value = "/postClientCreate")
    public String createClient(ClientsModel clientsModel, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (clientsModel == null) {
                return "redirect:/management/getManagemetPage";
            }
            ManagerModel session = (ManagerModel) request.getSession().getAttribute("admin");//Hangi müdür kayıt edecekse onun id si altına kayıt eder
            clientsModel.setManagerModel(session);
            clientsModel.setRecordTime(java.sql.Date.valueOf(LocalDate.now()));
            clientsService.saveClient(clientsModel);
            return "redirect:/management/getManagemetPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping("/postClientDelete/{client_id}")
    public String deleteClient(@PathVariable(value = "client_id") Long client_id, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (client_id == null || client_id == 0) {
                return "redirect:/management/getManagemetPage";
            }
            clientsService.deleteClientById(client_id);
            return "redirect:/management/getManagemetPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping("/getClientInfoCreate/{client_id}")
    public String getClientInfo(Model model, @PathVariable(value = "client_id") Long client_id, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (client_id == null || client_id == 0) {
                return "redirect:/management/getManagemetPage";
            }
            ClientInfoModel clientInfoModel = new ClientInfoModel();
            clientsModel = clientsService.getClientById(client_id);
            ClientInfoDto clientInfoDto = new ClientInfoDto();

            clientInfoDto.trainerList = trainersService.getAllTrainers();
            clientInfoDto.clientInfoList = clientInfoService.getClientInfoByClientId(client_id);

            model.addAttribute("clientInfoDto", clientInfoDto);
            return "managementClientCreateInfo";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @PostMapping("/postClientInfoCreate")
    public String postInfoCreate(InfoCreateDto infoCreateDto, HttpServletRequest request) {
        try {
            ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
            if (sessionAdmin != null) {
                if (infoCreateDto == null) {
                    return "redirect:/management/getClientInfoCreate/" + clientsModel.getClientId();
                }
                ClientInfoModel clientInfoModel = new ClientInfoModel();

                clientInfoModel.setDay_of_week(infoCreateDto.getDay_of_Week());
                clientInfoModel.setDuration_in_minute(infoCreateDto.getDuration_in_Minute());
                clientInfoModel.setTraining_description(infoCreateDto.getTraining_Description());
                clientInfoModel.setTrainerId(infoCreateDto.getTrainerId());

                TrainersModel trainersModel = trainersService.getTrainerById(Long.valueOf(infoCreateDto.getTrainerId()));
                SessionsModel sessionsModel = sessionsService.getSessionById(Long.valueOf(trainersModel.getSessionsModel().getSessionId()));

                clientInfoModel.setSessionName(sessionsModel.getSessionName());
                clientInfoModel.setClientsModel(clientsModel);
                clientInfoService.saveClientInfo(clientInfoModel);
                return "redirect:/management/getClientInfoCreate/" + clientsModel.getClientId();
            } else {
                return "redirect:/login/Authorization";
            }
        } catch (Exception e) {
            return "redirect:/management/getClientInfoCreate/" + clientsModel.getClientId();
        }

    }

    @GetMapping("/getClientInfoDelete/{clientInfoId}")
    public String getClientInfoDelete(@PathVariable(value = "clientInfoId") Long clientInfoId, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (clientInfoId == null || clientInfoId == 0) {
                return "redirect:/management/getClientInfoCreate/" + clientsModel.getClientId();
            }
            clientInfoService.deleteClientInfoById(clientInfoId);
            return "redirect:/management/getClientInfoCreate/" + clientsModel.getClientId();
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping(value = "/getTrainerPage")
    public String getTrainerPage(Model model, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            TrainerDto trainerList = new TrainerDto();
            trainerList.trainersModels = trainersService.getAllTrainers();
            trainerList.sessionsModels = sessionsService.getAllSessions();
            model.addAttribute("trainerList", trainerList);
            return "managementTrainerCreate";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping("/getTrainerDelete/{trainer_id}")
    public String getDeleteTrainer(@PathVariable("trainer_id") Long trainer_id, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            trainersService.deleteTrainerById(trainer_id);
            return "redirect:/management/getTrainerPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @PostMapping(value = "/postTrainerCreate")
    public String postTrainerCreate(@Valid @ModelAttribute("trainersDtoModel") TrainerCreateDto trainersDtoModel, BindingResult bindingResult, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (trainersDtoModel == null) {
                return "redirect:/management/getTrainerPage";
            }

            TrainersModel trainersModel = new TrainersModel();

            trainersModel.setName(trainersDtoModel.getName());
            trainersModel.setSurName(trainersDtoModel.getSurName());
            trainersModel.setPassword(trainersDtoModel.getPassword());
            trainersModel.setTcNumber(trainersDtoModel.getTcNumber());
            trainersModel.setMail(trainersDtoModel.getMail());
            trainersModel.setPhoneNumber(trainersDtoModel.getPhoneNumber());
            trainersModel.setRecordTime(java.sql.Date.valueOf(LocalDate.now()));
            trainersModel.setGender(trainersDtoModel.getGender());

            ManagerModel session = (ManagerModel) request.getSession().getAttribute("admin");
            trainersModel.setManagerModel(session);

            trainersModel.setSessionsModel(sessionsService.getSessionById(Long.valueOf(trainersDtoModel.getSessionId())));
            trainersService.saveTrainer(trainersModel);
            return "redirect:/management/getTrainerPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping("/getTrainerUpdate/{trainer_id}")
    public String getTrainerUpdate(@PathVariable("trainer_id") Long trainer_id, Model model, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (trainer_id == null || trainer_id == 0) {
                return "redirect:/management/getTrainerPage";
            }
            TrainersModel trainersModel = trainersService.getTrainerById(trainer_id);
            TrainerUpdateDto trainerUpdateDto = new TrainerUpdateDto();
            trainerUpdateDto.trainersModel = trainersModel;
            Long a = trainersService.getTrainerById(trainer_id).getSessionsModel().getSessionId();
            SessionsModel sessionModel = sessionsService.getSessionById(a);
            trainerUpdateDto.sessionsModelList = sessionsService.getAllSessions();
            trainerUpdateDto.sessionsModelList.add(0, sessionModel);
            model.addAttribute("trainersDtoModel", trainerUpdateDto);
            return "managementTrainerUpdate";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @PostMapping(value = "/postTrainerUpdate")
    public String postTrainerUpdate(@Valid @ModelAttribute("trainersDtoModel") TrainerCreateDto trainersDtoModel, BindingResult bindingResult, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (trainersDtoModel == null) {
                return "redirect:/management/getTrainerPage";
            }

            TrainersModel trainersModel = new TrainersModel();
            trainersModel.setTrainerId(trainersDtoModel.getTrainerId());
            trainersModel.setName(trainersDtoModel.getName());
            trainersModel.setSurName(trainersDtoModel.getSurName());
            trainersModel.setPassword(trainersDtoModel.getPassword());
            trainersModel.setTcNumber(trainersDtoModel.getTcNumber());
            trainersModel.setMail(trainersDtoModel.getMail());
            trainersModel.setPhoneNumber(trainersDtoModel.getPhoneNumber());
            trainersModel.setRecordTime(java.sql.Date.valueOf(LocalDate.now()));
            trainersModel.setGender(trainersDtoModel.getGender());

            ManagerModel session = (ManagerModel) request.getSession().getAttribute("admin");
            trainersModel.setManagerModel(session);

            trainersModel.setSessionsModel(sessionsService.getSessionById(Long.valueOf(trainersDtoModel.getSessionId())));
            trainersService.saveTrainer(trainersModel);
            return "redirect:/management/getTrainerPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping("/getSessionPage")
    public String getSessionPage(Model model, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            List<SessionsModel> sessionsList = sessionsService.getAllSessions();
            model.addAttribute("sessionList", sessionsList);
            return "managementSessionIndex";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @PostMapping("/postSessionCreate")
    public String postSessionCreate(@ModelAttribute("sessionModel") SessionsModel sessionsModel, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (sessionsModel == null) {
                return "redirect:/management/getSessionPage";
            }
            sessionsService.saveSession(sessionsModel);
            return "redirect:/management/getSessionPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }

    @GetMapping("/getSessionDelete/{session_id}")
    public String getSessionDelete(@PathVariable("session_id") Long session_id, HttpServletRequest request) {
        ManagerModel sessionAdmin = (ManagerModel) request.getSession().getAttribute("admin");
        if (sessionAdmin != null) {
            if (session_id == null || session_id == 0) {
                return "redirect:/management/getSessionPage";
            }
            sessionsService.deleteSessionById(session_id);
            return "redirect:/management/getSessionPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }
}
