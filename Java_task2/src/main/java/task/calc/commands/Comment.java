package task.calc.commands;

import task.calc.CommandContext;

public class Comment extends Command
{
    public Comment()
    {
        super(0, 0);
    }

    @Override
    public void execute(CommandContext context, Object[] params)
    {
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params)
    {
    }
}
