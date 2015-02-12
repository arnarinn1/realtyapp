package is.arnar.realty.presentation.presenter;

import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.presentation.view.IView;

/**
 * A generic Presenter class
 */
public class BasePresenter<TView extends IView<TModel>, TSystem, TModel>
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
}