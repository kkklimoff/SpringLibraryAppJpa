package ru.klimov.project2.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klimov.project2.models.Client;
import ru.klimov.project2.repositories.ClientsRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientsService {

    private final ClientsRepository clientsRepository;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    public Client findOne(int id) {
        Optional<Client> clientFound = clientsRepository.findById(id);
        if (clientFound.isEmpty()) return null;
        Client client = clientFound.get();
        Hibernate.initialize(client.getBooks());
        Date currentDate = new Date();
        client.getBooks().forEach(b -> b.setOverdue(
                currentDate.after(new Date(b.getTakenAt().getTime()+ 10L*24*60*60*1000))
        ));
        return client;
    }

    @Transactional
    public void save(Client client) {
        clientsRepository.save(client);
    }

    @Transactional
    public void update(int id, Client updatedClient) {
        updatedClient.setId(id);
        clientsRepository.save(updatedClient);
    }

    @Transactional
    public void delete(int id) {
        clientsRepository.deleteById(id);
    }
}
