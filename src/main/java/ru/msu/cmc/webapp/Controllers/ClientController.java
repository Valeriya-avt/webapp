package ru.msu.cmc.webapp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webapp.Dao.AdminDao;
import ru.msu.cmc.webapp.Dao.BookDao;
import ru.msu.cmc.webapp.Dao.ClientDao;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.BookDaoImpl;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Models.Admin;
import ru.msu.cmc.webapp.Models.Client;

import java.util.List;

@Controller
public class ClientController {
    AdminDao adminDao = new AdminDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
    BookDao bookDao = new BookDaoImpl();

    @GetMapping("/logged/personal")
    public String clientPage(@RequestParam(name = "client_id") int client_id,
                             @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                             @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                             Model model) {
        Client requested_client = clientDao.getClientByID(client_id);
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                model.addAttribute("client", requested_client);
                return "clientForAdmin";
            }
        }
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                model.addAttribute("client", requested_client);
                return "client";
            }
        }
        model.addAttribute("error_msg", "It's not admin or client");
        return "errorPage";
    }

    @GetMapping("/editClient")
    public String editClientPage(@RequestParam(name = "client_id") int client_id,
                                 @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                                 @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                 Model model) {
        Client requested_client = clientDao.getClientByID(client_id);
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                model.addAttribute("client", requested_client);
                return "editClient";
            }
        }
        return "errorPage";
    }

    @PostMapping("/updateClient")
    public String updateClientPage(@RequestParam(name = "client_id") int client_id,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "surname") String surname,
                                   @RequestParam(name = "address") String address,
                                   @RequestParam(name = "phone_number") String phone_number,
                                   @RequestParam(name = "email") String email,
                                   @RequestParam(name = "client_login") String client_login,
                                   @RequestParam(name = "client_password") String client_password,
                                   @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                                   @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                   Model model) {
        Client update_client = clientDao.getClientByID(client_id);
        update_client.setName(name);
        update_client.setSurname(surname);
        update_client.setAddress(address);
        update_client.setPhone_number(phone_number);
        update_client.setEmail(email);
        update_client.setClient_login(client_login);
        update_client.setClient_password(client_password);
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                clientDao.updateClient(update_client);
                if (clientDao.getClientByID(update_client.getClient_id()) != null) {
                    model.addAttribute("client", update_client);
                    return "clientForAdmin";
                }
                model.addAttribute("error_msg", "Client update error");
                return "errorPage";
            }
        }
        Client client = clientDao.getClientByLogin(login);
        if (client != null) {
            if (client.getClient_password().equals(password)) {
                clientDao.updateClient(update_client);
                if (clientDao.getClientByID(update_client.getClient_id()) != null) {
                    model.addAttribute("client", update_client);
                    return "client";
                }
                model.addAttribute("error_msg", "Client update error");
                return "errorPage";
            }
        }
        model.addAttribute("error_msg", "Client edit error");
        return "errorPage";
    }

    @GetMapping("/clients")
    public String clientsPage(@CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                              @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                              Model model) {
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                List<Client> clients = clientDao.getAllClient();
                model.addAttribute("clients", clients);
                return "clients";
            }
        }
        model.addAttribute("error_msg", "Error accessing the list of clients");
        return "errorPage";
    }

    @GetMapping("/addClient")
    public String createBookPage(@CookieValue(value = "login", defaultValue = "defaultLogin")String login,
                                 @CookieValue(value = "password", defaultValue = "defaultPassword") String password) {
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                return "addClient";
            }
        }
        return "errorPage";
    }

    @PostMapping("/createClient")
    public String createClientPage(@RequestParam(name = "name") String name,
                                   @RequestParam(name = "surname") String surname,
                                   @RequestParam(name = "address") String address,
                                   @RequestParam(name = "phone_number") String phone_number,
                                   @RequestParam(name = "email") String email,
                                   @RequestParam(name = "client_login") String client_login,
                                   @RequestParam(name = "client_password") String client_password,
                                   @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                                   @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                   Model model) {
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                Client new_client = new Client(name, surname, address, phone_number, email, client_login, client_password);
                clientDao.createClient(new_client);
                return "congratulationsOnAddClient";
            }
        }
        return "errorPage";
    }

    @GetMapping("/deleteClient")
    public String deleteClientPage(@RequestParam(name = "client_id") int client_id,
                                   @CookieValue(value = "login", defaultValue = "defaultLogin") String login,
                                   @CookieValue(value = "password", defaultValue = "defaultPassword") String password,
                                   Model model) {
        Client client = clientDao.getClientByID(client_id);
        Admin admin = adminDao.getAdminByLogin(login);
        if (admin != null) {
            if (admin.getAdmin_password().equals(password)) {
                clientDao.deleteClient(client);
                return "deleteClient";
            }
        }
        return "errorPage";
    }
}
