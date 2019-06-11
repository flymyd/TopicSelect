package com.tjrac.topicselect;

import com.alibaba.fastjson.JSON;
import com.tjrac.topicselect.util.PoiReadUtils;
import org.junit.Test;

import java.io.File;

public class PoiReadUtilsTest {
    public static void main(String[] args){
        PoiReadUtils poiReadUtils=new PoiReadUtils();
        System.out.println(JSON.toJSON(poiReadUtils.readExcel(new File("/Users/myd/Desktop/xh.xls"))));
    }
}
