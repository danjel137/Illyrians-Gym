package com.gym.managmentsystem.controller;

import com.gym.managmentsystem.service.IClientInfoService;
import com.gym.managmentsystem.service.IClientsService;
import com.gym.managmentsystem.model.ClientInfoModel;
import com.gym.managmentsystem.model.ClientsModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("/client")
public class ClientController {

    IClientsService clientsService;
    IClientInfoService clientInfoService;

    public ClientController(IClientsService clientsService, IClientInfoService clientInfoService) {
        this.clientsService = clientsService;
        this.clientInfoService = clientInfoService;
    }

    @GetMapping("/getClientPage")
    public String showClient(Model model, HttpServletRequest request, HttpServletResponse response) {
        ClientsModel clientSession = (ClientsModel) request.getSession().getAttribute("client");
        if (clientSession != null) {
            String name = clientSession.getName() +"/"+ clientSession.getSurName() ;
            //add cookie to response
            Cookie cookie1 = new Cookie("UserInfo",String.valueOf( name));
            cookie1.setMaxAge(1 * 24 * 60 * 60);
            cookie1.setSecure(false);
            cookie1.setHttpOnly(false);
            response.addCookie(cookie1);

            //add cookie to response
            Cookie cookie2 = new Cookie("Role", "Client");
            cookie2.setMaxAge(1 * 24 * 60 * 60);
            cookie2.setSecure(false);
            cookie2.setHttpOnly(false);
            response.addCookie(cookie2);

            response.setContentType("text/html;charset=UTF-8");
            ClientsModel clientModel = clientsService.getClientById(clientSession.getClientId());
            model.addAttribute("client", clientModel);
            return "clientShow";
        } else {
            return "redirect:/login/Authorization";
        }

    }

    @GetMapping("/getClientInfoPage/{client_id}")
    public String showClientInfo(@PathVariable("client_id") Long client_id, Model model, HttpServletRequest request) {
        ClientsModel clientSession = (ClientsModel) request.getSession().getAttribute("client");
        if (clientSession != null) {
            if (client_id == clientSession.getClientId()) {
                ClientsModel clientModel = clientsService.getClientById(clientSession.getClientId());
                List<ClientInfoModel> clientInfoModelList = clientInfoService.getClientInfoByClientId(clientSession.getClientId());
                model.addAttribute("clientInfoModelList",clientInfoModelList);
                return "clientInfoShow";
            }
            return "redirect:/client/getClientPage";
        } else {
            return "redirect:/login/Authorization";
        }
    }
}
