package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SwaggerCheckerTest {

    private Checker checker;

    private List<File> files;

    @Test
    public void test() throws CheckstyleException {
        assertThat(checker.process(files), is(6));
    }

    @Before
    public void setUp() throws CheckstyleException {
        checker = prepareCheckStyleChecker();
        files = prepareFilesToBeChecked();
    }

    private Checker prepareCheckStyleChecker() throws CheckstyleException {
        Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(prepareConfiguration());
        return checker;
    }

    private DefaultConfiguration prepareConfiguration() {
        DefaultConfiguration checks = new DefaultConfiguration("Checks");
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        DefaultConfiguration swaggerCheck = new DefaultConfiguration(SwaggerCheck.class.getCanonicalName());
        checks.addChild(treeWalker);
        treeWalker.addChild(swaggerCheck);
        return checks;
    }

    private List<File> prepareFilesToBeChecked() {
        String testFileName = "TestController.java";
        URL testFileUrl = getClass().getResource(testFileName);
        File testFile = new File(testFileUrl.getFile());
        List<File> files = new ArrayList<>();
        files.add(testFile);
        return files;
    }

}
