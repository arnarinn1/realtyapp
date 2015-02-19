package is.arnar.realty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.ui.adapters.ImagePagerAdapter;
import is.arnar.realty.ui.transformers.DeptPageTransformer;

public class DetailedRealtyActivity extends ActionBarActivity
{
    private RealtyData mRealty;

    @InjectView(R.id.imageViewPager) ViewPager mViewPager;
    @InjectView(R.id.realtyName)     TextView mRealtyTextView;
    @InjectView(R.id.realtyLocation) TextView mRealtyLocation;
    @InjectView(R.id.realtorName)    TextView mRealtorTextView;
    @InjectView(R.id.realtorImage)   ImageView mRealtorImageView;

    @InjectView(R.id.realtyPrice)               TextView mRealtyPriceTextView;
    @InjectView(R.id.realtyFireInsuranceValue)  TextView mRealtyFireInsuranceTextView;
    @InjectView(R.id.realtyAssessmentValue)     TextView mRealtyAssessmentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_realty_detailed);
        Initialize();
    }

    private void Initialize()
    {
        ButterKnife.inject(this);

        GetRealty();

        mViewPager.setAdapter(new ImagePagerAdapter(this, mRealty.getImages()));
        mViewPager.setPageTransformer(true, new DeptPageTransformer());

        mRealtyTextView.setText(mRealty.getName());

        mRealtyLocation.setText(mRealty.getRealtyCode().getNameAndCode());

        //Price Section
        SetPriceValues();

        //Realtor Section
        mRealtorTextView.setText(mRealty.getRealtor().getName());
        Picasso.with(this).load(mRealty.getRealtor().getImageUrl()).into(mRealtorImageView);
    }

    private void SetPriceValues()
    {
        String priceText = mRealtyPriceTextView.getText().toString();
        mRealtyPriceTextView.setText(priceText + " " + mRealty.getPriceValueBySeperator() + " m. kr.");

        String assessmentText = mRealtyAssessmentTextView.getText().toString();
        mRealtyAssessmentTextView.setText(assessmentText + " " + mRealty.getAssessmentValueBySeperator() + " m. kr.");

        String fireInsuranceText = mRealtyFireInsuranceTextView.getText().toString();
        mRealtyFireInsuranceTextView.setText(fireInsuranceText + " " + mRealty.getFireInsuranceValueBySeperator() + " m. kr.");
    }

    private void GetRealty()
    {
        Intent intent = getIntent();
        mRealty = (RealtyData) intent.getSerializableExtra(RealtyFragment.EXTRA_REALTY);
    }
}
