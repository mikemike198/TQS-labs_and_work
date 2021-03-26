package geocoding;

import connection.ISimpleHttpClient;
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
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {


    @Test
    void whenGoodAlboiGps_returnAddress() throws ParseException, IOException, URISyntaxException {

        //todo

        //test
        ISimpleHttpClient mock = mock(ISimpleHttpClient.class);
        AddressResolver resolver = new AddressResolver(mock);

        String url = "http://open.mapquestapi.com/geocoding/v1/reverse?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&location=40.640661%2C-8.656688&includeRoadMetadata=true";
        String jsonResponse = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2021 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2021 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.6318,\"lng\":-8.658}},\"locations\":[{\"street\":\"Parque Estacionamento da Reitoria - Univerisdade de Aveiro\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-193\",\"geocodeQualityCode\":\"P1AAA\",\"geocodeQuality\":\"POINT\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"displayLatLng\":{\"lat\":40.631803,\"lng\":-8.657881},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.6318025,-8.657881|marker-sm-50318A-1&scalebar=true&zoom=15&rand=-1691202703\",\"roadMetadata\":null}]}]}";
        when(mock.get(url)).thenReturn(jsonResponse);

        Address result = resolver.findAddressForLocation(40.640661, -8.656688);
        //return
        assertEquals( result, new Address( "Parque Estacionamento da Reitoria - Univerisdade de Aveiro", "Glória e Vera Cruz", "Centro", "3810-193", null) );
        verify(mock, times(1)).get(url);
    }


    @Test
    public void whenResponseIsNull_throwNullPointerException() throws IOException, URISyntaxException, ParseException {

        //todo
        ISimpleHttpClient mock = mock(ISimpleHttpClient.class);
        AddressResolver resolver = new AddressResolver(mock);

        String url = "http://open.mapquestapi.com/geocoding/v1/reverse?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&location=40.640661%2C-8.656688&includeRoadMetadata=true";

        when(mock.get(url)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> resolver.findAddressForLocation(40.640661, -8.656688));

        verify(mock, times(1)).get(url);

    }

    @Test
    public void whenNullResponseField_throwParseException() throws IOException, URISyntaxException, ParseException {

        //todo
        ISimpleHttpClient mock = mock(ISimpleHttpClient.class);
        AddressResolver resolver = new AddressResolver(mock);

        String url = "http://open.mapquestapi.com/geocoding/v1/reverse?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&location=40.640661%2C-8.656688&includeRoadMetadata=true";
        String jsonResponse = "{" +
                "\"info\":{" +
                    "\"statuscode\":0," +
                    "\"copyright\":{" +
                        "\"text\":\"\\u00A9 2021 MapQuest, Inc.\"," +
                        "\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\"," +
                        "\"imageAltText\":\"\\u00A9 2021 MapQuest, Inc.\"}," +
                    "\"messages\":[]}," +
                "\"options\":{" +
                    "\"maxResults\":1," +
                    "\"thumbMaps\":true," +
                    "\"ignoreLatLngInput\":false}," +
                "}";
        when(mock.get(url)).thenReturn(jsonResponse);

        assertThrows(NullPointerException.class, () -> resolver.findAddressForLocation(40.640661, -8.656688));

        verify(mock, times(1)).get(url);

    }
}