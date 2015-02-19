package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageData implements Serializable
{
    @SerializedName("realty_imageid")
    private int Id;

    @SerializedName("realtyid")
    private int RealtyId;

    @SerializedName("url")
    private String Url;

    @SerializedName("created_at")
    private String CreatedAt;

    @SerializedName("updated_at")
    private String UpdatedAt;

    public int getId() { return this.Id;}
    public int getRealtyId() { return this.RealtyId;}
    public String getUrl() { return this.Url;}
    public String getCreatedAt() { return this.CreatedAt;}
    public String getUpdatedAt () { return this.UpdatedAt;}
}
