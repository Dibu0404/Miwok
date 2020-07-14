package android.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> Words = new ArrayList<Word>();
        Words.add(new Word("one", "Lutti", R.drawable.number_one, R.raw.number_one));
        Words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        Words.add(new Word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        Words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        Words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        Words.add(new Word("six", "temmokke", R.drawable.number_six, R.raw.number_six));
        Words.add(new Word("seven", "kenekatu", R.drawable.number_seven, R.raw.number_seven));
        Words.add(new Word("Eight", "kawintra", R.drawable.number_eight, R.raw.number_eight));
        Words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        Words.add(new Word("Ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, Words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = Words.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getMediaResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mCompletionListener);

            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();

            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }
}