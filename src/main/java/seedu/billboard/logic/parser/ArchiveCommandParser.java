package seedu.billboard.logic.parser;

import static seedu.billboard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.billboard.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.billboard.logic.commands.AddArchiveCommand;
import seedu.billboard.logic.commands.ArchiveCommand;
import seedu.billboard.logic.commands.DeleteArchiveCommand;
import seedu.billboard.logic.commands.HelpCommand;
import seedu.billboard.logic.commands.ListArchiveCommand;
import seedu.billboard.logic.commands.ListArchiveNamesCommand;
import seedu.billboard.logic.commands.RevertArchiveCommand;
import seedu.billboard.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ArchiveCommandParser implements Parser<ArchiveCommand> {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ArchiveCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ListArchiveNamesCommand.COMMAND_WORD:
            return new ListArchiveNamesCommand();

        case ListArchiveCommand.COMMAND_WORD:
            return new ListArchiveCommandParser().parse(arguments);

        case AddArchiveCommand.COMMAND_WORD:
            return new AddArchiveCommandParser().parse(arguments);

        case RevertArchiveCommand.COMMAND_WORD:
            return new RevertArchiveCommandParser().parse(arguments);

        case DeleteArchiveCommand.COMMAND_WORD:
            return new DeleteArchiveCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
