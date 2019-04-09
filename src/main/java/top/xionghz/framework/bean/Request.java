package top.xionghz.framework.bean;
/**
 * 封装请求信息
 * @author bj
 * @since 1.0.0
 */
public class Request {
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 请求路径
     */
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Request)) return false;

        Request request = (Request) object;

        if (getRequestMethod() != null ? !getRequestMethod().equals(request.getRequestMethod()) : request.getRequestMethod() != null)
            return false;
        return getRequestPath() != null ? getRequestPath().equals(request.getRequestPath()) : request.getRequestPath() == null;
    }

    @Override
    public int hashCode() {
        int result = getRequestMethod() != null ? getRequestMethod().hashCode() : 0;
        result = 31 * result + (getRequestPath() != null ? getRequestPath().hashCode() : 0);
        return result;
    }
}
