package task.calc.exceptions;

public class DivisionByZeroException extends InvalidParametersException
{
    public DivisionByZeroException(String msg)
    {
        super(msg);
    }
}
