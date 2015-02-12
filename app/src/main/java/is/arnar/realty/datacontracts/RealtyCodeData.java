package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

public class RealtyCodeData
{
    @SerializedName("realty_codeid")
    private int Id;

    @SerializedName("base_code")
    private int BaseCode;

    @SerializedName("code")
    private int Code;

    @SerializedName("name")
    private String Name;

    public int getId() { return this.Id;}
    public int getBaseCode() { return this.BaseCode;}
    public int getCode() { return this.Code;}
    public String getName() { return this.Name;}

    public String getNameAndCode() { return this.Name + ", " + this.Code;}
}
