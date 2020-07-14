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

public class PhraseActivity extends AppCompatActivity {
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
        Words.add(new Word("Where are you ?", "minto wuksus", R.raw.phrase_where_are_you_going));
        Words.add(new Word("What is your name ?", "tinne ayaase'ne", R.raw.phrase_what_is_your_name));
        Words.add(new Word("My name is....", "ayaaset", R.raw.phrase_my_name_is));
        Words.add(new Word("How are you feeling ?", "michakses", R.raw.phrase_how_are_you_feeling));
        Words.add(new Word("I'm feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        Words.add(new Word("Are you coming ?", "aanes'aa", R.raw.phrase_are_you_coming));
        Words.add(new Word("Yes , I'm coming.", "hee'eenem", R.raw.phrase_yes_im_coming));
        Words.add(new Word("I'm coming.", "aanem", R.raw.phrase_im_coming));
        Words.add(new Word("Let's go", "yoowutis", R.raw.phrase_lets_go));
        Words.add(new Word("Come here.", "anni'nem", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, Words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = Words.get(position);
                mMediaPlayer = MediaPlayer.create(PhraseActivity.this, word.getMediaResourceId());
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