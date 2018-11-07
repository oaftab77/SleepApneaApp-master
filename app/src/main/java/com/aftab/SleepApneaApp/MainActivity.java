/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.aftab.SleepApneaApp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aftab.SleepApneaApp.Presenter.ConsentPresenter;
import com.aftab.SleepApneaApp.Presenter.SurveyPresenter;

import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.step.ConsentDocumentStep;
import org.researchstack.backbone.step.ConsentSignatureStep;
import org.researchstack.backbone.step.ConsentVisualStep;
import org.researchstack.backbone.step.InstructionStep;
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

import static android.Manifest.permission.RECORD_AUDIO;


public class MainActivity extends AppCompatActivity {

  private String[] mPermissions = {RECORD_AUDIO};

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    //Following code initializes the Backbone Storage Access Component, this initialization is required
    PinCodeConfig pinCodeConfig = new PinCodeConfig();

    EncryptionProvider encryptionProvider = new UnencryptedProvider();

    FileAccess fileAccess = new SimpleFileAccess();

    AppDatabase database = new DatabaseHelper(this,
            DatabaseHelper.DEFAULT_NAME,
            null,
            DatabaseHelper.DEFAULT_VERSION);

    StorageAccess.getInstance().init(pinCodeConfig, encryptionProvider, fileAccess, database);



    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button consentButton = (Button)findViewById(R.id.consentButton);
    final Button surveyButton = (Button)findViewById(R.id.surveyButton);
    surveyButton.setVisibility(View.INVISIBLE);

    consentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        ConsentPresenter consentPresenter = new ConsentPresenter();
        Intent intent = ViewTaskActivity.newIntent(MainActivity.this, ConsentPresenter.getConsentTask());
        startActivity(intent);

      }
    });


    surveyButton.setVisibility(View.VISIBLE);
    surveyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        SurveyPresenter surveyPresenter = new SurveyPresenter();
        OrderedTask task = surveyPresenter.createBerlinSurvey();

        Intent intent = ViewTaskActivity.newIntent(MainActivity.this, task);
        startActivity(intent);

      }
    });

  }





}
