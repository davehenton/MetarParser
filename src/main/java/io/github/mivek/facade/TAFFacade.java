package io.github.mivek.facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.mivek.exception.ErrorCodes;
import io.github.mivek.exception.ParseException;
import io.github.mivek.model.TAF;
import io.github.mivek.parser.TAFParser;

/**
 * Facade for TAF.
 * @author mivek
 */
public final class TAFFacade extends AbstractWeatherCodeFacade<TAF> {
    /** URL to retrieve the TAF from. */
    private static final String NOAA_TAF_URL = "https://tgftp.nws.noaa.gov/data/forecasts/taf/stations/";
    /**
     * The instance of the facade.
     */
    private static final TAFFacade INSTANCE = new TAFFacade();

    /**
     * Constructor.
     */
    private TAFFacade() {
        super(TAFParser.getInstance());
    }

    @Override
    public TAF decode(final String pCode) throws ParseException {
        return getParser().parse(pCode);
    }

    @Override
    public TAF retrieveFromAirport(final String pIcao) throws IOException, URISyntaxException, ParseException {
        if (pIcao.length() != AbstractWeatherCodeFacade.ICAO) {
            throw new ParseException(ErrorCodes.ERROR_CODE_INVALID_ICAO);
        }
        String website = NOAA_TAF_URL + pIcao.toUpperCase() // $NON-NLS-1$
        + ".TXT"; //$NON-NLS-1$
        URL url = new URL(website);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            // Throw the first line since it is not part of the TAF event.
            br.readLine();
            br.lines().forEach(currentLine -> sb.append(currentLine.replaceAll("\\s{2,}", "")).append("\n"));
            return getParser().parse(format(sb.toString()));
        }
    }

    /**
     * Reformat the first line of the code.
     * @param pCode the first line of the TAF event.
     * @return the formated taf code.
     * @throws ParseException when an error occurs.
     */
    protected String format(final String pCode) throws ParseException {
        String[] lines = pCode.split("\n");
        if (!TAFParser.TAF.equals(lines[0].trim())) {
            return pCode;
        }
        if ("AMD TAF".equals(lines[1].trim())) {
            List<String> list = new ArrayList<>(Arrays.asList(lines));
            list.remove(1);
            lines = list.toArray(new String[0]);
        }
        // Case of TAF AMD, the 2 first lines must be merged.
        return Arrays.stream(lines).reduce((x, y) -> x + y + "\n").orElseThrow(() -> new ParseException(ErrorCodes.ERROR_CODE_INVALID_MESSAGE));
    }

    /**
     * @return the instance of the facade.
     */
    public static TAFFacade getInstance() {
        return INSTANCE;
    }
}
