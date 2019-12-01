package com.xk.CarRenting.frontend.tools.dropdownmenu;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.xk.CarRenting.R;
import com.xk.CarRenting.frontend.tools.dropdownmenu.entity.FilterType;
import com.xk.CarRenting.frontend.tools.dropdownmenu.entity.FilterUrl;
import com.xk.CarRenting.frontend.tools.dropdownmenu.view.betterDoubleGrid.BetterDoubleGridView;
import com.xk.CarRenting.frontend.tools.dropdownmenu.view.doubleGrid.DoubleGridView;
import com.xk.CarRenting.utils.XmlUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.AddressPicker;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private int index;// xk
    private String content;//


    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        Log.e("DropMenuAdapter","getMenuTitle"+position);
        return titles[position];
    }


    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);
        List<FilterType> cityList = parperCityList();
        List<FilterType> timeList = parperTimeList();
        switch (position) {
            case 0:
                view = createDoubleListView(cityList,0);
                break;
            case 1:
                view = createDoubleListView(cityList,1);
                break;
            case 2:
                view = createDoubleListView(timeList,2);
                break;
        }

        return view;
    }

    private List<FilterType> parperCityList() {
        ArrayList<AddressPicker.Province> provinces = XmlUtils.parseArea(mContext, "China_area.xml");
        List<FilterType> list = new ArrayList<>();

        for (AddressPicker.Province province : provinces) {
            for (AddressPicker.City city : province.getCities()) {
                FilterType filterType = new FilterType();
                filterType.desc = city.getAreaName();
                ArrayList<String> child = new ArrayList<>();
                for (AddressPicker.County county : city.getCounties()) {
                    child.add(county.getAreaName());
                }
                filterType.child = child;
                list.add(filterType);
            }
        }
        return list;
    }


    private List<String> get31List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(get30List());
        strings.add("31st");
        return strings;
    }

    private List<String> get30List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(get29List());
        strings.add("30th");
        return strings;
    }

    private List<String> get29List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(get28List());
        strings.add("29th");
        return strings;
    }

    private List<String> get28List() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1st");
        strings.add("2nd");
        strings.add("3rd");
        strings.add("4th");
        strings.add("5th");
        strings.add("6th");
        strings.add("7th");
        strings.add("8th");
        strings.add("9th");
        strings.add("10th");
        strings.add("11th");
        strings.add("12th");
        strings.add("13th");
        strings.add("14th");
        strings.add("15th");
        strings.add("16th");
        strings.add("17th");
        strings.add("18th");
        strings.add("19th");
        strings.add("20th");
        strings.add("21th");
        strings.add("22th");
        strings.add("23th");
        strings.add("24th");
        strings.add("25th");
        strings.add("26th");
        strings.add("27th");
        strings.add("28th");
        return strings;
    }

    private List<FilterType> parperTimeList() {
        List<FilterType> list = new ArrayList<>();
        //
        FilterType filterType = new FilterType();
        filterType.desc = "Jan";
        filterType.child = get31List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Feb";
        filterType.child = get29List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Mar";
        filterType.child = get31List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Apr";
        filterType.child = get30List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "May";
        filterType.child = get31List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Jun";
        filterType.child = get30List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Jul";
        filterType.child = get31List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Aug";
        filterType.child = get31List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Sep";
        filterType.child = get30List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Oct";
        filterType.child = get31List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Nov";
        filterType.child = get30List();
        list.add(filterType);
        //
        filterType = new FilterType();
        filterType.desc = "Dec";
        filterType.child = get31List();
        list.add(filterType);
        return list;
    }

    private View createSingleListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleListPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add("" + i);
        }
        singleListView.setList(list, -1);

        return singleListView;
    }


    private View createDoubleListView(List<FilterType> filterTypes,  int pPosition) {
        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = position;
                            FilterUrl.instance().positionTitle = item.desc;
                            onFilterDone();
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String string,int index) {
                        FilterUrl.instance().doubleListLeft = item.desc;
                        FilterUrl.instance().doubleListRight = string;
                        DropMenuAdapter.this.index=index;
                        FilterUrl.instance().position = index;
                        String title;
                        if (index==2) {
                             title=item.desc+string;
                        }else{
                            title=string;
                        }
                        title= title;
                        FilterUrl.instance().positionTitle =title ;
                        content=item.desc+string;
                        onFilterDone();
                    }
                });

        //.
        comTypeDoubleListView.setIndex(pPosition);
        comTypeDoubleListView.setLeftList(filterTypes, 0);
        comTypeDoubleListView.setRightList(filterTypes.get(0).child, -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


    private View createSingleGridView() {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().singleGridPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();

                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 20; i < 39; ++i) {
            list.add(String.valueOf(i));
        }
        singleGridView.setList(list, -1);


        return singleGridView;
    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private View createDoubleGrid() {
        DoubleGridView doubleGridView = new DoubleGridView(mContext);
        doubleGridView.setOnFilterDoneListener(onFilterDoneListener);


        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        doubleGridView.setTopGridData(phases);

        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }
        doubleGridView.setBottomGridList(areas);

        return doubleGridView;
    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(index, content, "xk");
        }
    }

}
