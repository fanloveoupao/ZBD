package com.tgnet.cachebean;

import com.tgnet.repositories.ClientRepositories;
import com.tgnet.repositories.ISingleRepository;
import com.tgnet.repositories.IWriteableSingleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fan-gk on 2017/6/3.
 */

public class SharedPreferenceSettings {
    public static String MAP_LOCATION = "map_location"; //定位
    public static String READED_FOOTERPRINT_ID = "read_footer_print_id";

    private <T> ISingleRepository<T> getSingleRepository(String key, Class<T> classOfT) {
        return ClientRepositories
                .getInstance()
                .getSharedPreferencesRepositoryProvider()
                .GetSingleRepository("SharedPreferenceSettings_tg_information", key, classOfT);
    }

    private IWriteableSingleRepository getWriteableSingleRepository(String key) {
        return getSingleRepository(key, Object.class);
    }

    public LocationProvideBean getLocationProvide() {
        return getSingleRepository(MAP_LOCATION, LocationProvideBean.class).get();
    }

    public void saveLocationProvide(LocationProvideBean lastToken) {
        getWriteableSingleRepository(MAP_LOCATION).addOrReplace(lastToken);
    }

    public void saveHasReadFootPrintId(String footprintid) {
        List<String> footPrintList = getHasReadFootPrintList();
        if (footPrintList == null) {
            footPrintList = new ArrayList<>();
        }
        footPrintList.add(footprintid);
        getWriteableSingleRepository(READED_FOOTERPRINT_ID).addOrReplace(footPrintList);
    }

    public List<String> getHasReadFootPrintList() {
        return getSingleRepository(READED_FOOTERPRINT_ID, List.class).get();
    }
}
