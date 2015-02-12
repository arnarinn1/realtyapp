package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

public class RealtorData
{
    @SerializedName("realtorid")
    private int Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("image_url")
    private String ImageUrl;

    public int getId() { return this.Id;}
    public String getName() { return this.Name;}
    public String ImageUrl() { return this.ImageUrl;}
}
