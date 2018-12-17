package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseCheckTest {

    protected Checker prepareCheckStyleChecker(Class clz, Map<String, String> attrs) throws CheckstyleException {
        Checker checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());
        checker.configure(prepareConfiguration(clz, attrs));
        return checker;
    }

    private DefaultConfiguration prepareConfiguration(Class clz, Map<String, String> attrs) {
        DefaultConfiguration checks = new DefaultConfiguration("Checks");
        DefaultConfiguration treeWalker = new DefaultConfiguration("TreeWalker");
        DefaultConfiguration checker = new DefaultConfiguration(clz.getCanonicalName());
        for (Map.Entry<String, String> entry : attrs.entrySet()) {
            checker.addAttribute(entry.getKey(), entry.getValue());
        }
        checks.addChild(treeWalker);
        treeWalker.addChild(checker);
        return checks;
    }

    protected List<File> prepareFilesToBeChecked() {
        List<File> files = new ArrayList<>();
        files.add(getFile("PassController.java"));
        files.add(getFile("NotPassController.java"));
        files.add(getFile("BeFiltered.java"));
        return files;
    }

    private File getFile(String name) {
        URL testFileUrl = BaseCheckTest.class.getResource(name);
        return new File(testFileUrl.getFile());
    }
}
