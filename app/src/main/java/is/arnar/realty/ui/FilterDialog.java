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
import is.arnar.realty.ui.customviews.MultiSelectSpinner;
import is.arnar.realty.ui.customviews.RangeSeekBar;

public class FilterDialog extends DialogFragment
{
    public static final String REALTY_CODES = "is.arnar.realty.ui.REALTYCODES";
    public static final String REALTY_TYPES = "is.arnar.realty.ui.REALTYTYPES";
    public static final String Q_LOWER_PRICE_RANGE = "is.arnar.realty.ui.LOWERPRICE";
    public static final String Q_UPPER_PRICE_RANGE = "is.arnar.realty.ui.UPPERPRICE";
    public static final String Q_LOWER_ROOM_RANGE = "is.arnar.realty.ui.LOWERROOM";
    public static final String Q_UPPER_ROOM_RANGE = "is.arnar.realty.ui.UPPERROOM";

    @InjectView(R.id.multiSelectCodes) MultiSelectSpinner mMultiSpinnerCodes;
    @InjectView(R.id.multiSelectTypes) MultiSelectSpinner mMultiSpinnerTypes;
    @InjectView(R.id.save_filter) Button mSaveFilter;

    @InjectView(R.id.filterPriceLayout) LinearLayout mPriceLayout;
    @InjectView(R.id.priceFrom) TextView mPriceFrom;
    @InjectView(R.id.priceTo) TextView mPriceTo;

    @InjectView(R.id.filterRoomLayout) LinearLayout mRoomsLayout;
    @InjectView(R.id.lowerRoomNumber) TextView mLowerRoomNumber;
    @InjectView(R.id.upperRoomNumber) TextView mUpperRoomNumber;

    private RangeSeekBar<Integer> mSeekPriceBar;
    private RangeSeekBar<Integer> mSeekRoomBar;

    private String mPriceFromStr;
    private String mPriceToStr;
    private String mLowerRoomStr;
    private String mUpperRoomStr;

    private Action action;

    public FilterDialog() {}

    public static FilterDialog newInstance(Action action)
    {
        FilterDialog f = new FilterDialog();
        f.setAction(action);
        return f;
    }

    public void setAction(Action action)
    {
        this.action = action;
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
        AddRoomSeekBar();

        SetPriceValues();
        SetRoomValues();

        SetRealtyCodeValues();
        SetRealtyTypes();
    }

    private void GetStringValues()
    {
        mPriceToStr = getActivity().getString(R.string.priceTo);
        mPriceFromStr = getActivity().getString(R.string.priceFrom);

        mLowerRoomStr = getActivity().getString(R.string.lowerRoom);
        mUpperRoomStr = getActivity().getString(R.string.upperRoom);
    }

    private void AddPriceSeekBar()
    {
        mSeekPriceBar = new RangeSeekBar<>(5, 100, getActivity());

        mSeekPriceBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                String priceFromValue = minValue.toString();
                String priceToValue = maxValue.toString();
                mPriceFrom.setText(mPriceFromStr + " " + priceFromValue + " m.kr");
                mPriceTo.setText(mPriceToStr + " " + priceToValue + " m.kr");
            }
        });

        mPriceLayout.addView(mSeekPriceBar);
    }

    private void AddRoomSeekBar()
    {
        mSeekRoomBar = new RangeSeekBar<>(1, 9, getActivity());

        mSeekRoomBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                String roomsFrom = minValue.toString();
                String roomsTo = maxValue.toString();
                mLowerRoomNumber.setText(mLowerRoomStr + " " + roomsFrom);
                mUpperRoomNumber.setText(mUpperRoomStr + " " + roomsTo);
            }
        });

        mRoomsLayout.addView(mSeekRoomBar);
    }

    private void SetPriceValues()
    {
        int priceFrom = Prefs.with(getActivity()).GetInt(Q_LOWER_PRICE_RANGE, 5);
        int priceTo = Prefs.with(getActivity()).GetInt(Q_UPPER_PRICE_RANGE, 100);

        mSeekPriceBar.setSelectedMinValue(priceFrom);
        mSeekPriceBar.setSelectedMaxValue(priceTo);

        String priceFromValue = Integer.toString(priceFrom);
        String priceToValue = Integer.toString(priceTo);
        mPriceFrom.setText(mPriceFromStr + " " + priceFromValue + " m.kr");
        mPriceTo.setText(mPriceToStr + " " + priceToValue  + " m.kr");
    }

    private void SetRoomValues()
    {
        int lowerRoom = Integer.parseInt(Prefs.with(getActivity()).GetString(Q_LOWER_ROOM_RANGE, "1"));
        int upperRoom = Integer.parseInt(Prefs.with(getActivity()).GetString(Q_UPPER_ROOM_RANGE, "9"));

        mSeekRoomBar.setSelectedMinValue(lowerRoom);
        mSeekRoomBar.setSelectedMaxValue(upperRoom);

        mLowerRoomNumber.setText(String.format("%s %d", mLowerRoomStr, lowerRoom));
        mUpperRoomNumber.setText(String.format("%s %d", mUpperRoomStr, upperRoom));
    }

    private void SetRealtyCodeValues()
    {
        String[] strings = {"Öll svæði", "Reykjavík - 101", "Seltjarnarnes - 170", "Akureyri - 603", "Kópavogur - 200"};
        mMultiSpinnerCodes.setItems(strings);

        Set<String> codes = Prefs.with(getActivity()).GetStringSet(REALTY_CODES, new HashSet<String>());
        if (codes.isEmpty())
           mMultiSpinnerCodes.setSelection(new String[]{"Öll svæði"});

        List<String> selections = new ArrayList<>();
        for(String code : codes)
        {
            selections.add(code);
        }

        if (!selections.isEmpty())
            mMultiSpinnerCodes.setSelection(selections);

        mMultiSpinnerCodes.showSpinnerText();
    }

    private void SetRealtyTypes()
    {
        String[] strings = {"Allar tegundir", "Einbýli", "Fjölbýli", "Parhús"};
        mMultiSpinnerTypes.setItems(strings);

        Set<String> types = Prefs.with(getActivity()).GetStringSet(REALTY_TYPES, new HashSet<String>());
        if (types.isEmpty())
            mMultiSpinnerTypes.setSelection(new String[]{"Allar tegundir"});

        List<String> selections = new ArrayList<>();
        for(String type : types)
        {
            selections.add(type);
        }

        if (!selections.isEmpty())
            mMultiSpinnerTypes.setSelection(selections);

        mMultiSpinnerTypes.showSpinnerText();
    }

    @OnClick(R.id.save_filter)
    public void OnClickSaveFilter()
    {
        CachePriceRange();
        CacheRoomRange();
        CacheRealtyCodes();
        CacheRealtyTypes();

        dismiss();

        action.PerformAction();
    }

    private void CacheRoomRange()
    {
        String lowerRoom = Integer.toString(mSeekRoomBar.getSelectedMinValue());
        String upperRoom = Integer.toString(mSeekRoomBar.getSelectedMaxValue());

        Prefs.with(getActivity()).Save(Q_LOWER_ROOM_RANGE, lowerRoom);
        Prefs.with(getActivity()).Save(Q_UPPER_ROOM_RANGE, upperRoom);
    }

    private void CachePriceRange()
    {
        int lowerPrice = mSeekPriceBar.getSelectedMinValue();
        int upperPrice = mSeekPriceBar.getSelectedMaxValue();

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

    private void CacheRealtyTypes()
    {
        List<String> items = mMultiSpinnerTypes.getSelectedStrings();
        HashSet<String> types = new HashSet<>();
        for (String item : items)
        {
            if (!item.equals("Allar tegundir"))
                types.add(item);
        }

        Prefs.with(getActivity()).Save(REALTY_TYPES, types);
    }
}
