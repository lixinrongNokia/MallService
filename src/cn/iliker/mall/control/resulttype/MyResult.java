package cn.iliker.mall.control.resulttype;

import com.opensymphony.xwork2.ActionInvocation;
import org.apache.struts2.result.StrutsResultSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyResult extends StrutsResultSupport {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MyResult() {
        super();
    }

    public MyResult(String location) {
        super(location);
    }

    protected void doExecute(String finalLocation, ActionInvocation invocation)
            throws Exception {
        HttpServletRequest request = (HttpServletRequest) invocation
                .getInvocationContext().get(HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) invocation
                .getInvocationContext().get(HTTP_RESPONSE);
        request.getRequestDispatcher(location).forward(request, response);
    }

}
