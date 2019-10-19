package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.billboard.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Expense;

/**
 * Adds an expense to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a expense to the billboard. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DATE + "DATE"
            + "[" + PREFIX_TAG + "TAG]..."
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Bought "
            + PREFIX_DESCRIPTION + "Buy a book "
            + PREFIX_AMOUNT + "9.00 "
            + PREFIX_DATE + "25/3/2019 1200 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New expense added: %1$s";
    public static final String MESSAGE_DUPLICATE_EXPENSE = "This expense already exists in the billboard";

    private final Expense toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Expense}
     */
    public AddCommand(Expense expense) {
        requireNonNull(expense);
        toAdd = expense;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasExpense(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EXPENSE);
        }

        model.addExpense(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
