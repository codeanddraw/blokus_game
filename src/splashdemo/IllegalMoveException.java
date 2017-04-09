package splashdemo;

//exception handling
class IllegalMoveException extends Exception
   {
      public IllegalMoveException()
      {
         super();
      }
      
      public IllegalMoveException(String message)
      {
         super(message);
      }
   }