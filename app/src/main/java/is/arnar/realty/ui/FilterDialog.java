package is.arnar.realty.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashSet;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.interfaces.Action;
import is.arnar.realty.systems.Prefs;

public class FilterDialog extends DialogFragment
{
    public static final String REALTY_CODES = "is.arnar.realty.ui.REALTYCODES";

    @InjectView(R.id.multiSelect) MultiSelectSpinner mMultiSpinnerCodes;
    @InjectView(R.id.save_filter) Button mSaveFilter;

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

        String[] strings = {"Öll svæði", "Reykjavík - 101", "Seltjarnarnes - 170", "Akureyri - 603"};
        mMultiSpinnerCodes.setItems(strings);
        mMultiSpinnerCodes.setSelection(new String[]{"Öll svæði"});
        mMultiSpinnerCodes.showSpinnerText();
    }

    @OnClick(R.id.save_filter)
    public void OnClickSaveFilter()
    {
        CacheRealtyCodes();

        dismiss();

        action.PerformAction();
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
                codes.add(strings[1]);
            }
        }

        Prefs.with(getActivity()).Save(REALTY_CODES, codes);
    }

    public void setAction(Action action)
    {
        this.action = action;
    }
}
