package seedu.billboard.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.Billboard;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.expense.Expense;

/**
 * An Immutable Billboard that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate expense(s).";

    private final List<JsonAdaptedExpense> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedExpense> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyBillboard} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyBillboard source) {
        persons.addAll(source.getExpenses().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Billboard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Billboard toModelType() throws IllegalValueException {
        Billboard addressBook = new Billboard();
        for (JsonAdaptedExpense jsonAdaptedExpense : persons) {
            Expense expense = jsonAdaptedExpense.toModelType();
            if (addressBook.hasExpense(expense)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addExpense(expense);
        }
        return addressBook;
    }

}
