package com.android.wool.view.constom;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.android.wool.MyApplication;
import com.android.wool.R;
import com.android.wool.model.db.DBCityManager;
import com.android.wool.model.entity.CityInfoEntity;
import java.util.ArrayList;
import java.util.List;

/**  城市Picker  */
public class CityPicker extends LinearLayout {
	private List<CityInfoEntity> citys = new ArrayList<>();
	public void setCitys(List<CityInfoEntity> citys) {
		this.citys = citys;
	}
	/** 滑动控件 */
	private ScrollerNumberPicker provincePicker;
	private ScrollerNumberPicker cityPicker;
	private ScrollerNumberPicker counyPicker;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时索引 主要解决第一次重复触发的问题 */
	// 如:第一个选择了县,并且改变了值, 这时如果在选择市,即使不改变值,只要触发,县就会初始化,此处就是解决这个问题的
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	/** 上下文 */
	private Context context;
	/** 省的集合 */
	private List<CityInfoEntity> province_list = new ArrayList<CityInfoEntity>();
	/** 市的集合 */
	private List<CityInfoEntity> city_list = new ArrayList<CityInfoEntity>();
	/** 县的集合 */
	private List<CityInfoEntity> couny_list = new ArrayList<CityInfoEntity>();
	/** 数据初始化的对象 */
	private CityParser parser;
	/** 当前选中的省、市、县、及县代码的索引 */
	private String province_name ;
	private String province_id ;
	private String city_name ;
	private String city_id ;
	private String couny_name ;
	private String city_code ;
	private String city_string;
	public static boolean isFirst ;

	public CityPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		getaddressinfo();
	}

	public CityPicker(Context context) {
		super(context);
		this.context = context;
		getaddressinfo();
	}

	/** 获取城市信息 */
	private void getaddressinfo() {
		// 读取文件里的城市信息
		parser = new CityParser();
		if(isFirst)
			parser.setIsFirst(true);
		province_list = parser.getDepartments();
		province_name = province_list.get(0).getRegion_name();

		city_list = parser.getCityList(province_list.get(0).getId());
		city_name = city_list.get(0).getRegion_name();

		couny_list = parser.getCouny(city_list.get(0).getId());
		couny_name = couny_list.get(0).getRegion_name();
		city_code = couny_list.get(0).getId();
	}

	public String getDefaultStr(){
		StringBuffer sb = new StringBuffer();
		sb.append(province_name+" "+city_name+" "+couny_name);
		return sb.toString();
	}
	public String getDefaultCode(){
		return city_code;
	}

	/**  进行map、 list的初始化  */
	public static class CityParser {
		private List<CityInfoEntity> mlist = new ArrayList<>();
		private List<CityInfoEntity> province_list;
		private List<CityInfoEntity> city_list;
		private List<CityInfoEntity> couny_list;
		private DBCityManager dbCityManager;
		private boolean isFirst;
		public void setIsFirst(boolean isFirst) {
			this.isFirst = isFirst;
		}
		public CityParser(){
			if(MyApplication.cityList != null) {
				mlist.addAll(MyApplication.cityList);
			}
		}
		public List<CityInfoEntity> getProvinceList(){
			return province_list;
		}
		public List<CityInfoEntity> getCityList(){
			return city_list;
		}
		public List<CityInfoEntity> getCounyList(){
			return couny_list;
		}
		/**  获取省的集合  */
		public List<CityInfoEntity> getDepartments() {
			if(mlist == null || mlist.isEmpty()){
				dbCityManager = new DBCityManager();
				this.province_list = dbCityManager.selectByType("0");
				dbCityManager.closeDB();
			}else {
				this.province_list = mlist;
			}
			return this.province_list;
		}

		/** 获取市的集合 */
		public List<CityInfoEntity> getCityList(String cityCode) {
			if(mlist == null || mlist.isEmpty()){
				dbCityManager = new DBCityManager();
				city_list = dbCityManager.selectCityByPCode(cityCode);
				dbCityManager.closeDB();
			}else {
				for (int i=0;i<mlist.size();i++){
					if(cityCode.equals(mlist.get(i).getId())){
						city_list = mlist.get(i).getChild();
						break;
					}
				}
			}
			return city_list;
		}

		/** 获取县的集合 */
		public List<CityInfoEntity> getCouny(String cityCode) {
			if(mlist == null || mlist.isEmpty()){
				dbCityManager = new DBCityManager();
				couny_list = dbCityManager.selectCityByPCode(cityCode);
				dbCityManager.closeDB();
			}else {
				for (int i=0;i<getCityList().size();i++){
					if(cityCode.equals(getCityList().get(i).getId())){
						couny_list = getCityList().get(i).getChild();
						break;
					}
				}
			}
			return couny_list;
		}

		/** 转换ArrayList < Cityinfo >为ArrayList < String >
		 *   ArrayList < Cityinfo >
		 * @return ArrayList < String >
		 */
		public ArrayList<String> toArrayList(ArrayList<CityInfoEntity> oldlist) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < oldlist.size(); i++) {
				list.add(oldlist.get(i).getRegion_name());
			}
			return list;
		}
	}

	private void setData(ScrollerNumberPicker scroller,List<CityInfoEntity> citys){
		scroller.setData(citys);
		scroller.setDefault(0);
	}


	/**  加载完xml文件的时候      */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);
		// 获取控件引用
		provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);
		cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);
		counyPicker = (ScrollerNumberPicker) findViewById(R.id.couny);

		setData(provincePicker,province_list);
		setData(cityPicker,city_list);
		setData(counyPicker,couny_list);

		province_name = province_list.get(0).getRegion_name();
		province_id = province_list.get(0).getId();
		city_name = city_list.get(0).getRegion_name();
		city_id = city_list.get(0).getId();
		couny_name = couny_list.get(0).getRegion_name();
		city_code = couny_list.get(0).getId();

		Message message = new Message();
		message.what = REFRESH_VIEW;
		handler.sendMessage(message);

		// 设置省控件的监听器
		provincePicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null) {
					return;
				}
				if (tempProvinceIndex != id) {
					String selectDay = cityPicker.getSelectedText();
					if (selectDay == null || selectDay.equals("")) {
						return;
					}
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals("")) {
						return;
					}
					if (temCityIndex < 0) {
						temCityIndex = 0;
					}
					if (tempCounyIndex < 0) {
						tempCounyIndex = 0;
					}

					// 更改省的名称
					province_name = text;
					province_id = id+"";
					// 设置市的数据内容
					List<CityInfoEntity> m = parser.getCityList(parser.getProvinceList().get(id).getId());
//					cityPicker.setData(m);
//					cityPicker.setDefault(0);
					setData(cityPicker,m);
					city_name = cityPicker.getSelectedText();
					// 设置县的数据内容
//					counyPicker.setData(parser.getCouny(m.get(0).getCityCode()));
//					counyPicker.setDefault(0);
					setData(counyPicker,parser.getCouny(m.get(0).getId()));
					couny_name = counyPicker.getSelectedText();
					city_code = parser.getCounyList().get(0).getId();
				}
				tempProvinceIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}
			@Override
			public void selecting(int id, String text) {
			}
		});


		// 设置市控件的监听器
		cityPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null) {
					return;
				}
				if (temCityIndex != id) {
					String selectDay = provincePicker.getSelectedText();
					if (selectDay == null || selectDay.equals("")) {
						return;
					}
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals("")) {
						return;
					}
					if (tempCounyIndex < 0) {
						tempCounyIndex = 0;
					}
					if (tempProvinceIndex < 0) {
						tempProvinceIndex = 0;
					}
					city_name = text;
					city_id = id+"";
					// 设置县的数据内容
					List<CityInfoEntity> couny = parser.getCouny(parser.getCityList().get(id).getId());
//					counyPicker.setData(couny);
//					counyPicker.setDefault(0);
					setData(counyPicker,couny);
					couny_name = counyPicker.getSelectedText();
					city_code = parser.getCounyList().get(0).getId();
				}
				temCityIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {

			}
		});


		// 设置县控件的监听器
		counyPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {
			@Override
			public void endSelect(int id, String text) {

				if (text.equals("") || text == null) {
					return;
				}
				if (tempCounyIndex != id) {
					String selectDay = provincePicker.getSelectedText();
					if (selectDay == null || selectDay.equals("")) {
						return;
					}
					String selectMonth = cityPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals("")) {
						return;
					}
					if (temCityIndex < 0) {
						temCityIndex = 0;
					}
					if (tempProvinceIndex < 0) {
						tempProvinceIndex = 0;
					}
					// 改变县的名称
					couny_name = text;
					city_code = parser.getCounyList().get(id).getId()+"";
				}
				tempCounyIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}
			@Override
			public void selecting(int id, String text) {

			}
		});
	}


	// 这是用来更新界面，和绑定监听器值的
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case REFRESH_VIEW:
					if (onSelectingListener != null)
						onSelectingListener.selected(true,
								province_name,province_id,
								city_name, city_id,
								couny_name, city_code);
					break;
				default:
					break;
			}
		}
	};


	/**
	 * 绑定监听器
	 *
	 * @param onSelectingListener
	 *            控件的监听器接口
	 */
	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
		this.onSelectingListener = onSelectingListener;
	}

	/**
	 * 得到所选择的省的名称
	 *
	 * @return 省的名称
	 */
	public String getprovince_name() {
		return province_name;
	}

	/**
	 * 得到所选择的市的名称
	 *
	 * @return 市的名称
	 */
	public String getcity_name() {
		return city_name;
	}

	/**
	 * 得到所选择的县的名称
	 *
	 * @return 省县的名称
	 */
	public String getcouny_name() {
		return couny_name;
	}

	/**
	 * 得到所选择的城市的的天气查询代码
	 *
	 * @return 城市的的天气查询代码
	 */
	public String getCity_code() {
		return city_code;
	}

	/**
	 * 得到所选择的省--市--县
	 *
	 * @return省--市--县
	 */
	public String getCity_string() {
		city_string = provincePicker.getSelectedText() + "--"
				+ cityPicker.getSelectedText() + "--"
				+ counyPicker.getSelectedText();
		return city_string;
	}

	/**
	 * 监听器接口
	 *
	 * @author LOVE
	 *
	 */
	public interface OnSelectingListener {

		/**
		 * @param selected
		 *            是否选择该控件？？？
		 * @param province_name
		 *            省的名称
		 * @param city_name
		 *            市的名称
		 * @param couny_name
		 *            县的名称
		 * @param city_code
		 *            城市天气代码
		 */
		public void selected(boolean selected,
							 String province_name,String province_id,
                             String city_name, String city_id,
							 String couny_name, String city_code);
	}
}
