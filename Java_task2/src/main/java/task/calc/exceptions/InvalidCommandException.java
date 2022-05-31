package task.calc.exceptions;

public class InvalidCommandException extends StackCalculatorException
{
    public InvalidCommandException(String msg)
    {
        super(msg);
    }
}
