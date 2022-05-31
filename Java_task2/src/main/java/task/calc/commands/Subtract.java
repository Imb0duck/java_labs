package task.calc.commands;

import task.calc.CommandContext;

public class Subtract extends Command
{
    public Subtract()
    {
        super(0, 2);
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params)
    {
        double op1 = context.pop();
        double op2 = context.pop();

        context.push(op2 - op1);
    }
}
