package is.arnar.realty.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import is.arnar.realty.R;
import is.arnar.realty.datacontracts.RealtyData;
import is.arnar.realty.ui.transformers.DeptPageTransformer;

public class RealtyAdapter extends BaseAdapter
{
    private Context mContext;
    private List<RealtyData> realties;

    public RealtyAdapter(Context context, List<RealtyData> realties)
    {
        this.mContext = context;
        this.realties = realties;
    }

    static class PagerHolder
    {
        public PagerHolder(View view)
        {
            ButterKnife.inject(this, view);
        }

        @InjectView(R.id.lvImageViewPager) ImageView imageViewPager;
        @InjectView(R.id.lvRealtyName) TextView realtyName;
        @InjectView(R.id.lvRealtyCode) TextView realtyCode;
        @InjectView(R.id.lvRealtyPrice) TextView realtyPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        final PagerHolder holder;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.listview_viewpager, parent, false);
            holder = new PagerHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder = (PagerHolder) row.getTag();
        }

        final RealtyData realty = getItem(position);

        holder.realtyName.setText(realty.getName());
        holder.realtyCode.setText(realty.getRealtyCode().getNameAndCode());
        holder.realtyPrice.setText(realty.getPriceValue());

        Picasso.with(mContext).load(realty.getImages().get(0).getUrl()).into(holder.imageViewPager);

        return row;
    }

    @Override
    public int getCount() {
        return (realties == null) ? 0 : realties.size();
    }

    @Override
    public RealtyData getItem(int position) {
        return realties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return realties.indexOf(getItem(position));
    }
}