package task.calc;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import task.calc.commands.Command;
import task.calc.exceptions.CommandNotFoundException;
import task.calc.exceptions.InvalidCommandException;
import task.calc.exceptions.StackCalculatorException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Arrays;

public class DefaultCommandsReader implements CommandsReader
{
    private static final Logger logger = LogManager.getLogger(DefaultCommandsReader.class.getName());

    private final LineNumberReader reader;

    public DefaultCommandsReader(InputStream inputStream) throws StackCalculatorException, IOException
    {
        logger.trace("Constructing new DefaultCommandsReader...");

        reader = new LineNumberReader(new InputStreamReader(inputStream));
    }

    @Override
    public CommandInfo nextCommand() throws IOException, InvalidCommandException, CommandNotFoundException
    {
        logger.trace("Trying to read next command...");

        String line = reader.readLine();

        if (line == null || 0 == line.length()) return null;

        //split values
        String[] split = line.split("\\s+");

        Command cmd;
        try
        {
            cmd = CommandsFactory.create(split[0]);
        }
        catch (InvalidCommandException e)
        {
            throw new InvalidCommandException("Error at line " + reader.getLineNumber() + ": " + e.getMessage());
        }
        catch (CommandNotFoundException e)
        {
            throw new CommandNotFoundException("Error at line " + reader.getLineNumber() + ": " + e.getMessage());
        }

        //remove first value
        String[] params = Arrays.copyOfRange(split, 1, split.length);

        return new CommandInfo(cmd, params);
    }

    @Override
    public void close() throws IOException
    {
        logger.trace("Closing DefaultCommandsReader.");
        reader.close();
    }
}
