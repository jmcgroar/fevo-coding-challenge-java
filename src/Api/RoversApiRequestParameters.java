package Api;

import java.time.LocalDate;

import Cache.PhotoCache;

public class RoversApiRequestParameters extends ApiRequestParameters{
    PhotoCache photoCache;
    private String camera;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer photosPerDay;

    public PhotoCache getPhotoCache (){
        return (this.photoCache);
    }

    public void setPhotoCache (PhotoCache photoCache){
        this.photoCache = photoCache;
    }

    public String getCamera (){
        return (this.camera);
    }

    public void setCamera (String camera){
        this.camera = camera;
    }

    public LocalDate getStartDate (){
        return (this.startDate);
    }

    public void setStartDate (LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getEndDate (){
        return (this.endDate);
    }

    public void setEndDate (LocalDate endDate){
        this.endDate = endDate;
    }

    public Integer getPhotosPerDay (){
        return (this.photosPerDay);
    }

    public void setPhotosPerDay (Integer photosPerDay){
        this.photosPerDay = photosPerDay;
    }
}
