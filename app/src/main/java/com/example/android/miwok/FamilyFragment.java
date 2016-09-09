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
public class FamilyFragment extends Fragment {

    private MediaPlayer media;
    private AudioManager mAudio;

    AudioManager.OnAudioFocusChangeListener AudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
            {
                media.pause();
                media.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                media.start();
            }

            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list,container,false);

        mAudio=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Dad","pyo",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("Mother","Maa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("Older sister","Vadi Bhend",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("Older Brother","Vada bhara",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("Daughter","Beti",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("Son","Putt",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("Younger Son","Chota bhara",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("Younger Sister","choti bhend",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("Grandfather","Dada ji ",R.drawable.family_grandfather,R.raw.family_grandfather));
        words.add(new Word("Grandmother","Dadi ji ",R.drawable.family_grandmother,R.raw.family_grandmother));


        WordAdapter itemsAdapter = new WordAdapter(getActivity(),words,R.color.category_family);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //creating word object instance using factory method
                Word word = words.get(position);
                releaseMediaPlayer();

                int request= mAudio.requestAudioFocus(AudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(request==mAudio.AUDIOFOCUS_REQUEST_GRANTED) {
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

    /**
     * Clean up the media player by releasing its resources.
     */
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

            mAudio.abandonAudioFocus(AudioFocusChangeListener);
        }
    }

}
