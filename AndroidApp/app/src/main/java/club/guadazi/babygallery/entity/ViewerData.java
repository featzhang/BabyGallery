package club.guadazi.babygallery.entity;

public class ViewerData {
    private int id;
    private String name;
    private String inviteCode;
    private Integer isBlacklist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Integer getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(Integer isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewerData that = (ViewerData) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (inviteCode != null ? !inviteCode.equals(that.inviteCode) : that.inviteCode != null) return false;
        if (isBlacklist != null ? !isBlacklist.equals(that.isBlacklist) : that.isBlacklist != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (inviteCode != null ? inviteCode.hashCode() : 0);
        result = 31 * result + (isBlacklist != null ? isBlacklist.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViewerData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", isBlacklist=" + isBlacklist +
                '}';
    }
}
