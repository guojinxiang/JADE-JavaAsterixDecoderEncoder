/*
* Created by dan-geabunea on 4/18/2016.
* This code is the property of JLG Consulting. Please
* check the license terms for this product to see under what
* conditions you can use or modify this source code.
*/

package jlg.jade.test.asterix;

import jlg.jade.common.AsterixDecodingException;
import jlg.jade.abstraction.AsterixItem;
import jlg.jade.asterix.AsterixItemLength;
import jlg.jade.cat062.AsterixCat062Item010;
import jlg.jade.common.Constants;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AsterixCat062Item010Test {

    private Logger logger;

    @Before
    public void init() {
        logger = LoggerFactory.getLogger(Constants.LOGGER_NAME);
    }

    @Test(expected = AsterixDecodingException.UnexpectedEndOfData.class)
    public void when_remaining_input_data_length_less_than_2_bytes_should_throw() {
        //arrange
        byte[] data = {1, 2, 3, 4};
        int currentIndex = 3;
        AsterixItem asterixItem = new AsterixCat062Item010();

        //act
        asterixItem.decode(data, currentIndex, data.length);
    }

    @Test
    public void should_populate_item_with_correct_values() {
        //arrange
        byte[] data = {(byte) 200, (byte) 244};
        int currentIndex = 0;
        AsterixCat062Item010 asterixItem = new AsterixCat062Item010();

        //act
        asterixItem.decode(data, currentIndex, data.length);

        //assert
        assertEquals("Sac not decoded correctly", 200, asterixItem.getSac());
        assertEquals("Sic not decoded correctly", 244, asterixItem.getSic());

    }

    @Test
    public void should_populate_debug_message_after_parsing_data() {
        //arrange
        byte[] data = {(byte) 200, (byte) 244};
        int currentIndex = 0;
        AsterixCat062Item010 asterixItem = new AsterixCat062Item010();

        //act
        asterixItem.decode(data, currentIndex, data.length);

        //assert
        logger.debug(asterixItem.getDebugString());
        assertNotNull(asterixItem.getDebugString());
    }

    @Test
    public void should_increase_current_index_after_parsing_the_data() {
        //arrange
        byte[] data = {(byte) 200, (byte) 244};
        int currentIndex = 0;
        AsterixCat062Item010 asterixItem = new AsterixCat062Item010();

        //act
        int result = asterixItem.decode(data, currentIndex, data.length);

        //assert
        int expectedCurrentIndex = currentIndex + AsterixItemLength.TWO_BYTES.getValue();
        assertEquals("Current index not incremented correctly", expectedCurrentIndex, result);
    }
}
