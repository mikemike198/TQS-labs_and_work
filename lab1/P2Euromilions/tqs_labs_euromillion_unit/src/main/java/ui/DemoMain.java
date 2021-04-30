package ui;

import euromillions.CuponEuromillions;
import euromillions.Dip;
import euromillions.EuromillionsDraw;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DemoMain {

    private final static Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * demonstrates a client for ramdom euromillions bets
     */
    public static void main(String[] args) {

        // played sheet
        CuponEuromillions thisWeek = new CuponEuromillions();
        logger.log(Level.INFO, "Betting with three random bets...");
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());

        // simulate a random draw
        EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();

        //report results
        logger.log(Level.INFO, "You played:");
        logger.log(Level.INFO, thisWeek.format());


        logger.log(Level.INFO, "Draw results:");
        logger.log(Level.INFO, draw.getDrawResults().format());

        logger.log(Level.INFO, "Your score:");
        for (Dip dip : draw.findMatches(thisWeek)) {
            logger.log(Level.INFO, dip.format());

        }
    }
}
