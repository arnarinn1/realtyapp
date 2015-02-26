package is.arnar.realty.presentation.view;

import java.util.List;

import is.arnar.realty.datacontracts.RealtyData;

public interface IMapsView extends IView
{
    void Display(List<RealtyData> realties);
}