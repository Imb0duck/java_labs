package task.calc;

import task.calc.exceptions.CommandNotFoundException;
import task.calc.exceptions.InvalidCommandException;

import java.io.Closeable;
import java.io.IOException;

public interface CommandsReader extends Closeable
{
    CommandInfo nextCommand() throws IOException, InvalidCommandException, CommandNotFoundException;
}
