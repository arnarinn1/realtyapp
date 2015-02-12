package is.arnar.realty.presentation.view;

import java.util.List;

import is.arnar.realty.datacontracts.RealtyData;

public interface IRealtyView extends IView<RealtyData>
{
     void Display(List<RealtyData> realties);
}
