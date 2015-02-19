package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RealtorData implements Serializable
{
    @SerializedName("realtorid")
    private int Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("image_url")
    private String ImageUrl;

    public int getId() { return this.Id;}
    public String getName() { return this.Name;}
    public String getImageUrl() { return this.ImageUrl;}
}
