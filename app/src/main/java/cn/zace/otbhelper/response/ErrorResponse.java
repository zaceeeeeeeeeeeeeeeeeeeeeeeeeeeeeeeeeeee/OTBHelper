package cn.zace.otbhelper.response;

/**
 * Created by zace on 2018/6/12.
 */
public class ErrorResponse {

    /**
     * error : {"code":1001,"message":"market does not have a valid value"}
     */

    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        /**
         * code : 1001
         * message : market does not have a valid value
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
