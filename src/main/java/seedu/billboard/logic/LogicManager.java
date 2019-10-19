package seedu.billboard.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.billboard.commons.core.GuiSettings;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.logic.commands.Command;
import seedu.billboard.logic.commands.CommandResult;
import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.logic.parser.BillboardParser;
import seedu.billboard.logic.parser.exceptions.ParseException;
import seedu.billboard.model.Model;
import seedu.billboard.model.ReadOnlyArchiveWrapper;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final BillboardParser billboardParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        billboardParser = new BillboardParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = billboardParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveBillboard(model.getCombinedBillboard());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyBillboard getBillboard() {
        return model.getBillboard();
    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return model.getFilteredExpenses();
    }

    @Override
    public Path getBillboardFilePath() {
        return model.getBillboardFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyArchiveWrapper getArchive() {
        return model.getArchives();
    }

    @Override
    public ObservableList<Expense> getFilteredArchiveExpenseList(String archiveName) {
        return model.getFilteredArchiveExpenses(archiveName);
    }

}
