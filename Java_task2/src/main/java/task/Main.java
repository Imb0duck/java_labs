package task;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import task.calc.StackCalculator;
import task.calc.exceptions.StackCalculatorException;

import java.io.*;
import java.util.Arrays;

public class Main
{
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(args));
        logger.trace("Starting application...");

            if (args.length > 2)
        {
            String msg = "Too much arguments given. Exiting...";
            System.err.println(msg);
            logger.fatal(msg);
            return;
        }
        try
        {
            StackCalculator calc = new StackCalculator(getInputStream(args), getOutputStream(args));
            calc.execute();
        }
        catch (StackCalculatorException e)
        {
            System.err.println("Error: " + e.getMessage());
            logger.fatal(e.toString());
        }
        catch (IOException e)
        {
            System.err.println("Error reading from/writing to file " + e.getMessage());
            logger.fatal(e.toString());
        }
    }

    private static InputStream getInputStream(String[] args) throws FileNotFoundException
    {
        if (args.length == 0)
        {
            logger.info("Using System.in as input stream.");
            return System.in;
        }
        logger.info("Using " + args[0] + " file as input stream.");
        return new FileInputStream(args[0]);
    }

    private static OutputStream getOutputStream(String[] args) throws FileNotFoundException
    {
        if (args.length < 2)
        {
            logger.info("Using System.out as output stream.");
            return System.out;
        }
        logger.info("Using " + args[1] + " file as output stream.");
        return new FileOutputStream(args[1]);
    }
}
