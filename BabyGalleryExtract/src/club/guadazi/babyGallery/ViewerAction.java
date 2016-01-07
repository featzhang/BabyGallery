package club.guadazi.babyGallery;

import club.guadazi.babyGalleryEJB.ifc.ViewerData;
import club.guadazi.babyGalleryEJB.ifc.ViewerIfc;
import club.guadazi.common.EjbUtils;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class ViewerAction extends ActionSupport {
    private Log log = LogFactory.getLog(ViewerAction.class);
    private int viewerId;
    private int userId = -1;
    private String name;
    private String viewerDataString;
    private ViewerData viewerData;
    private ViewerIfc viewerIfc = (ViewerIfc) EjbUtils.getEjb(ViewerIfc.class);
    private List<ViewerData> viewerDatas;

    public ViewerData getViewerData() {
        return viewerData;
    }

    public void setViewDataString(String viewDataString) {
        viewerDataString = viewDataString;
    }

    public String addNewViewer() {
        if (viewerDataString != null && StringUtils.isNotEmpty(viewerDataString)) {
            viewerData = JSON.parseObject(viewerDataString, ViewerData.class);
            viewerId = viewerIfc.add(viewerData);
        }
        return SUCCESS;
    }

    public String listViewers() {
        if (userId != -1) {
            viewerDatas = viewerIfc.listViewerOfUser(userId);
        }
        return SUCCESS;
    }

    public String updateViewer() {
        if (viewerDataString != null && StringUtils.isNotEmpty(viewerDataString)) {
            viewerData = JSON.parseObject(viewerDataString, ViewerData.class);
            viewerData = viewerIfc.update(viewerData);
        }
        return SUCCESS;
    }
}
