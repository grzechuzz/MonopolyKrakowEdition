package game;

import service.*;

public class GameContext {
    private final PropertyTransactionService propertyTransactionService;
    private final UserInteractionService userInteractionService;
    private final TaxService taxService;
    private final JailService jailService;
    private final TravelService travelService;

    public GameContext(PropertyTransactionService propertyTransactionService,
                       UserInteractionService userInteractionService,
                       TaxService taxService,
                       JailService jailService,
                       TravelService travelService)
    {
        this.propertyTransactionService = propertyTransactionService;
        this.userInteractionService = userInteractionService;
        this.taxService = taxService;
        this.jailService = jailService;
        this.travelService = travelService;
    }

    public PropertyTransactionService getPropertyTransactionService() {
        return propertyTransactionService;
    }

    public UserInteractionService getUserInteractionService() {
        return userInteractionService;
    }

    public TaxService getTaxService() {
        return taxService;
    }

    public JailService getJailService() {
        return jailService;
    }

    public TravelService getTravelService() {
        return travelService;
    }
}
