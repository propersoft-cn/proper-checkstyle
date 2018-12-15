package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MethodLimitCheckTest extends BaseCheckTest {

    private Checker checker;

    private List<File> files;

    @Test
    public void test() throws CheckstyleException {
        assertThat(checker.process(files), is(1));
    }

    @Before
    public void setUp() throws CheckstyleException {
        checker = prepareCheckStyleChecker(MethodLimitCheck.class, Collections.singletonMap("max", "10"));
        files = prepareFilesToBeChecked();
    }

}
