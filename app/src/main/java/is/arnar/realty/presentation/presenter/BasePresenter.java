package is.arnar.realty.presentation.presenter;

import java.util.HashSet;
import java.util.Set;

import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.presentation.view.IView;
import is.arnar.realty.services.IRealtyService;
import is.arnar.realty.systems.Prefs;
import is.arnar.realty.ui.FilterDialog;

/**
 * A generic Presenter class
 */
public class BasePresenter<TView extends IView, TSystem extends IRealtyService, TModel>
{
    TView View;
    TSystem System;

    public BasePresenter(TView view, TSystem system)
    {
        if(view == null) throw new NullPointerException();
        if(system == null) throw new NullPointerException();

        this.View = view;
        this.System = system;
    }

    public void WaitAndPerformAction(final int wait, final Action action)
    {
        View.Busy(true);
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    synchronized (this)
                    {
                        wait(wait);
                        action.PerformAction();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            };
        }.start();
    }

    protected String RealtyCodeQueryString()
    {
        Set<String> codes = Prefs.with(View.Context()).GetStringSet(FilterDialog.REALTY_CODES, new HashSet<String>());
        if (codes.isEmpty()) return null;
        String serviceCode = "";
        for(String code : codes)
        {
            String[] strings = code.split("-");
            if (strings.length == 2)
            {
                serviceCode += strings[1].replace(" ", "") + "-";
            }
        }

        serviceCode = serviceCode.substring(0, serviceCode.length()-1);

        return serviceCode;
    }

    protected String RealtyTypeQueryString()
    {
        Set<String> types = Prefs.with(View.Context()).GetStringSet(FilterDialog.REALTY_TYPES, new HashSet<String>());
        if (types.isEmpty()) return null;
        String typeQuery = "";
        for(String type : types)
        {
            typeQuery += type + "-";
        }

        typeQuery = typeQuery.substring(0, typeQuery.length()-1);

        return typeQuery;
    }

    protected double PriceQueryParam(String extraValue, int defValue)
    {
        int value = Prefs.with(View.Context()).GetInt(extraValue, defValue);
        double factor = 1000000.0;
        return (double) value * factor;
    }
}