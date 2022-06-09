package task.calc.commands;

import task.calc.CommandContext;

public class Abs extends Command
{
    public Abs() { super(0, 1); }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params)
    {
        context.push(Math.abs(context.pop()));
    }
}
