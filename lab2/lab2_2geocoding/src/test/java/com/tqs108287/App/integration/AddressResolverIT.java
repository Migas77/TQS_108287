package com.tqs108287.App.integration;

import com.tqs108287.App.connection.ISimpleHttpClient;
import com.tqs108287.App.connection.TqsBasicHttpClient;
import com.tqs108287.App.geocoding.Address;
import com.tqs108287.App.geocoding.AddressResolverService;
import com.tqs108287.App.geocoding.ConfigUtils;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AddressResolverIT {

    AddressResolverService resolver;
    ISimpleHttpClient httpClient;


    @BeforeEach
    public void init(){
        httpClient = new TqsBasicHttpClient();
        resolver = new AddressResolverService(httpClient);
    }


    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        // todo
        double latitude = 40.63436;
        double longitude = -8.65616;

        Optional<Address> result = resolver.findAddressForLocation(latitude, longitude);

        //return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        // todo
        double latitude = -361;
        double longitude = -361;

        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        // verify no valid result
        assertFalse( result.isPresent());
        
    }

}
