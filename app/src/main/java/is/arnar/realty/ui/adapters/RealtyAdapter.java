package is.arnar.realty.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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

    private SparseIntArray mPagerPositions = new SparseIntArray();

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

        @InjectView(R.id.lvImageViewPager)  ViewPager imageViewPager;
        @InjectView(R.id.lvRealtyName) TextView realtyName;
        @InjectView(R.id.lvRealtyCode) TextView realtyCode;
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

        ImagePagerAdapter adapter = new ImagePagerAdapter(mContext, realty.getImages());

        holder.realtyName.setText(realty.getName());
        holder.realtyCode.setText(realty.getRealtyCode().getNameAndCode());

        holder.imageViewPager.setAdapter(adapter);
        holder.imageViewPager.setPageTransformer(true, new DeptPageTransformer());

        Integer pagerPosition = mPagerPositions.get(position);
        holder.imageViewPager.setCurrentItem(pagerPosition);

        holder.imageViewPager.setOnPageChangeListener(new MyPageChangeListener(position, holder.imageViewPager));

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

    private class MyPageChangeListener extends ViewPager.SimpleOnPageChangeListener
    {
        private int id;
        private ViewPager pager;

        public MyPageChangeListener(int id, ViewPager pager)
        {
            this.id = id;
            this.pager = pager;
        }

        @Override
        public void onPageSelected(int position)
        {
            //If Page is visible, put the position of the viewpager in the sparse array
            if (pager.isShown())
            {
                mPagerPositions.put(id, position);
            }
        }
    }
}