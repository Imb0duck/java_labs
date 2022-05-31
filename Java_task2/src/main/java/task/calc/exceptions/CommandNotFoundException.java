package task.calc.exceptions;

public class CommandNotFoundException extends StackCalculatorException
{
    public CommandNotFoundException(String msg)
    {
        super(msg);
    }
}
