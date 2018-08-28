package com.evayinfo.xposedtt.net;

import java.util.List;

public class TranslateResult {

    private String from;
    private String to;
    private List<TransResultEntity> trans_result;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<TransResultEntity> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<TransResultEntity> trans_result) {
        this.trans_result = trans_result;
    }

    public static class TransResultEntity {
        /**
         * src : apple
         * dst : 苹果
         */

        private String src;
        private String dst;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }
    }
}
