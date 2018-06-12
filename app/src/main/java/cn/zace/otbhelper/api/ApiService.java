package cn.zace.otbhelper.api;


import cn.zace.otbhelper.response.DepthResponse;
import cn.zace.otbhelper.response.TickersResponse;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    /**
     * 获取市场信息，包含当前买单卖单的最低价以及数量
     *
     * @return
     */
    @GET("api/v2/depth")
    Observable<DepthResponse> depth(@Query("market") String market,
                                    @Query("limit") String limit);




    /**
     * 获取市场信息，包含当前买单卖单的最低价以及数量
     *
     * @return
     */
    @GET("api/v2/tickers")
    Observable<TickersResponse> tickers();


//    /**
//     * 登录
//     *
//     * @return
//     */
//    @GET("login/app")
//    Observable<BaseResponse2<User>> login(@Query("userInfo") String userinfo,
//                                          @Query("password") String password);
//
//
//    /**
//     * 供电所台区查询
//     *
//     * @param userinfo
//     * @return
//     */
//    @POST("app/gdsAndTq/list")
//    Observable<BaseResponse<PowerSupply>> getGdsTq(@Query("userInfo") String userinfo);
//
//
//    /**
//     * 表箱查询
//     *
//     * @param userinfo
//     * @return
//     */
//    @POST("app/bx/list")
//    Observable<BaseResponse2<Box>> getBox(@Query("userInfo") String userinfo,
//                                          @Query("bxCode") String bxCode);
//
//
//    /**
//     * 表箱提交
//     *
//     * @param account
//     * @param gdsId    供电所ID
//     * @param tqId     台区ID
//     * @param bxId     表箱ID 第一次传空
//     * @param bxCode   表箱Code
//     * @param type     类型
//     * @param bxVolume 表位数
//     * @return
//     */
//    @POST("app/bx/save")
//    Observable<BaseResponse2> saveBox(@Query("account") String account,
//                                      @Query("gdsId") String gdsId,
//                                      @Query("tqId") String tqId,
//                                      @Query("bxId") String bxId,
//                                      @Query("bxCode") String bxCode,
//                                      @Query("type") String type,
//                                      @Query("bxVolume") String bxVolume);
//
//
//    /**
//     * 电表保存
//     *
//     * @param account
//     * @param bxCode  表箱Code
//     * @param dbCode  电表code
//     * @return
//     */
//    @POST("app/db/save")
//    Observable<BaseResponse2> saveEMeter(@Query("account") String account,
//                                         @Query("bxCode") String bxCode,
//                                         @Query("dbCode") String dbCode);
//
//
//    /**
//     * 修改密码
//     *
//     * @return
//     */
//    @POST("user/pwd/reset")
//    Observable<BaseResponse2> updatePwd(@Query("account") String account,
//                                        @Query("oldPwd") String oldPwd,
//                                        @Query("newPwd") String newPwd);
}
