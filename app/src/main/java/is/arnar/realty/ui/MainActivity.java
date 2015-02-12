package is.arnar.realty.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import is.arnar.realty.R;

public class MainActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RealtyFragment())
                    .commit();
        }
    }
}