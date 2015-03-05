package is.arnar.realty.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import is.arnar.realty.R;
import is.arnar.realty.ui.enums.UserInformationType;

public class BaseActivity extends ActionBarActivity
{
    private String mNoInternetConnectionStr;
    private String mNoDataStr;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GetStringValues();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Region - Protected
     */

    protected void ShowUserInformationLayout(UserInformationType type, TextView mUserInfoText,
                                             ImageView mUserInfoImage, FrameLayout mLayoutNoConnection)
    {
        if (type == UserInformationType.NoConnection)
        {
            mUserInfoImage.setImageDrawable(getResources().getDrawable(R.drawable.no_internet));
            mUserInfoText.setText(mNoInternetConnectionStr);
        }
        else if (type == UserInformationType.NoData)
        {
            mUserInfoImage.setImageDrawable(getResources().getDrawable(R.drawable.filter));
            mUserInfoText.setText(mNoDataStr);
        }

        mLayoutNoConnection.setVisibility(View.VISIBLE);
    }

    /*
    Region - Private
     */

    private void GetStringValues()
    {
        mNoInternetConnectionStr = getString(R.string.no_internet);
        mNoDataStr = getString(R.string.no_data);
    }
}