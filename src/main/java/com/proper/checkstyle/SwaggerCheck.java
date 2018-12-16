package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

public class SwaggerCheck extends AbstractCheck {

    private String anno = Instance.SWAGGER_ANNOTATION;

    private String filter = Instance.FILTER;

    private String mapping = Instance.REQUEST_MAPPING;

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        FileContents fileContents = getFileContents();
        String str = fileContents.getFileName();
        if (str.endsWith(filter)) {
            DetailAST detailAST = AnnotationUtil.getAnnotationHolder(ast).getFirstChild();
            if (detailAST != null && detailAST.getFirstChild() != null && detailAST.getFirstChild().getNextSibling() != null) {
                if (detailAST.getFirstChild().getNextSibling().getText().endsWith(mapping)) {
                    if (AnnotationUtil.containsAnnotation(ast, anno)) {
                        return;
                    } else {
                        String message = "Failed！The methods no have swagger annotation [" + ast.getText() + "]";
                        log(ast.getLineNo(), message);
                    }
                }
            }
        }
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[0];
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

}
