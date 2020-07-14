package android.example.miwok;

public class Word {

    private String mDefaultTranslation;


    private String mMiwokTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;
    private int mMediaResourceId;

    public Word(String defaultTranslation, String miwokTranslation, int MediaResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mMediaResourceId = MediaResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int MediaResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mMediaResourceId = MediaResourceId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getMediaResourceId() {
        return mMediaResourceId;
    }
}
