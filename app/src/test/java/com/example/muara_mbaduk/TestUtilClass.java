package com.example.muara_mbaduk;

import com.example.muara_mbaduk.utils.UtilMethod;

import org.junit.Assert;
import org.junit.Test;

public class TestUtilClass {

    @Test
    public void getIndexTest(){
        String kalimat = "mohammad tajut zamzami";
        String keyword = "tajut";
        int lastIndex = UtilMethod.getLastIndex(kalimat, keyword);
        int startIndex = UtilMethod.getStartIndex(kalimat, keyword);
        String result = kalimat.substring(startIndex , lastIndex);
        Assert.assertEquals(result , keyword);
    }
}
