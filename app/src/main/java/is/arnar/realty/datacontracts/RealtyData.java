package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RealtyData implements Serializable
{
    @SerializedName("realtyid")
    private int Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("price")
    private double Price;

    @SerializedName("description")
    private String Description;

    @SerializedName("assessment_value")
    private double AssessmentValue;

    @SerializedName("fire_insurance_value")
    private double FireInsuranceValue;

    @SerializedName("location")
    private String Location;

    @SerializedName("realtor")
    private RealtorData Realtor;

    @SerializedName("realty_code")
    private RealtyCodeData RealtyCode;

    @SerializedName("images")
    private List<ImageData> Images;

    @SerializedName("properties")
    private List<PropertyData> Properties;

    @SerializedName("created_at")
    private String CreatedAt;

    @SerializedName("updated_at")
    private String UpdatedAt;

    public int getId() { return this.Id;}
    public String getName() { return this.Name;}
    public double getPrice() { return this.Price;}
    public String getDescription() {return this.Description;}
    public double getAssessmentValue() { return this.AssessmentValue;}
    public double getFireInsuranceValue() { return this.FireInsuranceValue;}
    public String getLocation() { return this.Location;}
    public RealtorData getRealtor() { return this.Realtor;}
    public RealtyCodeData getRealtyCode() { return this.RealtyCode;}
    public List<ImageData> getImages() { return this.Images;}
    public List<PropertyData> getProperties() { return this.Properties;}

    public String getCreatedAt() { return this.CreatedAt;}
    public String getUpdatedAt() { return this.UpdatedAt;}

    public String getPriceValue()
    {
        double price = this.Price / 1000000.0;
        return String.format("%.1f m.kr", price);
    }

    public String getPriceValueBySeperator()
    {
        return String.format("%,.2f", this.Price);
    }

    public String getAssessmentValueBySeperator()
    {
        return String.format("%,.2f", this.AssessmentValue);
    }

    public String getFireInsuranceValueBySeperator()
    {
        return String.format("%,.2f", this.FireInsuranceValue);
    }
}
