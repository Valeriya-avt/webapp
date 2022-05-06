package ru.msu.cmc.webapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.Dao.AdminDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Models.Client;

@Controller
public class RegistrationController {
    ClientDao clientDao = new ClientDaoImpl();
    AdminDao adminDao = new AdminDaoImpl();

    @GetMapping("/registration")
    public String registrationPage(@CookieValue(value = "login", defaultValue = "defaultLogin") String client_login,
                                   @CookieValue(value = "password", defaultValue = "defaultPassword") String client_password,
                                   Model model) {
        Client client = clientDao.getClientByLogin(client_login);
        if (client != null) {
            if (client.getClient_password().equals(client_password)) {
                return "redirect:/logged";
            }
        }
        return "registration";
    }

    @PostMapping("/registered")
    public String checkRegistrationData(@RequestParam(name = "name") String name,
                                 @RequestParam(name = "surname") String surname,
                                 @RequestParam(name = "address") String address,
                                 @RequestParam(name = "phone_number") String phone_number,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "client_login") String client_login,
                                 @RequestParam(name = "client_password") String client_password,
                                 Model model) {
        Client client = clientDao.getClientByLogin(client_login);
        if (client != null) {
            model.addAttribute("error_msg", "This username is already taken!");
            return "errorPage";
        }
        Client new_client = new Client(name, surname, address, phone_number, email, client_login, client_password);
        try {
            clientDao.createClient(new_client);
        } catch (Exception e) {
            model.addAttribute("error_msg", e.getMessage());
            return "errorPage";
        }
        Client clientForCheck = clientDao.getClientByID(new_client.getClient_id());
        if (clientForCheck == null) {
            model.addAttribute("error_msg", "Account creation error1");
            return "errorPage";
        }
        if (clientForCheck.equals(new_client)) {
            return "congratulationsOnRegistration";
        }
        model.addAttribute("error_msg", "Account creation error2");
        return "errorPage";
    }
}
