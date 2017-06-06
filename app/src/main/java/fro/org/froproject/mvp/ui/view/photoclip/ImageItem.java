package fro.org.froproject.mvp.ui.view.photoclip;

/**
 * 相册图片实体类
 * Create by shixm on 2016/10/29
 */
public class ImageItem {
    private int imageId;
    private String imagePath;
    private long date;
    private boolean isImage = true;

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
