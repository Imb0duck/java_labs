package task.calc.commands;

import task.calc.CommandContext;
import task.calc.exceptions.WriteException;

import java.io.IOException;
import java.io.Writer;

public class Print extends Command
{
    public Print()
    {
        super(0, 1);
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params) throws WriteException
    {
        try
        {
            Writer writer = context.getWriter();
            writer.append(Double.toString(context.peek())).append(System.lineSeparator());
            writer.flush();
        }
        catch (IOException e)
        {
            throw new WriteException(createExceptionMessage(e.getMessage()));
        }
    }
}
