package is.arnar.realty.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.systems.Prefs;

public class FilterDialog extends DialogFragment
{
    public static final String REALTY_CODES = "is.arnar.realty.ui.REALTYCODES";
    public static final String Q_LOWER_PRICE_RANGE = "is.arnar.realty.ui.LOWERPRICE";
    public static final String Q_UPPER_PRICE_RANGE = "is.arnar.realty.ui.UPPERPRICE";

    @InjectView(R.id.multiSelect) MultiSelectSpinner mMultiSpinnerCodes;
    @InjectView(R.id.save_filter) Button mSaveFilter;
    @InjectView(R.id.price) LinearLayout mPriceLayout;
    @InjectView(R.id.priceFrom) TextView mPriceFrom;
    @InjectView(R.id.priceTo) TextView mPriceTo;

    private RangeSeekBar<Integer> mSeekBar;

    private String mPriceFromStr;
    private String mPriceToStr;

    private Action action;

    public FilterDialog() {}

    public static FilterDialog newInstance(Action action)
    {
        FilterDialog f = new FilterDialog();
        f.setAction(action);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_TITLE, R.style.CommentLikeDialog_PopUpAnimation);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dialog_filter, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        GetStringValues();
        AddPriceSeekBar();

        SetPriceValues();

        SetRealtyCodeValues();
    }

    private void SetRealtyCodeValues()
    {
        String[] strings = {"Öll svæði", "Reykjavík - 101", "Seltjarnarnes - 170", "Akureyri - 603"};
        mMultiSpinnerCodes.setItems(strings);

        Set<String> codes = Prefs.with(getActivity()).GetStringSet(REALTY_CODES, new HashSet<String>());
        if (codes.isEmpty())
           mMultiSpinnerCodes.setSelection(new String[]{"Öll svæði"});

        List<String> selections = new ArrayList<>();
        for(String code : codes)
        {
            selections.add(code);
        }

        mMultiSpinnerCodes.setSelection(selections);
        mMultiSpinnerCodes.showSpinnerText();
    }

    private void GetStringValues()
    {
        mPriceToStr = getActivity().getString(R.string.priceTo);
        mPriceFromStr = getActivity().getString(R.string.priceFrom);
    }

    private void SetPriceValues()
    {
        int priceFrom = Prefs.with(getActivity()).GetInt(Q_LOWER_PRICE_RANGE, 5);
        int priceTo = Prefs.with(getActivity()).GetInt(Q_UPPER_PRICE_RANGE, 100);

        mSeekBar.setSelectedMinValue(priceFrom);
        mSeekBar.setSelectedMaxValue(priceTo);

        String priceFromValue = Integer.toString(priceFrom);
        String priceToValue = Integer.toString(priceTo);
        mPriceFrom.setText(mPriceFromStr + " " + priceFromValue + " m.kr");
        mPriceTo.setText(mPriceToStr + " " + priceToValue  + " m.kr");
    }

    @OnClick(R.id.save_filter)
    public void OnClickSaveFilter()
    {
        CachePriceRange();
        CacheRealtyCodes();

        dismiss();

        action.PerformAction();
    }

    private void CachePriceRange()
    {
        int lowerPrice = mSeekBar.getSelectedMinValue();
        int upperPrice = mSeekBar.getSelectedMaxValue();

        Prefs.with(getActivity()).Save(Q_LOWER_PRICE_RANGE, lowerPrice);
        Prefs.with(getActivity()).Save(Q_UPPER_PRICE_RANGE, upperPrice);
    }

    private void CacheRealtyCodes()
    {
        List<String> items = mMultiSpinnerCodes.getSelectedStrings();
        HashSet<String> codes = new HashSet<>();
        for (String item : items)
        {
            String [] strings = item.split("-");
            if (strings.length == 2)
            {
                codes.add(item);
            }
        }

        Prefs.with(getActivity()).Save(REALTY_CODES, codes);
    }

    private void AddPriceSeekBar()
    {
        mSeekBar = new RangeSeekBar<>(5, 100, getActivity());

        mSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>()
        {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue)
            {
                String priceFromValue = minValue.toString();
                String priceToValue = maxValue.toString();
                mPriceFrom.setText(mPriceFromStr + " " + priceFromValue + " m.kr");
                mPriceTo.setText(mPriceToStr + " " + priceToValue + " m.kr");
            }
        });

        mPriceLayout.addView(mSeekBar);
    }

    public void setAction(Action action)
    {
        this.action = action;
    }
}
