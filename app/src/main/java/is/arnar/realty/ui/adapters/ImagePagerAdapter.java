package is.arnar.realty.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import is.arnar.realty.R;
import is.arnar.realty.datacontracts.ImageData;

public class ImagePagerAdapter extends PagerAdapter
{
    private List<ImageData> mImages;
    private Context mContext;

    public ImagePagerAdapter(Context context, List<ImageData> urls)
    {
        mContext = context;
        mImages = urls;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position)
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.pager_image, container, false);

        String url = mImages.get(position).getUrl();

        ImageView imageView = (ImageView) layout.findViewById(R.id.image);

        Picasso.with(mContext)
                .load(url)
                .into(imageView);

        container.addView(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        return layout;
    }

    @Override
    public int getCount()
    {
        return null != mImages ? mImages.size() : 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o)
    {
        return view == o;
    }
}