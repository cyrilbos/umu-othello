/**
 * Main class that parses the arguments and prints the choosen move.
 * Manages the iterative deepening search through an exception catch.
 */
public class Othello {
    public static void main(String[] args) throws IllegalMoveException {
        //The timestamp in miliseconds corresponding to the end of the turn
        //We substract 100 ms to avoid going over the limit
        long timeLimitStamp = System.currentTimeMillis() + ((long) Integer.parseInt(args[1])) * 1000 - 100;

        String positionString = args[0];
        OthelloPosition position = new OthelloPosition(positionString);

        OthelloAlgorithm moveChooser = new AlphaBeta(timeLimitStamp);
        OthelloEvaluator evaluator = new BoardEvaluator();
        moveChooser.setEvaluator(evaluator);

        int depth = 0;
        OthelloAction chosenMove = new OthelloAction(0,0);

        //Stop the search if the remaining time is inferior to the last search time
        while (System.currentTimeMillis() < timeLimitStamp) {
            //search again with an incremented depth to find a supposedly better move
            moveChooser.setSearchDepth(depth++);
            try {
                OthelloAction newMove = moveChooser.evaluate(position);
                //may return a blank action if there is no move possible
                if (!newMove.equals(new OthelloAction(0, 0))) {
                    chosenMove = newMove;
                }

            } catch (OutOfTimeException exception) {
                //time is up
            }

        }
        //no move possible at all: play a pass move
        if (chosenMove.equals(new OthelloAction(0,0)))
            chosenMove = new OthelloAction(0,0,true);
        chosenMove.print();
    }
}
