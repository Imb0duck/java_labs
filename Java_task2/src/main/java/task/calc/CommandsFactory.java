package task.calc;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import task.calc.commands.Command;
import task.calc.exceptions.CommandNotFoundException;
import task.calc.exceptions.InvalidCommandException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

public class CommandsFactory
{
    private final static Logger logger = LogManager.getLogger(CommandsFactory.class.getName());

    private static final String COMMANDS_PROP = "/commands.properties";
    private final static Map<String, Class<?>> commandsClasses = new HashMap<>();
    private static volatile CommandsFactory commandsFactory;

    private CommandsFactory() throws CommandNotFoundException, InvalidCommandException, IOException
    {
        logger.trace("Constructing new CommandsFactory...");

        logger.info("Reading properties from '" + COMMANDS_PROP + "'");

        InputStream propsInputStream = getClass().getResourceAsStream(COMMANDS_PROP);

        if (propsInputStream == null)
        {
            String msg = "Unable to find '" + COMMANDS_PROP + "' bundle.";
            logger.fatal(msg);
            throw new MissingResourceException(msg, COMMANDS_PROP, "");
        }

        readProperties(propsInputStream);

        try
        {
            propsInputStream.close();
        }
        catch (IOException e)
        {
            logger.fatal("Error closing resource bundle input stream.");
        }
    }

    public CommandsFactory(InputStream propsInputStream) throws InvalidCommandException, CommandNotFoundException, IOException
    {
        logger.trace("Constructing new CommandsFactory...");
        logger.info("Reading properties from " + propsInputStream.getClass().getSimpleName());

        readProperties(propsInputStream);
    }

    private void readProperties(InputStream propsInputStream) throws CommandNotFoundException, InvalidCommandException, IOException
    {
        PropertyResourceBundle bundle = new PropertyResourceBundle(propsInputStream);

        var propKeys = bundle.getKeys();

        int count = 0;
        while (propKeys.hasMoreElements())
        {
            String key = propKeys.nextElement();
            String className = bundle.getString(key);

            Class<?> cl = null;
            try
            {
                cl = Class.forName(className);
            }
            catch (ClassNotFoundException e)
            {
                throw new CommandNotFoundException("Unable to find " + className + " class");
            }

            if (!Command.class.isAssignableFrom(cl))
            {
                throw new InvalidCommandException(className + " class doesn't derive from " + Command.class.getName());
            }
            commandsClasses.put(key.toLowerCase(), cl);
            count++;
        }

        logger.info("Successfully read " + count + " properties.");

    }

    public static Command create(String commandName) throws InvalidCommandException, CommandNotFoundException, IOException {
        logger.trace("Trying to create Command with name '" + commandName + "'.");
        Command instance = null;

        synchronized (CommandsFactory.class) {
            if(commandsFactory == null){
                commandsFactory = new CommandsFactory();
            }
            var commandClass = commandsClasses.get(commandName.toLowerCase());
            if (commandClass == null) {
                logger.error("Unable to find class name for command name '" + commandName + "'.");
                throw new CommandNotFoundException("Unknown command: " + commandName);
            }

            try {
                instance = (Command) commandClass.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                logger.error("Unable to create instance of " + commandClass.getName() + " class");
                throw new InvalidCommandException("Unknown command: " + commandName);
            }
        }

        logger.info("Successfully created Command instance with name '" + commandName + "'.");
        return instance;
    }
}
