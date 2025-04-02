package jbar.service_core.RatingUserSell.Model;

import java.util.Map;

public class WaiterRatingChartDTO {
    private Map<String, Integer> data;

    public WaiterRatingChartDTO(Map<String, Integer> data) {
        this.data = data;
    }

    public Map<String, Integer> getData() {
        return data;
    }

    public void setData(Map<String, Integer> data) {
        this.data = data;
    }
}
