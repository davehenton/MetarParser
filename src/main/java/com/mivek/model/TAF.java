package com.mivek.model;

import java.util.ArrayList;
import java.util.List;

import com.mivek.model.trend.BECMGTafTrend;
import com.mivek.model.trend.FMTafTrend;
import com.mivek.model.trend.TEMPOChange;

/**
 * Class representing a TAF.
 * @author mivek
 */
public class TAF extends AbstractWeatherCode {
    /**
     * The valididty of the TAF.
     */
    private Validity fValidity;
    /**
     * List of BECMG changes.
     */
    private List<BECMGTafTrend> fBECMGs;
    /**
     * List of From changes.
     */
    private List<FMTafTrend> fFMs;
    /**
     * List of Tempos changes.
     */
    private List<TEMPOChange> fTempos;
    /**
     * List of probability changes.
     */
    private List<PROBChange> fProbs;
    /**
     * Probability of the metar.
     */
    private Integer fProbability;

    /**
     * Constructor.
     */
    public TAF() {
        super();
        fBECMGs = new ArrayList<>();
        fFMs = new ArrayList<>();
        fTempos = new ArrayList<>();
        fProbs = new ArrayList<>();
    }

    /**
     * @return the validity
     */
    public Validity getValidity() {
        return fValidity;
    }

    /**
     * @param pValidity the validity to set
     */
    public void setValidity(final Validity pValidity) {
        fValidity = pValidity;
    }

    /**
     * @return the bECMGs
     */
    public List<BECMGTafTrend> getBECMGs() {
        return fBECMGs;
    }

    /**
     * @return the fMs
     */
    public List<FMTafTrend> getFMs() {
        return fFMs;
    }

    /**
     * @return the probs
     */
    public List<PROBChange> getProbs() {
        return fProbs;
    }

    /**
     * Adds a tempo change to the list.
     * @param pChange the change to add.
     */
    public void addTempo(final TEMPOChange pChange) {
        fTempos.add(pChange);
    }

    /**
     * Adds a PROB Change to the list.
     * @param pChange the change to add.
     */
    public void addProb(final PROBChange pChange) {
        fProbs.add(pChange);
    }

    /**
     * @return the probability
     */
    public Integer getProbability() {
        return fProbability;
    }

    /**
     * @param pProbability
     * the probability to set
     */
    public void setProbability(final Integer pProbability) {
        fProbability = pProbability;
    }

    /**
     * Adds a BECMG to the list.
     * @param pChange the change to add.
     */
    public void addBECMG(final BECMGTafTrend pChange) {
        fBECMGs.add(pChange);
    }

    /**
     * Adds a FM change to the list.
     * @param pChange the change to add.
     */
    public void addFM(final FMTafTrend pChange) {
        fFMs.add(pChange);
    }

    /**
     * @return the tempos
     */
    public List<TEMPOChange> getTempos() {
        return fTempos;
    }
}
