package splashdemo;

/**
* <h1>IllegalMoveException methods</h1>
* @author  Nisha Chaube
*/
class IllegalMoveException extends Exception {
    //handles exceptions
    public IllegalMoveException() {
        super();
    }

    public IllegalMoveException(String dispMessage) {
        super(dispMessage);
    }
}
