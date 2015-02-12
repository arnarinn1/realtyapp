package is.arnar.realty.presentation.view;

import android.support.v4.app.FragmentActivity;

import retrofit.RetrofitError;

public interface IView<TModel>
{
    TModel Model();
    FragmentActivity Context();
    void ShowError(RetrofitError ex);
    void Busy(boolean isBusy);
}