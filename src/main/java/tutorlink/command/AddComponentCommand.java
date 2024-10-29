package tutorlink.command;

import java.util.HashMap;

import tutorlink.appstate.AppState;
import tutorlink.commons.Commons;
import tutorlink.component.Component;
import tutorlink.exceptions.IllegalValueException;
import tutorlink.exceptions.TutorLinkException;
import tutorlink.result.CommandResult;

public class AddComponentCommand extends Command {

    public static final String[] ARGUMENT_PREFIXES = {"c/", "w/", "m/"};
    public static final String COMMAND_WORD = "add_component";

    @Override
    public CommandResult execute(AppState appState, HashMap<String, String> hashmap) throws TutorLinkException {
        String componentName = hashmap.get(ARGUMENT_PREFIXES[0]);
        String weightageNumber = hashmap.get(ARGUMENT_PREFIXES[1]);
        String maxScoreNumber = hashmap.get(ARGUMENT_PREFIXES[2]);
        if (componentName == null || weightageNumber == null || maxScoreNumber == null) {
            throw new IllegalValueException(Commons.ERROR_NULL);
        }

        double weightage = convertWeightageToValidDouble(weightageNumber);

        double maxScore = convertMaxScoreToValidDouble(maxScoreNumber);
        appState.components.addComponent(new Component(componentName, maxScore, weightage));
        return new CommandResult(String.format(Commons.ADD_COMPONENT_SUCCESS,
                componentName, weightageNumber, maxScoreNumber));
    }

    private static double convertWeightageToValidDouble(String weightageNumber) {
        try {
            double weightage = Double.parseDouble(weightageNumber);
            if (weightage < 0.0 || weightage > 1.0) {
                throw new IllegalValueException(Commons.ERROR_INVALID_WEIGHTAGE);
            }
            return weightage;

        } catch (NumberFormatException e) {
            throw new IllegalValueException(Commons.ERROR_INVALID_WEIGHTAGE);
        }
    }

    private static double convertMaxScoreToValidDouble(String maxScoreNumber) {
        try {
            double maxScore = Double.parseDouble(maxScoreNumber);

            if (maxScore < 0.0) {
                throw new IllegalValueException(Commons.ERROR_INVALID_MAX_SCORE);
            }

            return maxScore;

        } catch (NumberFormatException e) {
            throw new IllegalValueException(Commons.ERROR_INVALID_MAX_SCORE);
        }
    }


    @Override
    public String[] getArgumentPrefixes() {
        return ARGUMENT_PREFIXES;
    }
}