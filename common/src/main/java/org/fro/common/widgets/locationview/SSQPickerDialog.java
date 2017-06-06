package org.fro.common.widgets.locationview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.fro.common.R;
import org.fro.common.widgets.locationview.entity.CityData;
import org.fro.common.widgets.locationview.entity.CountryData;
import org.fro.common.widgets.locationview.entity.ProvinceData;

import java.util.ArrayList;

/**
 * 省市区选择对话框
 *
 * @author pengjian
 */
public class SSQPickerDialog extends BaseDialogTimer implements View.OnClickListener {
    public static final int DIALOG_MODE_BOTTOM = 1;
    private Context mContext;
    private WheelView wvProvince;
    private WheelView wvCity;
    private WheelView wvCountry;
    private View vDialog;
    private View vDialogChild;
    private ViewGroup VDialogPicker;
    private TextView tvTitle;
    private TextView btnSure;
    private TextView btnCancel;
    private ArrayList<ProvinceData> array_Province = new ArrayList<>();
    private ArrayList<CityData> array_City = new ArrayList<>();
    private ArrayList<CountryData> array_County = new ArrayList<>();

    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CountryAdapter countryAdapter;
    private ProvinceData selectProvince;
    private CityData selectCity;
    private CountryData selectCounty;
    private boolean cycle = false;
    private String strTitle = "选择地址";

    private OnDatePickListener onDatePickListener;

    public SSQPickerDialog(Context context, ProvinceData mProvince, CityData selectCity, CountryData selectCounty, ArrayList<ProvinceData> array_Province) {
        super(context, R.layout.dialog_picker_bottom);
        this.mContext = context;
        this.selectProvince = mProvince;
        this.selectCity = selectCity;
        this.selectCounty = selectCounty;
        this.array_Province = array_Province;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        VDialogPicker = (ViewGroup) findViewById(R.id.ly_dialog_picker);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        // 此处相当于布局文件中的Android:layout_gravity属性
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.weight = 1;
        wvProvince = new WheelView(mContext);
        wvProvince.setLayoutParams(lp);
        VDialogPicker.addView(wvProvince);

        wvCity = new WheelView(mContext);
        wvCity.setLayoutParams(lp);
        VDialogPicker.addView(wvCity);

        wvCountry = new WheelView(mContext);
        wvCountry.setLayoutParams(lp);
        VDialogPicker.addView(wvCountry);

        vDialog = findViewById(R.id.ly_dialog);
        vDialogChild = findViewById(R.id.ly_dialog_child);
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
        btnSure = (TextView) findViewById(R.id.btn_dialog_sure);
        btnCancel = (TextView) findViewById(R.id.btn_dialog_cancel);

        tvTitle.setText(strTitle);
        vDialog.setOnClickListener(this);
        vDialogChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        if (null != btnCancel) {
            btnCancel.setOnClickListener(this);
        }

        if (selectProvince == null) {
            selectProvince = array_Province.get(0);
            selectCity = selectProvince.getChildren().get(0);
            selectCounty = selectCity.getChildren().get(0);
            array_City = selectProvince.getChildren();
            array_County = selectCity.getChildren();
        } else {
            array_City = selectProvince.getChildren();
            array_County = selectCity.getChildren();
        }

        provinceAdapter = new ProvinceAdapter(mContext, array_Province.indexOf(selectProvince));
        wvProvince.setVisibleItems(5);
        wvProvince.setCyclic(cycle);
        wvProvince.setViewAdapter(provinceAdapter);

        wvProvince.setCurrentItem(array_Province.indexOf(selectProvince));
        provinceAdapter.notifyDataChangedEvent();

        cityAdapter = new CityAdapter(mContext, array_City.indexOf(selectCity));
        wvCity.setVisibleItems(5);
        wvCity.setCyclic(cycle);
        wvCity.setViewAdapter(cityAdapter);

        array_City = array_Province.get(wvProvince.getCurrentItem()).getChildren();
        wvCity.setCurrentItem(array_City.indexOf(selectCity));
        cityAdapter.notifyDataChangedEvent();


        countryAdapter = new CountryAdapter(mContext, array_County.indexOf(selectCounty));
        wvCountry.setVisibleItems(5);
        wvCountry.setCyclic(cycle);
        wvCountry.setViewAdapter(countryAdapter);

        array_County = array_City.get(wvCity.getCurrentItem()).getChildren();
        wvCountry.setCurrentItem(array_County.indexOf(selectCounty));
        countryAdapter.notifyDataChangedEvent();

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectProvince = array_Province.get(wheel.getCurrentItem());
                selectCity = selectProvince.getChildren().get(0);
                selectCounty = selectCity.getChildren().get(0);
                array_City = array_Province.get(wheel.getCurrentItem()).getChildren();
                array_County = array_City.get(0).getChildren();
                wvCity.setCurrentItem(0);
                wvCity.setViewAdapter(new CityAdapter(mContext, 0));
                wvCountry.setViewAdapter(new CountryAdapter(mContext, 0));
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                wvProvince.setCurrentItem(wheel.getCurrentItem());
                selectProvince = array_Province.get(wheel.getCurrentItem());

                array_City = array_Province.get(wheel.getCurrentItem()).getChildren();
                selectCity = array_City.get(0);

                array_County = selectCity.getChildren();
                selectCounty = array_County.get(0);

                wvCity.setCurrentItem(0);
                wvCountry.setCurrentItem(0);
            }
        });

        wvCity.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectCity = array_City.get(wheel.getCurrentItem());
                array_County = selectCity.getChildren();
                selectCounty = selectCity.getChildren().get(0);
                wvCity.setCurrentItem(wheel.getCurrentItem());
                wvCountry.setViewAdapter(new CountryAdapter(mContext, 0));
            }
        });

        wvCity.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                array_County = array_City.get(wheel.getCurrentItem()).getChildren();
                selectCity = array_City.get(wheel.getCurrentItem());
                selectCounty = selectCity.getChildren().get(0);
                wvCity.setCurrentItem(wheel.getCurrentItem());
                wvCountry.setCurrentItem(0);
                countryAdapter.notifyDataChangedEvent();
            }
        });
        wvCountry.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                wvCountry.setCurrentItem(newValue);
                selectCounty = array_County.get(wheel.getCurrentItem());
            }
        });

        wvCountry.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectCounty = array_County.get(wheel.getCurrentItem());
            }
        });


    }


    /**
     * 设置dialog弹出框模式
     *
     * @param dialogMode 从屏幕底部弹出
     */
    public void setDialogMode(int dialogMode) {
        if (dialogMode == DIALOG_MODE_BOTTOM) {
            resetContent(R.layout.dialog_picker_bottom);
            setAnimation(R.style.AnimationBottomDialog);
            setGravity(Gravity.BOTTOM);
        }
    }

    public void setTitle(String title) {
        this.strTitle = title;
    }

    @Override
    protected int dialogWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay()
                .getMetrics(metric);
        return metric.widthPixels;
    }

    public void setDatePickListener(OnDatePickListener onDatePickListener) {
        this.onDatePickListener = onDatePickListener;
    }

    @Override
    public void onClick(View v) {
        if (v == btnSure) {
            if (onDatePickListener != null) {
                onDatePickListener.onClick(selectProvince, selectCity, selectCounty);
            }
        } else if (v == btnCancel) {

        } else if (v == vDialogChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();
    }

    public interface OnDatePickListener {
        void onClick(ProvinceData selectProvince, CityData selectCity, CountryData selectCounty);
    }


    /**
     * 省
     */
    private class ProvinceAdapter extends AbstractWheelTextAdapter {
        protected ProvinceAdapter(Context context, int currentItem) {
            super(context, R.layout.item_ssq, NO_RESOURCE, currentItem);
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return array_Province.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return array_Province.get(index).getName();
        }

    }

    /**
     * 城市
     */
    private class CityAdapter extends AbstractWheelTextAdapter {

        protected CityAdapter(Context context, int currentItem) {
            super(context, R.layout.item_ssq, NO_RESOURCE, currentItem);
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return array_City.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return array_City.get(index).getName();
        }

    }

    /**
     * 地区
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {

        protected CountryAdapter(Context context, int currentItem) {
            super(context, R.layout.item_ssq, NO_RESOURCE, currentItem);
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return array_County.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return array_County.get(index).getName();
        }

        @Override
        public void setTextSize(int textSize) {
            super.setTextSize(textSize);
        }
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }
}