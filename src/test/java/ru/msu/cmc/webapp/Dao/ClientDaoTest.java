package ru.msu.cmc.webapp.Dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.Impl.ClientDaoImpl;
import ru.msu.cmc.webapp.Models.Client;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientDaoTest {
    private final ClientDao clientDao = new ClientDaoImpl();

    @Test
    public void testCreateClient() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        assertThat(clientDao.getClientByID(client.getClient_id())).usingRecursiveComparison().isEqualTo(client);
        clientDao.deleteClient(client);
    }

    @Test
    public void testUpdateClient() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        assertThat(clientDao.getClientByID(client.getClient_id())).usingRecursiveComparison().isEqualTo(client);
        client.setName("Александр");
        clientDao.updateClient(client);
        assertThat(clientDao.getClientByID(client.getClient_id())).usingRecursiveComparison().isEqualTo(client);
        clientDao.deleteClient(client);
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        assertThat(clientDao.getClientByID(client.getClient_id())).usingRecursiveComparison().isEqualTo(client);
        clientDao.deleteClient(client);
        Assert.assertNull(clientDao.getClientByID(client.getClient_id()));
    }

    @Test
    public void testGetClientByLogin() {
        Client client = new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 3-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erbof789uh");
        clientDao.createClient(client);
        Assert.assertEquals("VIvanov", clientDao.getClientByID(client.getClient_id()).getClient_login());
        assertThat(clientDao.getClientByLogin("VIvanov")).usingRecursiveComparison().isEqualTo(client);
        clientDao.deleteClient(client);
    }

    @Test
    public void testGetClientsByName() {
        List<Client> expectedClients = List.of(
                new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 4-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erof789uh"),
                new Client("Василий", "Степанов", "Россия, г. Зеленогорск, 5-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VStepanov", "erbf789uh"),
                new Client("Василий", "Андреев", "Россия, г. Зеленогорск, 6-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VAndreev", "erbo789uh")
        );
        clientDao.createClient(expectedClients.get(0));
        clientDao.createClient(expectedClients.get(1));
        clientDao.createClient(expectedClients.get(2));
        List<Client> actualClients = clientDao.getClientsByName("Василий");

        assertThat(actualClients.get(0)).usingRecursiveComparison().isEqualTo(expectedClients.get(0));
        assertThat(actualClients.get(1)).usingRecursiveComparison().isEqualTo(expectedClients.get(1));
        assertThat(actualClients.get(2)).usingRecursiveComparison().isEqualTo(expectedClients.get(2));

        clientDao.deleteClient(expectedClients.get(0));
        clientDao.deleteClient(expectedClients.get(1));
        clientDao.deleteClient(expectedClients.get(2));
    }

    @Test
    public void testGetClientsBySurname() {
        List<Client> expectedClients = List.of(
                new Client("Иван", "Иванов", "Россия, г. Зеленогорск, 4-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VIvanov", "erof789uh"),
                new Client("Василий", "Иванов", "Россия, г. Зеленогорск, 5-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VStepanov", "erbf789uh"),
                new Client("Андрей", "Иванов", "Россия, г. Зеленогорск, 6-й Лесной пер., 5", "+79007387094", "vivanov@gmail.com", "VAndreev", "erbo789uh")
        );
        clientDao.createClient(expectedClients.get(0));
        clientDao.createClient(expectedClients.get(1));
        clientDao.createClient(expectedClients.get(2));
        List<Client> actualClients = clientDao.getClientsBySurname("Иванов");

        assertThat(actualClients.get(0)).usingRecursiveComparison().isEqualTo(expectedClients.get(0));
        assertThat(actualClients.get(1)).usingRecursiveComparison().isEqualTo(expectedClients.get(1));
        assertThat(actualClients.get(2)).usingRecursiveComparison().isEqualTo(expectedClients.get(2));

        clientDao.deleteClient(expectedClients.get(0));
        clientDao.deleteClient(expectedClients.get(1));
        clientDao.deleteClient(expectedClients.get(2));
    }
}
