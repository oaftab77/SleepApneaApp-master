package com.aftab.SleepApneaApp.Presenter;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.answerformat.DecimalAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;

import java.util.ArrayList;
import java.util.List;

public class SurveyPresenter {

    public OrderedTask createBerlinSurvey()
    {
        List<Step> steps = new ArrayList<>();

        InstructionStep instructionStep = new InstructionStep("survey_instruction_step",
            "Berlin Questionnaire",
            "Please complete the following prompts.");
        steps.add(instructionStep);

        AnswerFormat textFormat = new TextAnswerFormat(20);
        AnswerFormat numFormat = new DecimalAnswerFormat(0.00f, 600.00f);

        QuestionStep heightStep = new QuestionStep("height", "Height:", numFormat);
        QuestionStep weightStep = new QuestionStep("weight", "Weight:", numFormat);
        QuestionStep ageStep = new QuestionStep("age", "Age:", numFormat);
        QuestionStep sexStep = new QuestionStep("sex", "Sex:", textFormat);

        heightStep.setPlaceholder("Inches");
        weightStep.setPlaceholder("Pounds");
        ageStep.setPlaceholder("Years");
        sexStep.setPlaceholder("Sex");

        heightStep.setOptional(false);
        weightStep.setOptional(false);
        ageStep.setOptional(false);
        sexStep.setOptional(false);

        FormStep formPage1 = new FormStep("formPage1", "Step 1", "Please complete the following.");
        formPage1.setFormSteps(heightStep, weightStep, ageStep, sexStep);
        formPage1.setOptional(false);
        steps.add(formPage1);

        AnswerFormat question2Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
            new Choice<>("Yes", 0),
            new Choice<>("No", 1),
            new Choice<>("Don't know", 2));
        QuestionStep  question2Step = new QuestionStep("question2Step", "Do you snore?", question2Format);
        question2Step.setOptional(false);
        steps.add(question2Step);

        AnswerFormat question3Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Silent or slightly louder than breathing", 0),
                new Choice<>("As loud as talking", 1),
                new Choice<>("Louder than talking", 2),
                new Choice<>("Very loud, can be heard in adjacent rooms", 3));
        QuestionStep  question3Step = new QuestionStep("question3Step", "How loud is your snoring?", question3Format);
        question3Step.setOptional(false);
        steps.add(question3Step);

        AnswerFormat question4Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Nearly every day", 0),
                new Choice<>("3-4 times a week", 1),
                new Choice<>("1-2 times a week", 2),
                new Choice<>("1-2 times a month", 3),
                new Choice<>("Never or nearly never", 4));
        QuestionStep  question4Step = new QuestionStep("question4Step", "How often do you snore?", question4Format);
        question4Step.setOptional(false);
        steps.add(question4Step);

        AnswerFormat question5Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Yes", 0),
                new Choice<>("No", 1),
                new Choice<>("Don't know", 2));
        QuestionStep  question5Step = new QuestionStep("question5Step", "Do you snore?", question5Format);
        question5Step.setOptional(false);
        steps.add(question5Step);

        AnswerFormat question6Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
            new Choice<>("Nearly every day", 0),
            new Choice<>("3-4 times a week", 1),
            new Choice<>("1-2 times a week", 2),
            new Choice<>("1-2 times a month", 3),
            new Choice<>("Never or nearly never", 4));
        QuestionStep  question6Step = new QuestionStep("question6Step", "Has anyone noticed that you quit breathing during your sleep?", question6Format);
        question6Step.setOptional(false);
        steps.add(question6Step);


        // START OF CATEGORY 2
        AnswerFormat question7Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Nearly every day", 0),
                new Choice<>("3-4 times a week", 1),
                new Choice<>("1-2 times a week", 2),
                new Choice<>("1-2 times a month", 3),
                new Choice<>("Never or nearly never", 4));
        QuestionStep  question7Step = new QuestionStep("question7Step", "How often do you feel tired or fatigued after you sleep?", question7Format);
        question7Step.setOptional(false);
        steps.add(question7Step);

        AnswerFormat question8Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Nearly every day", 0),
                new Choice<>("3-4 times a week", 1),
                new Choice<>("1-2 times a week", 2),
                new Choice<>("1-2 times a month", 3),
                new Choice<>("Never or nearly never", 4));
        QuestionStep  question8Step = new QuestionStep("question8Step", "During your wake time, do you feel tired, fatigued, or not up to par?", question8Format);
        question8Step.setOptional(false);
        steps.add(question8Step);

        AnswerFormat question9Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Yes", 0),
                new Choice<>("No", 1));
        QuestionStep  question9Step = new QuestionStep("question9Step", "Have you ever nodded off or fallen asleep while driving a vehicle?", question9Format);
        question9Step.setOptional(false);
        steps.add(question9Step);

        AnswerFormat question9P2Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Nearly every day", 0),
                new Choice<>("3-4 times a week", 1),
                new Choice<>("1-2 times a week", 2),
                new Choice<>("1-2 times a month", 3),
                new Choice<>("Never or nearly never", 4));
        QuestionStep  question9P2 = new QuestionStep("question9P2", "How often do you fall asleep while driving a vehicle?", question9P2Format);
        question9P2.setOptional(false);
        steps.add(question9P2);

        // CATEGORY 3
        AnswerFormat question10Format = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Yes", 0),
                new Choice<>("No", 1),
                new Choice<>("Don't know", 2));
        QuestionStep  question10Step = new QuestionStep("question10Step", "Do you have high blood pressure?", question10Format);
        question10Step.setOptional(false);
        steps.add(question10Step);

        QuestionStep BMIStep = new QuestionStep("height", "BMI:", numFormat);
        BMIStep.setPlaceholder("");
        BMIStep.setOptional(false);
        steps.add(BMIStep);


        InstructionStep summaryStep = new InstructionStep("summaryStep",
                "Thank you for your participation in this study!",
                "");
        steps.add(summaryStep);

        OrderedTask task = new OrderedTask("survey_task", steps);
        //StepResult s1 = new StepResult(questionStep);
        return task;
    }

}
