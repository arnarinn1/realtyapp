package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RealtyData
{
    @SerializedName("realtyid")
    private int Id;

    @SerializedName("name")
    private String Name;

    @SerializedName("Price")
    private double Price;

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

    @SerializedName("created_at")
    private String CreatedAt;

    @SerializedName("updated_at")
    private String UpdatedAt;

    public int getId() { return this.Id;}
    public String getName() { return this.Name;}
    public double getPrice() { return this.Price;}
    public double getAssessmentValue() { return this.AssessmentValue;}
    public double getFireInsuranceValue() { return this.FireInsuranceValue;}
    public String getLocation() { return this.Location;}
    public RealtorData getRealtor() { return this.Realtor;}
    public RealtyCodeData getRealtyCode() { return this.RealtyCode;}
    public List<ImageData> getImages() { return this.Images;}

    public String getCreatedAt() { return this.CreatedAt;}
    public String getUpdatedAt() { return this.UpdatedAt;}
}
