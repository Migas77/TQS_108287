package com.tqs108287.App.geocoding;

import com.tqs108287.App.connection.ISimpleHttpClient;
import com.tqs108287.App.connection.TqsBasicHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @InjectMocks
    AddressResolverService resolver;

    @Mock
    ISimpleHttpClient httpClient;

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        // done: implement test; remove Disabled annotation
        double latitude = 40.63436;
        double longitude = -8.65616;
        String uri = String.format("https://www.mapquestapi.com/geocoding/v1/reverse?key=%s&location=%.5f%%2C%.5f&outFormat=json&thumbMaps=false",ConfigUtils.getPropertyFromConfig("mapquest_key"), latitude, longitude);

        when(httpClient.doHttpGet(uri))
                .thenReturn("{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2024 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2024 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.63436,\"lng\":-8.65616}},\"locations\":[{\"street\":\"Avenida da Universidade\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-489\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"L\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63437,\"lng\":-8.65625},\"displayLatLng\":{\"lat\":40.63437,\"lng\":-8.65625},\"mapUrl\":\"\"}]}]}");

        Optional<Address> result = resolver.findAddressForLocation(latitude, longitude);

        //return
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        /// done: implement test
        double latitude = -361;
        double longitude = -361;
        String uri = String.format("https://www.mapquestapi.com/geocoding/v1/reverse?key=%s&location=%.5f%%2C%.5f&outFormat=json&thumbMaps=false",ConfigUtils.getPropertyFromConfig("mapquest_key"), latitude, longitude);

        when(httpClient.doHttpGet(uri)).thenReturn("{\"info\":{\"statuscode\":400,\"copyright\":{\"text\":\"© 2024 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2024 MapQuest, Inc.\"},\"messages\":[\"Illegal argument from request: Invalid LatLng specified.\"]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{},\"locations\":[]}]}");

        Optional<Address> result = resolver.findAddressForLocation( latitude,longitude);
        // verify no valid result
        assertFalse( result.isPresent());

    }
}