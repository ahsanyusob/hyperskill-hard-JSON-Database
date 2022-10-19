package client;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")  // separators are now "=" and " "
public class ClientArgs {

    @Parameter(names = {"-h", "--help"},
            help = true,
            description = "Display help information")
    private boolean help;

    @Parameter(names = {"-t", "--type"},
            required = true,
            arity = 1,
            description = "Specify the following request types: set, get, remove, exit")
    private String type;

    @Parameter(names = {"-i", "--index"},
            arity = 1,
            description = "Specify the index for set, get and remove requests")
    private int index;

    @Parameter(names = {"-m", "--message"},
            arity = 1,
            description = "Value to save in database in string format")
    private String message;

    public boolean isHelp() {
        return help;
    }

    @Override
    public String toString() {
        return "\nhelp=" + help +
                "\ntype=" + type +
                "\nindex=" + index+
                "\nmessage=" + message;
    }

    public String getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public String getMessage() {
        return message;
    }

}
