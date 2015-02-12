package is.arnar.realty.datacontracts;

import com.google.gson.annotations.SerializedName;

public class TypeItemData
{
    @SerializedName("type_itemid")
    private int TypeItemId;

    @SerializedName("type_name")
    private String TypeName;

    public int getTypeItemId() { return this.TypeItemId;}
    public String getTypeName() { return this.TypeName;}
}
