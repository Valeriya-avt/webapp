package ru.msu.cmc.webapp.Dao;

import ru.msu.cmc.webapp.Models.Client;

import java.util.List;

public interface ClientDao {
    void createClient(Client client);
    void updateClient(Client client);
    void deleteClient(Client client);
    Client getClientByID(int id);
    Client getClientByLogin(String login);
    List<Client> getClientsByName(String name);
    List<Client> getClientsBySurname(String surname);
    List<Client> getAllClient();
}
