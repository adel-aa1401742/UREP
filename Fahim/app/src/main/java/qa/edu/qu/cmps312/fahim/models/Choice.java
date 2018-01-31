package qa.edu.qu.cmps312.fahim.models;

/**
 * Created by adel_ on 1/29/2018.
 */

public class Choice {
    private String imageUrl;
    private boolean isTrue;
    private boolean showFinger;

    public Choice(String imageUrl, boolean isTrue, boolean showFinger) {
        this.imageUrl = imageUrl;
        this.isTrue = isTrue;
        this.showFinger = showFinger;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public boolean isShowFinger() {
        return showFinger;
    }

    public void setShowFinger(boolean showFinger) {
        this.showFinger = showFinger;
    }
}
