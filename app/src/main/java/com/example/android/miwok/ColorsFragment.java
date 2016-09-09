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
public class ColorsFragment extends Fragment {

    public MediaPlayer media;
    private AudioManager mAudio;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
                media.pause();
                media.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                media.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.words_list,container,false);

        mAudio=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("black","kada",R.drawable.color_black,R.raw.color_black));
        words.add(new Word("red","lall",R.drawable.color_red,R.raw.color_red));
        words.add(new Word("gray","saleti",R.drawable.color_gray,R.raw.color_gray));
        words.add(new Word("green","hara",R.drawable.color_green,R.raw.color_green));
        words.add(new Word("Mustard yellow","Peella",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow","bhura peella",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        words.add(new Word("white","safaid",R.drawable.color_white,R.raw.color_white));
        words.add(new Word("brown","Bhura",R.drawable.color_brown,R.raw.color_brown));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(),words,R.color.category_colors);

        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //creating word object instance using factory method
                Word word = words.get(position);
                releaseMediaPlayer();

                int request =mAudio.requestAudioFocus(audioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(request==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
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
