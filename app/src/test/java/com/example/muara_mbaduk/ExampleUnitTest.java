package com.example.muara_mbaduk;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.muara_mbaduk.model.entity.ProductsModel;

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


    @Test
    public void testApplicationProperties(){
        ProductsModel productsModel = new ProductsModel();
    }

    @Test
    public void testGenerateMessageWhatshap(){

        //String generateMessage = UtilMethod.generateMessage(List.of("tenda \n" , "kompor \n" , "jajan \n"),"12 februari 2023","zam","may 14 2023","mohammad@gmail.com","Rp.12000","oke");
       // System.out.println(generateMessage);

        Float a = 2.5f;

        int round = Math.round(a);
        System.out.println(round);

    }
}