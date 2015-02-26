package is.arnar.realty.presentation.view;

import android.content.Context;
import retrofit.RetrofitError;

public interface IView
{
    Context Context();
    void ShowError(RetrofitError ex);
    void Busy(boolean isBusy);
}