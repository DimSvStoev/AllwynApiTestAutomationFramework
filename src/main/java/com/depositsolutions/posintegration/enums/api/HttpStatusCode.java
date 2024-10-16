package com.depositsolutions.posintegration.enums.api;

    public enum HttpStatusCode {
        OK(200),
        BAD_REQUEST(400);
        private final int code;

        HttpStatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
