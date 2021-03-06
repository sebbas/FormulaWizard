package com.ndroidstudios.android.formulawizard;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.ndroidstudios.android.helper.FontHelper;
import com.ndroidstudios.android.helper.VoiceHelper;

public class MainActivity extends SherlockFragmentActivity {

	private static String[] tabNames = {"Formulas","Custom","Voice Calc"};
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	VoiceHelper voiceHelper = new VoiceHelper(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);

		setContentView(mViewPager);
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mTabsAdapter = new TabsAdapter(this, mViewPager);
		mTabsAdapter.addTab(
				bar.newTab().setText("Formulas"),
				FormulaFragment.class, null);
			
		mTabsAdapter.addTab(
				bar.newTab().setText("Custom"),
				CustomFormulaFragment.class, null);
		
		// Only add voice tab if the device is voice recognition capable
		if(voiceHelper.isVoiceCapable()) {
			mTabsAdapter.addTab(
				bar.newTab().setText("Voice Calc"),
				VoiceFragment.class, null);
		}	
		styleTabs(bar);
	}
	
	@Override
	protected void onDestroy() {
		getSherlock().dispatchDestroy();
		try {
			super.onDestroy();
		} catch (Exception e) {
	    }
	}
	
	private void styleTabs(ActionBar bar) {
		// Apply a custom font to the tabs
		for (int i = 0; i < bar.getTabCount(); i++){
		    LayoutInflater inflater = LayoutInflater.from(this);
		    View customView = inflater.inflate(R.layout.tab_title, null);

		    TextView tabTitle = (TextView) customView.findViewById(R.id.action_custom_title);
		    tabTitle.setText(tabNames[i]);
		    FontHelper.overrideFonts(getApplicationContext(), tabTitle);

		    bar.getTabAt(i).setCustomView(customView);
		}
	}

	private static class TabsAdapter extends FragmentPagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo
		{
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> _class, Bundle _args)
			{
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(SherlockFragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}
}