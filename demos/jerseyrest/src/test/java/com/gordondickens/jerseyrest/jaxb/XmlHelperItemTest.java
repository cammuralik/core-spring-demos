package com.gordondickens.jerseyrest.jaxb;

import com.gordondickens.jerseyrest.domain.Item;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author gordon
 *         Date: 2012-08-03
 */
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class XmlHelperItemTest {
    private static final Logger logger = LoggerFactory.getLogger(XmlHelperItemTest.class);

    @Autowired
    XmlHelper xmlHelper;


    @Test
    public void CheckXmlHelperWithItem() {
        final ClassPathResource resourceIn = new ClassPathResource("/data/item-in.xml");
        assertThat("/data/item-in.xml resource MUST Exist",
                resourceIn, is(not(nullValue())));

        Item item = new Item();

        String fullPath = "";

        //TODO - Capture the directory and create the output file there.
        try {
            fullPath = resourceIn.getURL().toExternalForm();
            logger.debug("URL {}", fullPath);
            JAXBContext context = JAXBContext.newInstance(item.getClass().getPackage().getName());

            // load item
            File inputFile = resourceIn.getFile();

            assertThat("Input File MUST be valid",
                    inputFile, is(not(nullValue())));

            item = (Item) xmlHelper.load(new StreamSource(new FileInputStream(inputFile)));
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        assertThat("Item Must Exist",
                item, is(not(nullValue())));

        logger.debug("The item '{}' was created", item.toString());

        assertThat("Item Name MUST Match",
                item.getName(), is(equalTo("Jane Doe")));

        assertThat("Item Description MUST Match",
                item.getDescription(), is(equalTo("I got your description")));


        xmlHelper.marshalToStandardOut(item);

        try {
            fullPath = StringUtils.remove(fullPath, "file:");
            logger.debug("Full Path '{}'", fullPath);

            File outputFile = new File(fullPath + ".result.xml");
            logger.debug("Writing output file {}", outputFile.getAbsolutePath());

            assertThat("Output File MUST be valid",
                    outputFile, is(not(nullValue())));

            xmlHelper.save(item, new StreamResult(outputFile));

            Long length = outputFile.length();

            assertThat("File Length MUST be greater than zero",
                    length, is(greaterThan(0L)));

            logger.debug("File '{}' has a length '{}'", outputFile, length);

        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }
}
