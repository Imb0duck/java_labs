package task.calc.commands;

import task.calc.CommandContext;

public class Sqrt extends Command
{
    public Sqrt()
    {
        super(0, 1);
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params)
    {
        context.push(Math.sqrt(context.pop()));
    }
}
