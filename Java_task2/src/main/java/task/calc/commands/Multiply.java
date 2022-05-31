package task.calc.commands;

import task.calc.CommandContext;

public class Multiply extends Command
{
    public Multiply()
    {
        super(0, 2);
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params)
    {
        double op1 = context.pop();
        double op2 = context.pop();

        context.push(op1 * op2);
    }
}
