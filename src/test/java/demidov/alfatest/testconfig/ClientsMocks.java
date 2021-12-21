package demidov.alfatest.testconfig;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.springframework.util.StreamUtils.copyToString;

public class ClientsMocks {

    public static void setupRecentExchangeResponse(WireMockServer mockServer) throws IOException {
        mockServer.stubFor(WireMock.get("/?app_id=30aaf5f53e3c4fcc8a4e57d950c76b71&symbols=RUB")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ClientsMocks.class.getClassLoader().getResourceAsStream("payload/recent.json"),
                                        StandardCharsets.UTF_8)
                        )));
    }

    public static void setupHistoricalExchangeResponse(WireMockServer mockServer) throws IOException {
        OffsetDateTime todayInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        LocalDate yesterdayInUTC = todayInUTC.minusDays(1).toLocalDate();
        String date = yesterdayInUTC.toString();

        mockServer.stubFor(WireMock.get("/" + date + ".json?app_id=30aaf5f53e3c4fcc8a4e57d950c76b71&symbols=RUB")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ClientsMocks.class.getClassLoader().getResourceAsStream("payload/historical.json"),
                                        StandardCharsets.UTF_8)
                        )));
    }

    public static void setupGiphyResponse(WireMockServer mockServer) throws IOException {
        mockServer.stubFor(WireMock.get("/?api_key=460LCWHTb1C4B48SAkZySSIHHq6Is5fw&q=rich&rating=pg")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(ClientsMocks.class.getClassLoader().getResourceAsStream("payload/giphy.json"),
                                        StandardCharsets.UTF_8)
                        )));
    }


}
