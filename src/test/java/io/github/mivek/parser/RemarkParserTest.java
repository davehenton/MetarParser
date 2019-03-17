package io.github.mivek.parser;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import io.github.mivekinternationalization.Messages;

public class RemarkParserTest {
    RemarkParser fSut;

    @Before
    public void setUp() {
        fSut = RemarkParser.getInstance();
    }

    @Test
    public void testParseWithAO1() {
        // GIVEN a RMK with AO1 token.
        String code = "Token AO1 End of remark";
        // WHEN parsing the remark.
        String remark = fSut.parse(code);
        // THEN the token is parsed and translated
        assertNotNull(remark);
        assertThat(remark, containsString(Messages.getInstance().getString("Remark.AO1")));
    }

    @Test
    public void testParseWithAO2() {
        // GIVEN a RMK with AO2 token
        String code = "Token AO2 End of remark";
        // WHEN parsing the remark.
        String remark = fSut.parse(code);
        // THEN the token is parsed and translated
        assertNotNull(remark);
        assertThat(remark, containsString(Messages.getInstance().getString("Remark.AO2")));
    }

    @Test
    public void testParseWithWindPeakAtTheHour() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a RMK with Peak wind at the hour.
        String code = "AO1 PK WND 28045/15";
        // WHEN parsing the remark.
        String remark = fSut.parse(code);
        // THEN the token is parsed and translated
        assertNotNull(remark);
        assertThat(remark, containsString("peak wind of 45 knots from 280 degrees at :15"));
    }

    @Test
    public void testParseWithWindPeakAtAnotherHour() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a RMK with Peak wind at the hour.
        String code = "AO1 PK WND 28045/1515";
        // WHEN parsing the remark.
        String remark = fSut.parse(code);
        // THEN the token is parsed and translated
        assertNotNull(remark);
        assertThat(remark, containsString("peak wind of 45 knots from 280 degrees at 15:15"));
    }

    @Test
    public void testParseWindShiftAtTheHour() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a RMK with Wind shift at the hour
        String code = "AO1 WSHFT 30";
        // WHEN parsing the remark.
        String remark = fSut.parse(code);
        // THEN the remark contains the decoded wind shift
        assertNotNull(remark);
        assertThat(remark, containsString("wind shift at :30"));
    }

    @Test
    public void testParseWindShiftWithFrontal() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a RMK with wind shift with frontal passage
        String code = "AO1 WSHFT 1530 FROPA";
        // WHEN parsing the remark
        String remark = fSut.parse(code);
        // THEN the remark contains the decoded wind shift fropa
        assertNotNull(remark);
        assertThat(remark, containsString("wind shift accompanied by frontal passage at 15:30"));
    }

    @Test
    public void testParseTowerVisibility() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a rmk with tower visibility
        String code = "AO1 TWR VIS 16 1/2";
        // WHEN parsing the remark
        String remark = fSut.parse(code);
        // THEN the tower visibility is decoded
        assertThat(remark, containsString("tower visibility of 16 1/2 statute miles"));
    }

    @Test
    public void testParseSurfaceVisibility() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a rmk with surface visibility
        String code = "AO1 SFC VIS 16 1/2";
        // WHEN parsing the remark
        String remark = fSut.parse(code);
        // THEN the surface visibility is decoded
        assertThat(remark, containsString("surface visibility of 16 1/2 statute miles"));
    }

    @Test
    public void testParsePrevailingVisibility() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a rmk with variable prevailing visibility
        String code = "AO1 VIS 1/2V2";
        // WHEN parsing the remark
        String remark = fSut.parse(code);
        // THEN the variable prevailing visibility is decoded
        assertThat(remark, containsString("variable prevailing visibility between 1/2 and 2 SM"));
    }

    @Test
    public void testParseSectorVisibility() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a rmk with sector visibility
        String code = "AO1 VIS NE 2 1/2";
        // WHEN parsing the remark
        String remark = fSut.parse(code);
        // THEN the sector visibility is decoded
        assertThat(remark, containsString("visibility of 2 1/2 SM in the North East direction"));
    }

    @Test
    public void testParseSecondLocationVisibility() {
        Messages.getInstance().setLocale(Locale.ENGLISH);
        // GIVEN a rmk with visibility at second location
        String code = "AO1 VIS 2 1/2 RWY11";
        // WHEN parsing the remark
        String remark = fSut.parse(code);
        // THEN the visibility at second location is decoded
        assertThat(remark, containsString("visibility of 2 1/2 SM mesured by a second sensor located at RWY11"));
    }

}
