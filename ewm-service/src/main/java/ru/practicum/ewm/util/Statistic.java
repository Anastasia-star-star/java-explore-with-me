package ru.practicum.ewm.util;

import ru.practicum.ewm.StatsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Statistic {

    public final StatsClient statsClient;

    @Autowired
    public Statistic(StatsClient statsClient) {
        this.statsClient = statsClient;
    }

}