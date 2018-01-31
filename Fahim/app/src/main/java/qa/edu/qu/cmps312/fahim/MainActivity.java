package qa.edu.qu.cmps312.fahim;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import qa.edu.qu.cmps312.fahim.models.Choice;
import qa.edu.qu.cmps312.fahim.models.GridAdapter;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private GridView imagesGV;
    private GridAdapter gridAdapter;
    private ImageView fingerIV;
    private int unite, lesson, level, score, trials, chancesGiven, correctAnswerIndex;
    private ArrayList<Choice> listOfChoices;
    private SharedPreferences history;
    private MediaPlayer soundPlayer;
    private Timer _timer;
    private Boolean hasClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagesGV = findViewById(R.id.imagesGV);

        loadHistory();

        gridAdapter = new GridAdapter(listOfChoices, this);
        imagesGV.setAdapter(gridAdapter);
        imagesGV.setOnItemClickListener(this);
        hasClicked = false;
        trials = 0;
        startTrial();

    }

    public void startTrial() {
        trials++;
        _timer = new Timer();
        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // use runOnUiThread(Runnable action)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chancesGiven++;
                        soundPlayer = MediaPlayer.create(getApplicationContext(), R.raw.red);
                        switch (chancesGiven) {
                            case 1:
                            case 2:
                                soundPlayer.start();
                                break;
                            case 3:
                                soundPlayer.start();
                                // TODO: 1/30/2018 Show fingerIV
                                showFinger();
                                break;
                            case 4:
                                soundPlayer.stop();
                                if (!hasClicked)
                                    hideOneChoice();
                                break;
                        }

                    }
                });
            }
        }, 0, 5000);
    }


    public void showFinger() {
        listOfChoices.get(correctAnswerIndex).setShowFinger(true);
        gridAdapter.notifyDataSetChanged();
        hasClicked = false;
    }

    public void hideFinger() {
        listOfChoices.get(correctAnswerIndex).setShowFinger(false);
        Log.d(TAG, "hideFinger: "+correctAnswerIndex);
        gridAdapter.notifyDataSetChanged();
        hasClicked = false;
    }

    public void hideOneChoice() {
        imagesGV.getChildAt((correctAnswerIndex + 1) % listOfChoices.size()).setVisibility(View.GONE);
        _timer.cancel();
    }
    public void unhideChoice(){
        imagesGV.getChildAt((correctAnswerIndex + 1) % listOfChoices.size()).setVisibility(View.VISIBLE);

    }

    public void loadHistory() {
        //Check if Child has previous records
        history = getSharedPreferences("CHILD_HISTORY", MODE_PRIVATE);


        Boolean isFirstTime = history.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            // TODO: 1/30/2018 get all assets from firebase and store them on the device

            // Unite 1 Lesson 1 Level 1
            listOfChoices = new ArrayList<>();
            listOfChoices.add(new Choice("http://dreamicus.com/data/red/red-01.jpg", true, false));
            listOfChoices.add(new Choice("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/000080_Navy_Blue_Square.svg/600px-000080_Navy_Blue_Square.svg.png", false, false));
            getCorrectAnswerIndex();

            unite = lesson = level = 1;
        } else {
            // Reading from SharedPreferences
            unite = history.getInt("unite", 1);
            lesson = history.getInt("lesson", 1);
            level = history.getInt("level", 1);
            Log.d(TAG, "loadHistory: " + level);
            // TODO: 1/30/2018             Get Images using this data (unite,lesson,level) and add it to listOfChoices

        }
        score = 0;
        trials = 0;
        chancesGiven = 0;

    }

    private void getCorrectAnswerIndex() {
        for (int i = 0; i < listOfChoices.size(); i++) {
            if (listOfChoices.get(i).isTrue()) {
                correctAnswerIndex = i;
                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        hasClicked = true;
        //Stopping the sound playing
        soundPlayer.stop();

        //Log.d(TAG, "Level "+level);
        switch (level) {
            case 1:
                if (chancesGiven < 3) {
                    if (listOfChoices.get(position).isTrue()) {
                        score += 2;
                        // TODO: 1/30/2018 play reward number 1
                    } else {
                        //showFinger();
                        // _timer.cancel();
                    }
                } else {
                    if (listOfChoices.get(position).isTrue()) {
                        score += 1;
                        // TODO: 1/30/2018 play reward number 1
                    } else {
                        // reward 0 new trial
                    }
                }
                Log.d(TAG, "Score in Trial : " + trials + " is " + score);
                _timer.cancel();
                if (trials != 2){
                    newTrial();
                }
                else {
                    Log.d(TAG, "Final Score is " + score);
                    Intent scoreActivity = new Intent(this, ScoreActivity.class);
                    scoreActivity.putExtra("score", score);
                    startActivity(scoreActivity);
                    // TODO: 1/31/2018 open Score Activity
                }
                break;
            case 2:

                break;
            case 3:
                break;
            case 4:
                break;
        }

    }

    public void newTrial() {
        chancesGiven = 0;
        unhideChoice();
        getCorrectAnswerIndex();
        hideFinger();
        switch (level) {
            case 1: //reverse list
                Collections.reverse(listOfChoices);
                break;
            case 2: //shuffle list
            case 3:
            case 4:
                break;
        }
        getCorrectAnswerIndex();
        startTrial();
    }
}
