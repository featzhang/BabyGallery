package club.guadazi.babygallery.provider.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

public class MissionManager {

    private static final String MISSION_PREF_NAME = "missions";
    private static final String MISSION_COUNT = "mission.count";
    private static final String MISSION_COUNT_INDEX = "mission.count.index";
    private static final String MISSION_IDS = "mission.ids";
    private static final String MISSION_ID_prefix = "mission_";
    private static final String MISSION_ID_separator = ":::";


    public static void addNewMission(Context context, MissionEntity missionEntity) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(MISSION_PREF_NAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int missionCount = sharedPreferences.getInt(MISSION_COUNT, 0);
        int missionIndexCount = sharedPreferences.getInt(MISSION_COUNT_INDEX, 0);
        String missionIds = sharedPreferences.getString(MISSION_IDS, "");
        editor.putInt(MISSION_COUNT, missionCount + 1);
        editor.putInt(MISSION_COUNT_INDEX, missionIndexCount + 1);
        int missionId = missionIndexCount + 1;
        Gson gson = new Gson();
        String missionIdString = MISSION_ID_prefix + missionId;
        editor.putString(missionIdString, gson.toJson(missionEntity));
        if (TextUtils.isEmpty(missionIds)) {
            missionIds = missionIdString;
        } else {
            missionIds += MISSION_ID_separator + missionIdString;
        }
        editor.putString(MISSION_IDS, missionIds);
        editor.commit();
    }
}
