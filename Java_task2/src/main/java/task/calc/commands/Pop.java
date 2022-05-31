package task.calc.commands;

import task.calc.CommandContext;

public class Pop extends Command
{
    public Pop()
    {
        super(0, 1);
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params)
    {
        context.pop();
    }
}
