package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

public class PropertyData
{
    @SerializedName("realty_propertyid")
    private int Id;

    @SerializedName("realtyid")
    private int RealtyId;

    @SerializedName("type_numberid")
    private int TypeNumberId;

    @SerializedName("value")
    private String Value;

    @SerializedName("type_item")
    private TypeItemData TypeItem;

    @SerializedName("created_at")
    private String CreatedAt;

    @SerializedName("updated_at")
    private String UpdatedAt;

    public int getId () { return this.Id;}
    public int getRealtyId () { return this.RealtyId;}
    public int getTypeNumberId () { return this.TypeNumberId;}
    public String getValue() { return this.Value;}
    public TypeItemData getTypeItem() { return this.TypeItem;}

    public String getCreatedAt() { return this.CreatedAt;}
    public String getUpdatedAt() { return this.UpdatedAt;}
}
