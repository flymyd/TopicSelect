package com.tjrac.topicselect.bean.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @author myd
 */
@Data
public class GetTopicSelSelectedResultResp {
    public String[] titleList;
    public Hashtable<String, ArrayList> resultTable;
}
