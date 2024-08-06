package ru.practicum.ewm.request;

import ru.practicum.ewm.request.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RequestRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

    @Test
    void findAllByRequesterId() {
    }

    @Test
    void findAllByEventId() {
    }

    @Test
    void findAllByIdIn() {
    }

    @Test
    void countByRequesterIdAndEventId() {
    }

    @Test
    void countByEventIdAndStatus() {
    }

}