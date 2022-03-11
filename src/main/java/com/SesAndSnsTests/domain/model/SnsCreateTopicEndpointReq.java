package com.SesAndSnsTests.domain.model;

public class SnsCreateTopicEndpointReq {
    private String topicName;
    private String[] topicEndpoints;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String[] getTopicEndpoints() {
        return topicEndpoints;
    }

    public void setTopicEndpoints(String[] topicEndpoints) {
        this.topicEndpoints = topicEndpoints;
    }

    public SnsCreateTopicEndpointReq(String topicName, String[] topicEndpoints) {
        this.topicName = topicName;
        this.topicEndpoints = topicEndpoints;
    }

    public SnsCreateTopicEndpointReq() {
    }
}
