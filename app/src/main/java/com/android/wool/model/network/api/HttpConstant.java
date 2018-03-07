package com.android.wool.model.network.api;
/**
 * Created by AiMr on 2017/9/21
 */
public class HttpConstant {
//    public static final String URL = "http://www.posjishu.cn/";
//    public static final String URL = "http://47.100.96.69/";
    public static final String URL = "http://www.jinxucar.com/";
//    public static final String URL = "http://www.hzcbys.com/";

    public static final String BASE_URL = URL+"Api/";
    //接口返回消息字段·
    public static final String API_ERROR_RESULT = "result";
    //接口验证码字段
    public static final String API_ERROR_CODE = "code";
    //code=0 接口调用正确
    public static final String API_CODE_OK = "200";
    //当前页
    public static final String CURRENT_PAGE = "current_page";
    //总页数
    public static final String COUNT_PAGE = "all_page";
    //登陆
    public static final String LOGIN = "Login/login";
    //发送验证码
    public static final String SEND_MSG = "Login/send_code";
    //注册
    public static final String REGISTER = "Login/register";
    //找回密码
    public static final String RETRIEVE_PWD = "Login/back_pwd_first";
    //设置密码
    public static final String RESET_PWD = "Login/back_pwd_second";
    //获取地址信息列表
    public static final String LOCATION = "User/get_address";
    //收货地址添加、修改
    public static final String LOCATION_UPDATE = "User/deal_address";
    //收藏列表
    public static final String MINE_HOUSE = "User/collection";
    //收藏商品删除，添加
    public static final String MINE_GOODS_ADD_DEL = "User/deal_collection";
    //个人中心
    public static final String MINE_PERSONAL = "User/personal_center";
    //获取省市区
    public static final String ADDRESS_LIST = "User/region_list";
    //首页
    public static final String HOME_LIST = "Index/index";
    //商品分类请求地址
    public static final String HOME_TYPE_GOODS = "Index/post_goods_cats";
    //删除地址信息
    public static final String DELETE_LOCATION = "User/del_address";
    //修改用户信息
    public static final String UPDATE_MINE = "User/deal_user_info";
    //购物车列表
    public static final String CART_LIST = "Cart/post_cart_list";
    //头像上传
    public static final String HEAD_IMG = "User/avatar_post";
    //订单列表
    public static final String ORDER_LIST = "Order/order_list";
    //订单详情
    public static final String ORDER_DETAIL = "Order/order_details";
    //商品详情
    public static final String GOODS_INFO = "Goods/index";
    //热搜关键字
    public static final String SEARCH_HOT_SEARCH = "search/hot_search";
    //添加删除收藏
    public static final String HOUSE_GOODS = "User/deal_collection";
    //加入购物车
    public static final String JOIN_CART = "Cart/join_cart";
    //删除购物车
    public static final String DELETE_CART = "Cart/post_del_cart";
    //购物车选中
    public static final String CART_SELECT = "Cart/post_cart_checked";
    //购物车数量修改
    public static final String CART_UPDATE_NUMBER = "Cart/post_change_cart";
    //处理订单（取消，删除，收货）
    public static final String ORDER_UPDATE = "Order/deal_order";
    //搜索商品列表
    public static final String SEARCH_GOODS = "Search/search_goods";
    //分类商品列表
    public static final String TYPE_GOODS_LIST = "Cat/post_cat_goods";
    //购物车去结算
    public static final String GO_TO_BALANCE = "Order/order_confrim";
    //提交订单
    public static final String ORDER_SUBMIT = "Order/order_submit";
    //支付方式
    public static final String PAY_TYPE = "pay/cashier";
    //物流
    public static final String LOGISTICS_INFO = "Order/get_express";
    //专场
    public static final String SPECIAL_LIST = "Index/special";
    //再次购买
    public static final String ORDER_RESET = "Order/buy_again";
    //支付方式
    public static final String HTML_PAY = "order/pay";
    //商品规格选择
    public static final String GOODS_SPEC = "Goods/get_goods_spec";
    //获取七牛云token
    public static final String QN_TOKEN = "QiNiu/token";
    //素材库
    public static final String HTML_SOURCE = "public/get_news_list";
    //每日更新，热销排行
    public static final String INDEX_ORDER = "Index/get_goods_list";
    //产品推荐
    public static final String INDEX_RECOMMEND = "Index/recommend_goods";

    //素材库分享
    public static final String HTML_SHARE_SOURCE = "Home/News/lists/id/";
}