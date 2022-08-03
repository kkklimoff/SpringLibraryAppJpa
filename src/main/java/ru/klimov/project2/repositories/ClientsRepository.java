package ru.klimov.project2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klimov.project2.models.Client;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Integer> {
}
