package org.fro.common.widgets.tab.tablayout;


import org.fro.common.widgets.tab.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public String title;
//    public int titleColor;
//    public int titleColorUn;
    public int selectedIcon;
    public int unSelectedIcon;

    public TabEntity(String title,  int selectedIcon, int unSelectedIcon) {
        this.title = title;
//        this.titleColor = titleColor;
//        this.titleColorUn = titleColorUn;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

//    @Override
//    public int getTabTitleColorUn() {
//        return titleColorUn;
//    }
//
//    @Override
//    public int getTabTitleColor() {
//        return titleColor;
//    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }

}
