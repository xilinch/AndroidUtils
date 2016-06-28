package com.xiaocoder.android_ui.view.pf.gridviewadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


import com.xiaocoder.android_xcfw.util.UtilScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Cuckoo
 * @date 2014-11-18
 * @description
 * 		封装adapter，用listview实现gridview的效果。<br>
 * 	调用者只需要实现{@link IGridViewCallback#getGridItemView(int, View, ViewGroup, LinearLayout, Object)}}方法，<br>
 * 	当需要更新数据时只需要调用{@linkplain #changeData(ArrayList)}方法<br>
 * 	当listview被销毁需要调用{@link #release()}清除内存
 *
 */
public class PFGridViewAdapter extends BaseAdapter{
	private Context context = null ;
	private int numColumns = 0 ;
	private int hSpacing ;
	private int vSpacing ;
	private ArrayList<Object> dataList = null ;
	private IGridViewCallback gridViewCallback = null ;
	//占位view集合
	private ArrayList<View> emptyViewList = null ;
	private EmptyView emptyView = null ;
	//记录没一行的数据，防止在getview方式中不停的遍历
	/**key是行号，value是当前行中的值*/
	private HashMap<Integer, ArrayList> rowDataMap = null ;
	/**
	 * 初始化adapter数据
	 * @param context
	 * @param numColumns
	 * 	当前listview一行需要显示的view数量
	 * @param vSpacing
	 * 		两个item的垂直间距，单位为DP
	 * @param hSpacing
	 * 		两个Item的水平间距，单位为DP
	 * @param gridViewCallback
	 * 		初始化GridView的回调接口
	 */
	public PFGridViewAdapter(Context context, int numColumns, int vSpacing,
							 int hSpacing, IGridViewCallback gridViewCallback) {
		this.context = context ;
		this.numColumns = numColumns ;
		//计算垂直间距
		this.vSpacing = UtilScreen.dip2px(context, vSpacing) ;
		//计算水平间距
		this.hSpacing = UtilScreen.dip2px(context, hSpacing) ;
		this.gridViewCallback = gridViewCallback ;
		//初始化数据
		changeData(null);
	}

	/**
	 * 过滤用户数据，并自动补齐最后一行
	 * @param customDataList
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void filterDataList(ArrayList customDataList){
		dataList = (ArrayList<Object>) PFListUtil.clear(dataList);
		int dataCount = PFListUtil.getSize(customDataList) ;
		dataList.addAll(PFListUtil.filter(customDataList));
		if(dataCount > 0 ){
			int count = numColumns - dataCount % numColumns ;
			if( count > 0 && count < numColumns ){
				for(int i = 0 ;i < count ; i ++){
					//添加空的占位符
					if( emptyView == null ){
						emptyView = new EmptyView();
					}
					dataList.add(emptyView);
				}
			}
		}
		//初始化每一行的数据
		if( rowDataMap == null ){
			rowDataMap = new HashMap<Integer, ArrayList>();
		}
		rowDataMap.clear();
		for(int i = 0 ;i < dataList.size(); i += numColumns){
			//截取当前行的数据
			ArrayList rowDataList = new ArrayList();
			for(int j = i; j < i + numColumns; j ++ ){
				rowDataList.add(dataList.get(j));
			}
			rowDataMap.put(i / numColumns, rowDataList);
		}
	}

	/**
	 * 更新数据
	 * @param customDataList
	 */
	@SuppressWarnings("rawtypes")
	public void changeData(ArrayList customDataList){
		filterDataList(customDataList);
		notifyDataSetChanged();
	}

	/**
	 * 获取listview的行数
	 */
	@Override
	public int getCount() {
		if( PFListUtil.isEmpty(dataList)){
			return 0 ;
		}else {
			//显示的是listview的行数，不是gridview的个数
			int count = PFListUtil.getSize(dataList) ;
			int size = PFListUtil.getSize(dataList)/numColumns;
			if(count % numColumns == 0 ){
				//刚好整除，不需要加1
			}else {
				//不整除，需要加1
				size += 1;
			}
			return size;
		}
	}

	/**
	 * 废弃这方法，应为这个position是属于listivew的，和gridview的数据对不上
	 */
	@Override
	@Deprecated
	public Object getItem(int position) {
		return null;
	}

	/**
	 * 废弃这方法，应为这个position是属于listivew的，和gridview的数据对不上
	 */
	@Override
	@Deprecated
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null ){
			convertView = new RowItemView().getRowView();
		}
		if( convertView.getTag() instanceof RowItemView){
			//刷新当前的UI
			((RowItemView)convertView.getTag()).refresh(rowDataMap.get(position), position, convertView, parent);
		}
		return convertView;
	}

	/**
	 * 获取可用的占位view
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public View getAvailableEmptyView(){
		emptyViewList = (ArrayList<View>) PFListUtil.filter(emptyViewList);
		for(View view: emptyViewList ){
			if(view != null && view.getParent() == null ){
				return view ;
			}
		}
		View view = new View(context);
		view.setVisibility(View.INVISIBLE);
		if( emptyView == null ){
			emptyView = new EmptyView();
		}
		view.setTag(emptyView);
		emptyViewList.add(view);
		return view ;
	}

	/**
	 * 清除数据
	 */
	public void release(){
		PFListUtil.clear(emptyViewList);
		emptyViewList = null;
		PFListUtil.clear(dataList);
		dataList = null;
		PFListUtil.clear(rowDataMap);
		rowDataMap = null;
	}

	/**
	 * listview的行，每行包含{@linkplain PFGridViewAdapter#numColumns}个gridview
	 * @author Cuckoo
	 *
	 */
	class RowItemView {
		private LinearLayout rowView = null ;
		public RowItemView() {
			rowView = new LinearLayout(context);
			rowView.setOrientation(LinearLayout.HORIZONTAL);
			rowView.setTag(this);
			if( vSpacing > 0 ){
				//设置垂直间距
				rowView.setPadding(0, 0, 0, vSpacing);
			}
		}

		public View getRowView(){
			return rowView;
		}

		/**
		 * 假定{@linkplain PFGridViewAdapter#numColumns}初始化之后不会再更改
		 */
		public void refresh(List rowDataList, int position, View convertView, ViewGroup parent){
			int itemCount = PFListUtil.getSize(rowDataList);
			//已经存在的view数量
			int existViewCount = rowView.getChildCount();
			Object obj = null ;
			View tempView = null ;
			boolean isEmptyBean = false ;
			int startPosition = position * numColumns ;
			for(int i = 0 ;i < itemCount ;i ++){
				obj = rowDataList.get(i);
				int gridViewPosition = startPosition + i ;
				//判断当前数据是否为占位
				isEmptyBean = obj instanceof EmptyView ;
				if( i < existViewCount ){
					//当前view已经存在
					tempView = rowView.getChildAt(i);
					if(tempView.getTag() instanceof EmptyView){
						//当前view是占位view,需要替换成
						if( !isEmptyBean ){
							//view为占为view，但是需要显示成gridview, 重新创建gridview
							if( gridViewCallback != null ){
								tempView = gridViewCallback.getGridItemView(gridViewPosition, null, parent, rowView,obj);
							}
							addItemView(tempView, i, i>0, true);
						}else{
							tempView.setVisibility(View.VISIBLE);
						}
					}else {
						//当前view为gridview
						if( !isEmptyBean){
							tempView.setVisibility(View.VISIBLE);
							//重用gridview，仅仅刷新数据
							if( gridViewCallback != null ){
								gridViewCallback.getGridItemView(gridViewPosition, tempView, parent, rowView,obj);
							}
						}else {
							//用空view代替
							addItemView(getAvailableEmptyView(), i, i>0, true);
						}
					}
				}else {
					//创建新view
					if( isEmptyBean ){
						tempView = getAvailableEmptyView();
					}else {
						//获取gridview
						if( gridViewCallback != null ){
							tempView = gridViewCallback.getGridItemView(gridViewPosition, null, parent, rowView,obj);
						}
					}
					//添加view
					addItemView(tempView, -1, i>0, false);
				}

				if( existViewCount > itemCount){
					//隐藏多余的view
					for(int j = itemCount ; j < existViewCount ;j ++){
						rowView.getChildAt(j).setVisibility(View.GONE);
					}
				}
			}
		}

		/**
		 * 为rowView添加子view
		 * @param childView
		 * 		子view
		 * @param index
		 * 		子view下标，当小于0时，加在最后
		 * @param haveLeftMargin
		 * 		是否需要添加左测margin
		 * @param removeExist
		 * 		是否需要移除已存在的view
		 */
		private void addItemView(View childView, int index, boolean haveLeftMargin, boolean removeExist){
			LayoutParams lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
			lp.weight = 1 ;
			if( haveLeftMargin && hSpacing > 0 ){
				//添加左测padding
				lp.leftMargin = hSpacing ;
			}
			if( index >= 0 ){
				if( removeExist && rowView.getChildCount() > index){
					rowView.removeViewAt(index);
				}
				rowView.addView(childView, index, lp);
			}else {
				rowView.addView(childView, lp);
			}
		}
	}

	/**
	 * 创建gridview itemview的回调接口
	 * @author Cuckoo
	 *
	 */
	public interface IGridViewCallback{
		/**
		 * 创建gridview的item
		 * @param position
		 * @param convertView
		 * @param parent
		 * @param rowLayout
		 * @return
		 */
		 View getGridItemView(int position, View convertView, ViewGroup parent, LinearLayout rowLayout, Object item);
	}

	/**
	 * 空View，当最后一行不满时，用空View占位
	 * @author Cuckoo
	 *
	 */
	class EmptyView {
		
	}
	
}
