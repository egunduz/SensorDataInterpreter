package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.entity.FailedMessageEntity;
import com.demo.SensorDataInterpreter.repository.FailedMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FailedMessageService {

    private final FailedMessageRepository repository;

    public void save(FailedMessageEntity entity) {
        repository.save(entity);
    }

    public List<FailedMessageEntity> findAll() {
        return repository.findAll();
    }
}
