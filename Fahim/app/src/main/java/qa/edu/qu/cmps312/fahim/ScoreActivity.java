package qa.edu.qu.cmps312.fahim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoreTV;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score = getIntent().getExtras().getInt("score");
        scoreTV = findViewById(R.id.scoreTV);
        scoreTV.setText("Score : "+score);
    }
}
