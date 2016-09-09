package com.example.android.miwok;

/**
 * Created by RIPU on 12-Aug-16.
 */
public class Word {

    private String mDefaultTrans; // by convention as this is private variable we name it using m as member variable

    private String mMiwokTrans;

    private int mResourceId=NoImage;

    private static final int NoImage = -1;

    private int mAudioId;

       //Creating constructor to make this class accesible to user
    public Word(String DefaultTrans, String MiwokTrans,int audioId)
    {
        mDefaultTrans= DefaultTrans;
        mMiwokTrans = MiwokTrans;
        mAudioId = audioId;
    }

    public Word(String DefaultTrans,String MiwokTrans,int Rid,int audioID)
    {
        mDefaultTrans=DefaultTrans;
        mMiwokTrans = MiwokTrans;
        mResourceId = Rid;
        mAudioId = audioID;

    }
    //Method to get the translated english or default word
   public String getDefaultTrans()
   {
       return mDefaultTrans;
   }

    //Method to get the translated Miwok word
    public String getMiwokTrans(){
        return mMiwokTrans;
    }

    public int getResourceId(){
        return mResourceId;
    }

    public boolean trueImage(){
        return mResourceId!=NoImage;
    }

    public int getAudioId(){
        return mAudioId;
    }

}
