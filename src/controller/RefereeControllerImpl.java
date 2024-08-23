package controller;

import GUI.referee.RefereeUI;
import controller.api.RefereeController;
import data.Referee;

public class RefereeControllerImpl implements RefereeController {

    private Referee model;
    private final RefereeUI view = new RefereeUI();

    /**
     * the constructor.
     * @param r the Referee which will be the model
     */
    public RefereeControllerImpl(final Referee r) {
        this.model = r;
        view.setRefereeId(model.getCardNumber());
    }
}
