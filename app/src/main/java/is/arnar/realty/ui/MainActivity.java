package is.arnar.realty.ui;

import android.os.Bundle;
import android.view.Window;

import is.arnar.realty.R;

public class MainActivity extends BaseActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RealtyFragment())
                    .commit();
        }
    }
}