package club.guadazi.babygallery.provider.entity;

/**
 * Created by aaron on 16-1-8.
 */
public class MissionEntity {
    public MISSION_TYPE missionType;
    public String missionValue;



    public MissionEntity(MISSION_TYPE missionType, String missionValue) {
        this.missionType = missionType;
        this.missionValue = missionValue;
    }

    public MISSION_TYPE getMissionType() {
        return missionType;
    }

    public String getMissionValue() {
        return missionValue;
    }
}
