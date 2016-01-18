package club.guadazi.babygallery.provider.backmission;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台任务管理<br/>
 * 主要的耗时后台任务，均记录下来，防止重复请求和请求为完成时提前获取数据
 */
public class BackMissionManager {
    /**
     * 是否需要
     *
     * @param remoteImageId
     * @return
     */
    public static MissionState requestImage(int remoteImageId) {
        MissionState state = getOrUpdateState(remoteImageId, null);
        return state;
    }

    public static void setImageRequestState(int remoteImageId, MissionState working) {
        getOrUpdateState(remoteImageId, working);
    }

    enum MissionState {
        NOT_BEGIN, WORKING, END
    }

    private static Map<String, MissionState> map = new HashMap<String, MissionState>();

    /**
     * 获取或更新后台任务状态
     *
     * @param imageId
     * @param missionState
     * @return
     */
    private static MissionState getOrUpdateState(int imageId, MissionState missionState) {
        String key = "ir_" + imageId;
        synchronized (map) {

            MissionState value = map.get(key);
            if (missionState == null) {
                if (value == null) {
                    return MissionState.NOT_BEGIN;
                } else {
                    return value;
                }
            }

            map.put(key, missionState);
            return missionState;
        }
    }
}
