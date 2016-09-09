package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private MediaPlayer media;
    private AudioManager mAudio;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                media.pause();
                media.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                media.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }

        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list,container,false);

        //initializing audiomanager by calling getSystemService
        mAudio = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","Ikk",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","do",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tinn",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","chaar",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","panj",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","che",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","satt",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","athh",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","nau",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","das",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),words,R.color.category_numbers);

        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        //audio files been introduced through setonitemclicklistener


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //creating word object instance using factory method
                Word word =words.get(position);

                releaseMediaPlayer();

                int request = mAudio.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    media = MediaPlayer.create(getActivity(), word.getAudioId());
                    media.start();

                    media.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return rootView;

    }

    @Override
    public void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (media != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            media.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            media = null;

            mAudio.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}
