package is.arnar.realty.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.PropertyData;
import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.ui.adapters.ImagePagerAdapter;
import is.arnar.realty.ui.transformers.DeptPageTransformer;

public class DetailedRealtyActivity extends BaseActivity
{
    private RealtyData mRealty;

    @InjectView(R.id.transparent_image) ImageView mTransparentMapsImage;
    @InjectView(R.id.main_scrollview) ScrollView mMainScrollView;

    @InjectView(R.id.imageViewPager) ViewPager mViewPager;
    @InjectView(R.id.realtyName)     TextView mRealtyTextView;
    @InjectView(R.id.realtyLocation) TextView mRealtyLocation;
    @InjectView(R.id.realtorName)    TextView mRealtorTextView;
    @InjectView(R.id.realtorImage)   ImageView mRealtorImageView;

    @InjectView(R.id.realtyDescription)   TextView mRealtyDescription;

    @InjectView(R.id.realtyProperties)   TextView mRealtyProperties;

    @InjectView(R.id.realtyPrice)               TextView mRealtyPriceTextView;
    @InjectView(R.id.realtyFireInsuranceValue)  TextView mRealtyFireInsuranceTextView;
    @InjectView(R.id.realtyAssessmentValue)     TextView mRealtyAssessmentTextView;

    @InjectView(R.id.realtorEmail)           TextView mRealtorEmail;
    @InjectView(R.id.realtorPhoneNumber)     TextView mRealtorPhoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_realty_detailed);
        Initialize();
    }

    private void Initialize()
    {
        ButterKnife.inject(this);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetRealty();

        //ImageSection
        mViewPager.setAdapter(new ImagePagerAdapter(this, mRealty.getImages()));
        mViewPager.setPageTransformer(true, new DeptPageTransformer());

        //Basic Realty Information Section
        mRealtyTextView.setText(mRealty.getName());
        mRealtyLocation.setText(mRealty.getRealtyCode().getNameAndCode());

        //Price Section
        SetPriceValues();

        //Description Section
        mRealtyDescription.setText(mRealty.getDescription());

        //Google Maps Position
        SetMapsPosition();
        SetTouchListenerForMapsImage();

        //Properties Section
        SetProperties();

        //Realtor Section
        mRealtorTextView.setText(mRealty.getRealtor().getName());
        Picasso.with(this).load(mRealty.getRealtor().getImageUrl()).into(mRealtorImageView);
        mRealtorEmail.setText(mRealty.getRealtor().getEmail());
        mRealtorPhoneNumber.setText(mRealty.getRealtor().getPhoneNumber());
    }

    private void GetRealty()
    {
        Intent intent = getIntent();
        mRealty = (RealtyData) intent.getSerializableExtra(RealtyFragment.EXTRA_REALTY);
    }

    private void SetPriceValues()
    {
        String priceText = mRealtyPriceTextView.getText().toString();
        mRealtyPriceTextView.setText(priceText + " " + mRealty.getPriceValueBySeperator() + " kr.");

        String assessmentText = mRealtyAssessmentTextView.getText().toString();
        mRealtyAssessmentTextView.setText(assessmentText + " " + mRealty.getAssessmentValueBySeperator() + " kr.");

        String fireInsuranceText = mRealtyFireInsuranceTextView.getText().toString();
        mRealtyFireInsuranceTextView.setText(fireInsuranceText + " " + mRealty.getFireInsuranceValueBySeperator() + " kr.");
    }

    private void SetProperties()
    {
        StringBuilder sb = new StringBuilder();
        List<PropertyData> properties = mRealty.getProperties();

        for(PropertyData property : properties)
        {
            sb.append(property.getTypeItem().getTypeName()).append(": ").append(property.getValue());
            sb.append("\n");
        }

        mRealtyProperties.setText(sb.toString());
    }

    private void SetMapsPosition()
    {
        GoogleMap map;
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        final LatLng realtyLocation = new LatLng(mRealty.getLatitude(), mRealty.getLongitude());
        Marker marker = map.addMarker(new MarkerOptions()
                .position(realtyLocation)
                .title(mRealty.getName())
                .draggable(false));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(realtyLocation, 14));
    }

    private void SetTouchListenerForMapsImage()
    {
        mTransparentMapsImage.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mMainScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mMainScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mMainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }
}
