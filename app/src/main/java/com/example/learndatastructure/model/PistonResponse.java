package com.example.learndatastructure.model;

public class PistonResponse {
    private Run run;

    public Run getRun() {
        return run;
    }

    public static class Run {
        private String stdout;
        private String stderr;
        private String output;
        private int code;

        public String getOutput() {
            return output;
        }

        public String getStdout() {
            return stdout;
        }

        public String getStderr() {
            return stderr;
        }

        public int getCode() {
            return code;
        }
    }
}
