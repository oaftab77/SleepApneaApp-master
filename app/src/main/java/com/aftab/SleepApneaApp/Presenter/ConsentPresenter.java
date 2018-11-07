package com.aftab.SleepApneaApp.Presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.telephony.IccOpenLogicalChannelResponse;

import com.aftab.SleepApneaApp.MainActivity;
import com.aftab.SleepApneaApp.Model.Patient;
import com.aftab.SleepApneaApp.R;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.step.ConsentDocumentStep;
import org.researchstack.backbone.step.ConsentSignatureStep;
import org.researchstack.backbone.step.ConsentVisualStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.storage.database.AppDatabase;
import org.researchstack.backbone.storage.database.sqlite.DatabaseHelper;
import org.researchstack.backbone.storage.file.EncryptionProvider;
import org.researchstack.backbone.storage.file.FileAccess;
import org.researchstack.backbone.storage.file.PinCodeConfig;
import org.researchstack.backbone.storage.file.SimpleFileAccess;
import org.researchstack.backbone.storage.file.UnencryptedProvider;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.ui.step.layout.ConsentSignatureStepLayout;

import java.util.ArrayList;
import java.util.List;


// NOTE: SIGNATURES ARE NOT STORED, NEED TO ADD IMPLEMENTATION FOR STORING THEM IN THE FUTURE
// Note that I did not create a method of storing signatures because I am not sure as to what would be appropriate/what type of output is desired

public class ConsentPresenter extends Application {
    public ConsentPresenter(){
        PinCodeConfig pinCodeConfig = new PinCodeConfig();

        EncryptionProvider encryptionProvider = new UnencryptedProvider();

        FileAccess fileAccess = new SimpleFileAccess();

        AppDatabase database = new DatabaseHelper(this,
                DatabaseHelper.DEFAULT_NAME,
                null,
                DatabaseHelper.DEFAULT_VERSION);

        StorageAccess.getInstance().init(pinCodeConfig, encryptionProvider, fileAccess, database);

    }

    public static Task getConsentTask() {
        // 1
        ConsentDocument document1 = createConsentDocument();

        // 2
        List<Step> steps = createConsentSteps(document1);

        // 3
        Task consentTask = new OrderedTask("consent_task", steps);
        return consentTask;

    }

    private static List<Step> createConsentSteps(ConsentDocument document) {

        List<Step> steps = new ArrayList<>();

        for (ConsentSection section: document.getSections()) {
            ConsentVisualStep visualStep = new ConsentVisualStep(section.getType().toString());
            visualStep.setSection(section);
            visualStep.setNextButtonString("Next");
                //getString(R.string.rsb_next) is not allowed in a static context
            steps.add(visualStep);
        }

        ConsentDocumentStep documentStep = new ConsentDocumentStep("consent_doc");
        documentStep.setConsentHTML(document.getHtmlReviewContent());
        documentStep.setConfirmMessage("By agreeing you confirm that you read the information and that you wish to take part in this research study.");
            //getString(R.string.rsb_consent_review_reason) is not allowed in a static context
        steps.add(documentStep);


        ConsentSignature signature = document.getSignature(0);
        if (signature.requiresName()) {
            TextAnswerFormat format = new TextAnswerFormat();
            format.setIsMultipleLines(false);

            QuestionStep fullName = new QuestionStep("consent_name_step", "Please enter your full name",
                    format);
            fullName.setPlaceholder("Full name");
            fullName.setOptional(false);
            steps.add(fullName);
        }

        if (signature.requiresSignatureImage()) {

            ConsentSignatureStep signatureStep = new ConsentSignatureStep("signature_step");
            signatureStep.setTitle("Signature");
                //getString(R.string.rsb_consent_signature_title) is not allowed in a static context
            signatureStep.setText("Please sign using your finger on the line below.");
                //getString(R.string.rsb_consent_signature_instruction) is not allowed in a static context
            signatureStep.setOptional(false);

            signatureStep.setStepLayoutClass(ConsentSignatureStepLayout.class);

            steps.add(signatureStep);
        }


        return steps;
    }

    private static ConsentDocument createConsentDocument() {

        ConsentDocument document = new ConsentDocument();

        document.setTitle("Demo Consent");
        document.setSignaturePageTitle(R.string.rsb_consent);

        List<ConsentSection> sections = new ArrayList<>();
        sections.add(createSection(ConsentSection.Type.Overview, "Overview Info", "<h1>Read " +
                "This!</h1><p>Some " +
                "really <strong>important</strong> information you should know about this step"));
        sections.add(createSection(ConsentSection.Type.DataGathering, "Data Gathering Info", ""));
        sections.add(createSection(ConsentSection.Type.Privacy, "Privacy Info", ""));
        sections.add(createSection(ConsentSection.Type.DataUse, "Data Use Info", ""));
        sections.add(createSection(ConsentSection.Type.TimeCommitment, "Time Commitment Info", ""));
        sections.add(createSection(ConsentSection.Type.StudySurvey, "Study Survey Info", ""));
        sections.add(createSection(ConsentSection.Type.StudyTasks, "Study Task Info", ""));
        sections.add(createSection(ConsentSection.Type.Withdrawing, "Withdrawing Info", "Some detailed steps " +
                "to withdrawal from this study. <ul><li>Step 1</li><li>Step 2</li></ul>"));

        document.setSections(sections);


        ConsentSignature signature = new ConsentSignature();
        signature.setRequiresName(true);
        signature.setRequiresSignatureImage(true);

        document.addSignature(signature);

        document.setHtmlReviewContent("<div style=\"padding: 10px;\" class=\"header\">" +
                "<h1 style='text-align: center'>Review Consent!</h1></div>");

        return document;
    }

    private static ConsentSection createSection(ConsentSection.Type type, String summary, String content) {

        ConsentSection section = new ConsentSection(type);
        section.setSummary(summary);
        section.setHtmlContent(content);

        return section;
    }

}
