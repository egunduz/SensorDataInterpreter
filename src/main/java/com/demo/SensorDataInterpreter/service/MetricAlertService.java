package com.demo.SensorDataInterpreter.service;

import com.demo.SensorDataInterpreter.entity.MetricAlertEntity;
import com.demo.SensorDataInterpreter.repository.MetricAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricAlertService {

    private final MetricAlertRepository repository;

    public void save(MetricAlertEntity entity) {
        repository.save(entity);
    }

    public List<MetricAlertEntity> getAll() {
        return repository.findAll();
    }
}
