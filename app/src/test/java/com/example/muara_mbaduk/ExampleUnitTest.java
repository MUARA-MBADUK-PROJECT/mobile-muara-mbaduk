package com.example.muara_mbaduk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void findIndexInWord(){
        String kata = "dengan melanjutkan tahap ini, anda setuju dengan syarat penggunaan adn kebijakan privasi kami";
        String word = "anda";
        int index = kata.indexOf(word);
        int endIndex = index + word.length();
        assertEquals(word , kata.substring(index , endIndex));
    }
}