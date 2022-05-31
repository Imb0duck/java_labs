package task.calc.commands;

import task.calc.CommandContext;
import task.calc.exceptions.DefineAlreadyExistsException;
import task.calc.exceptions.InvalidParametersException;

public class Define extends Command
{
    public Define()
    {
        super(2, 0);
    }

    @Override
    protected void executeImplementation(CommandContext context, Object[] params) throws InvalidParametersException
    {
        String name = null;
        String valueStr;
        try
        {
            name = (String) params[0];
            valueStr = (String) params[1];
        }
        catch (ClassCastException e)
        {
            throw new InvalidParametersException(createExceptionMessage("Unknown type of " + ((name == null) ? "first" : "second") + " argument."));
        }

        try
        {
            double value = Double.parseDouble(valueStr);

            if (context.hasNamedParameter(name))
            {
                throw new DefineAlreadyExistsException(createExceptionMessage("Name '" + name + "' is already declared."));
            }
            context.addNamedParameter(name, value);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidParametersException(createExceptionMessage("Error parsing value '" + params[1] + "'."));
        }
    }
}
