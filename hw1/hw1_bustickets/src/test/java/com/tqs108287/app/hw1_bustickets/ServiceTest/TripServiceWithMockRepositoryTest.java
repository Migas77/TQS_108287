package com.tqs108287.app.hw1_bustickets.ServiceTest;

import com.tqs108287.app.hw1_bustickets.repositories.TripRepository;
import com.tqs108287.app.hw1_bustickets.service.impl.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TripServiceWithMockRepositoryTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;

    @Test
    void test(){

    }

}
