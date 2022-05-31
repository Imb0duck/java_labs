package task.calc.commands;

import task.calc.CommandContext;
import task.calc.exceptions.EmptyStackException;
import task.calc.exceptions.InvalidParametersException;
import task.calc.exceptions.StackCalculatorException;

public abstract class Command
{
    protected int paramsCount;
    protected int stackArgsCount;

    protected Command(int paramsCount, int stackArgsCount)
    {
        this.paramsCount = paramsCount;
        this.stackArgsCount = stackArgsCount;
    }

    protected String createExceptionMessage(String msg)
    {
        return getClass().getSimpleName() + ": " + msg;
    }

    protected abstract void executeImplementation(CommandContext context, Object[] params) throws StackCalculatorException;

    public void execute(CommandContext context, Object[] params) throws StackCalculatorException
    {
        if (params.length > paramsCount)
        {
            throw new InvalidParametersException(createExceptionMessage("Unexpected parameter: " + params[paramsCount]));
        }
        else if (params.length < paramsCount)
        {
            throw new InvalidParametersException(createExceptionMessage("Too few parameters. Expected " + paramsCount + ", given " + params.length + "."));
        }

        int stackLength = context.getStackSize();

        if (stackLength < stackArgsCount)
        {
            throw new EmptyStackException(createExceptionMessage("Too few arguments on stack."));
        }

        executeImplementation(context, params);
    }
}
