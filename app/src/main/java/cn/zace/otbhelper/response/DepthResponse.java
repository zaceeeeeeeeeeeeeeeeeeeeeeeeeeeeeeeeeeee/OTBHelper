package cn.zace.otbhelper.response;

import java.util.List;

/**
 * Created by zace on 2018/6/12.
 */
public class DepthResponse {


    /**
     * timestamp : 1528766814
     * asks : [["32.33333333","27.62901706"],["32.22222222","36.83868943"]]
     * bids : [["31.00000041","184.02948412"],["31.0000004","200.0"]]
     */

    private int timestamp;
    private List<List<String>> asks;
    private List<List<String>> bids;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<List<String>> getAsks() {
        return asks;
    }

    public void setAsks(List<List<String>> asks) {
        this.asks = asks;
    }

    public List<List<String>> getBids() {
        return bids;
    }

    public void setBids(List<List<String>> bids) {
        this.bids = bids;
    }
}
