package io.github.mivek.parser;

import java.util.regex.Pattern;

import io.github.mivek.utils.Regex;
import io.github.mivekinternationalization.Messages;

/**
 * @author mivek
 */
public final class RemarkParser {
    /** The instance of the parser. */
    private static final RemarkParser INSTANCE = new RemarkParser();

    /** No precipitation automated stations. */
    private static final Pattern AO1 = Pattern.compile("^AO1");
    /** Automated station with precipitation. */
    private static final Pattern AO2 = Pattern.compile("^AO2");
    /** Wind peak pattern. */
    private static final Pattern WIND_PEAK = Pattern.compile("^PK WND (\\d{3})(\\d{2,3})\\/(\\d{2})?(\\d{2})");
    /** Wind shift pattern. */
    private static final Pattern WIND_SHIFT = Pattern.compile("^WSHFT (\\d{2})?(\\d{2})");
    /** Wind shift fopa pattern. */
    private static final Pattern WIND_SHIFT_FROPA = Pattern.compile("^WSHFT (\\d{2})?(\\d{2}) FROPA");
    /** Tower visibility. */
    private static final Pattern TOWER_VISIBILITY = Pattern.compile("^TWR VIS ((\\d)*( )?(\\d?\\/?\\d))");
    /** Surface visibility. */
    private static final Pattern SURFACE_VISIBILITY = Pattern.compile("^SFC VIS ((\\d)*( )?(\\d?\\/?\\d))");
    /** Variable prevailing visibility. */
    private static final Pattern VARIABLE_PREVAILING_VISIBILITY = Pattern.compile("^VIS ((\\d)*( )?(\\d?\\/?\\d))V((\\d)*( )?(\\d?\\/?\\d))");
    /** Sector visibility. */
    private static final Pattern SECTOR_VISIBILITY = Pattern.compile("^VIS ([A-Z]{1,2}) ((\\d)*( )?(\\d?\\/?\\d))");
    /** Visibility at second location. */
    private static final Pattern SECOND_LOCATION_VISIBILITY = Pattern.compile("^VIS ((\\d)*( )?(\\d?\\/?\\d)) (\\w+)");
    /** Tornadic activity with beginning time. */
    private static final Pattern TORNADIC_ACTIVITY_BEGINNING = Pattern.compile("^(TORNADO|FUNNEL CLOUD|WATERSPOUT) (B(\\d{2})?(\\d{2}))( (\\d+)? ([A-Z]{1,2})?)?");
    /** Tornadic activity with ending time. */
    private static final Pattern TORNADIC_ACTIVITY_ENDING = Pattern.compile("^(TORNADO|FUNNEL CLOUD|WATERSPOUT) (E(\\d{2})?(\\d{2}))( (\\d+)? ([A-Z]{1,2})?)?");
    /** Tornadic activity with beginning and ending time. */
    private static final Pattern TORNADIC_ACTIVITY_BEG_END = Pattern.compile("^(TORNADO|FUNNEL CLOUD|WATERSPOUT) (B(\\d{2})?(\\d{2}))(E(\\d{2})?(\\d{2}))( (\\d+)? ([A-Z]{1,2})?)?");

    /***
     * Private constructor.
     */
    private RemarkParser() {
    }

    /**
     * @param pRemark the remark to parse.
     * @return the remark string
     */
    public String parse(final String pRemark) {
        String rmk = pRemark;
        StringBuilder sb = new StringBuilder();
        while (!rmk.equals("")) {
            if (Regex.find(AO1, rmk)) {
                sb.append(Messages.getInstance().getString("Remark.AO1")).append(" ");
                rmk = rmk.replaceFirst(AO1.pattern(), "").trim();
            } else if (Regex.find(AO2, rmk)) {
                sb.append(Messages.getInstance().getString("Remark.AO2")).append(" ");
                rmk = rmk.replaceFirst(AO2.pattern(), "").trim();
            } else if (Regex.find(WIND_PEAK, rmk)) {
                String[] windPeakParts = Regex.pregMatch(WIND_PEAK, rmk);
                sb.append(Messages.getInstance().getString("Remark.PeakWind", windPeakParts[1], windPeakParts[2], windPeakParts[3] == null ? "" : windPeakParts[3], windPeakParts[4]));
                sb.append(" ");
                rmk = rmk.replaceFirst(WIND_PEAK.pattern(), "").trim();
            } else if (Regex.find(WIND_SHIFT_FROPA, rmk)) {
                String[] windShiftParts = Regex.pregMatch(WIND_SHIFT_FROPA, rmk);
                sb.append(Messages.getInstance().getString("Remark.WindShift.FROPA", windShiftParts[1] == null ? "" : windShiftParts[1], windShiftParts[2]));
                sb.append(" ");
                rmk = rmk.replaceFirst(WIND_SHIFT_FROPA.pattern(), "");
            } else if (Regex.find(WIND_SHIFT, rmk)) {
                String[] windShiftParts = Regex.pregMatch(WIND_SHIFT, rmk);
                sb.append(Messages.getInstance().getString("Remark.WindShift", windShiftParts[1] == null ? "" : windShiftParts[1], windShiftParts[2]));
                sb.append(" ");
                rmk = rmk.replaceFirst(WIND_SHIFT.pattern(), "").trim();
            } else if (Regex.find(TOWER_VISIBILITY, rmk)) {
                String[] towerVisibilityParts = Regex.pregMatch(TOWER_VISIBILITY, rmk);
                sb.append(Messages.getInstance().getString("Remark.TowerVisibility", towerVisibilityParts[1])).append(" ");
                rmk = rmk.replaceFirst(TOWER_VISIBILITY.pattern(), "").trim();
            } else if (Regex.find(SURFACE_VISIBILITY, rmk)) {
                String[] surfaceVisivilityParts = Regex.pregMatch(SURFACE_VISIBILITY, rmk);
                sb.append(Messages.getInstance().getString("Remark.SurfaceVisibility", surfaceVisivilityParts[1])).append(" ");
                rmk = rmk.replaceFirst(SURFACE_VISIBILITY.pattern(), "").trim();
            } else if (Regex.find(VARIABLE_PREVAILING_VISIBILITY, rmk)) {
                String[] visibilityParts = Regex.pregMatch(VARIABLE_PREVAILING_VISIBILITY, rmk);
                sb.append(Messages.getInstance().getString("Remark.VariablePrevailingVisibility", visibilityParts[1], visibilityParts[5])).append(" ");
                rmk = rmk.replaceFirst(VARIABLE_PREVAILING_VISIBILITY.pattern(), "").trim();
            } else if (Regex.find(SECTOR_VISIBILITY, rmk)) {
                String[] sectorVisibilityParts = Regex.pregMatch(SECTOR_VISIBILITY, rmk);
                sb.append(Messages.getInstance().getString("Remark.SectorVisibility", Messages.getInstance().getString("Converter." + sectorVisibilityParts[1]), sectorVisibilityParts[2]))
                .append(" ");
                rmk = rmk.replaceFirst(SECTOR_VISIBILITY.pattern(), "").trim();
            } else if (Regex.find(SECOND_LOCATION_VISIBILITY, rmk)) {
                String[] secondLocationVisibilityParts = Regex.pregMatch(SECOND_LOCATION_VISIBILITY, rmk);
                sb.append(Messages.getInstance().getString("Remark.SecondLocationVisibility", secondLocationVisibilityParts[1], secondLocationVisibilityParts[5])).append(" ");
                rmk = rmk.replaceFirst(SECOND_LOCATION_VISIBILITY.pattern(), "").trim();
            } else if (Regex.find(TORNADIC_ACTIVITY_BEG_END, rmk)) {
                String[] tornadicParts = Regex.pregMatch(TORNADIC_ACTIVITY_BEG_END, rmk);
                sb.append(Messages.getInstance().getString("Remark.TornadicActivity.BegEnd", Messages.getInstance().getString("Remark." + tornadicParts[1].replace(" ", "")),
                        tornadicParts[3] == null ? "" : tornadicParts[3], tornadicParts[4], tornadicParts[6] == null ? "" : tornadicParts[6], tornadicParts[7], tornadicParts[9],
                                Messages.getInstance().getString("Converter." + tornadicParts[10]))).append(" ");
                rmk = rmk.replaceFirst(TORNADIC_ACTIVITY_BEG_END.pattern(), "").trim();
            } else if (Regex.find(TORNADIC_ACTIVITY_BEGINNING, rmk)) {
                String[] tornadicParts = Regex.pregMatch(TORNADIC_ACTIVITY_BEGINNING, rmk);
                sb.append(Messages.getInstance().getString("Remark.TornadicActivity.Beginning", Messages.getInstance().getString("Remark." + tornadicParts[1].replace(" ", "")),
                        tornadicParts[3] == null ? "" : tornadicParts[3], tornadicParts[4], tornadicParts[6], Messages.getInstance().getString("Converter." + tornadicParts[7]))).append(" ");
                rmk = rmk.replaceFirst(TORNADIC_ACTIVITY_BEGINNING.pattern(), "").trim();
            } else if (Regex.find(TORNADIC_ACTIVITY_ENDING, rmk)) {
                String[] tornadicParts = Regex.pregMatch(TORNADIC_ACTIVITY_ENDING, rmk);
                sb.append(Messages.getInstance().getString("Remark.TornadicActivity.Ending", Messages.getInstance().getString("Remark." + tornadicParts[1].replace(" ", "")),
                        tornadicParts[3] == null ? "" : tornadicParts[3], tornadicParts[4], tornadicParts[6], Messages.getInstance().getString("Converter." + tornadicParts[7]))).append(" ");
                rmk = rmk.replaceFirst(TORNADIC_ACTIVITY_ENDING.pattern(), "").trim();
            } else {
                String[] strSlit = rmk.split(" ", 2);
                sb.append(strSlit[0]);
                rmk = strSlit.length == 1 ? "" : strSlit[1];
            }
        }
        return sb.toString();
    }

    /**
     * @return the instance of the parser.
     */
    public static RemarkParser getInstance() {
        return INSTANCE;
    }
}