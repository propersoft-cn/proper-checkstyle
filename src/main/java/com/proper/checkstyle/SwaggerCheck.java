package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.api.*;
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
        String str = getFileContents().getFileName();
        if (str.endsWith(filter)) {
            DetailAST child = detail(ast);
            final DetailAST detailAST = child.getFirstChild();
            final String name = FullIdent.createFullIdent(detailAST.getNextSibling()).getText();
            typeValue(name, ast);
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

    private DetailAST detail(DetailAST ast) {
        DetailAST holder = AnnotationUtil.getAnnotationHolder(ast);
        for (DetailAST child = holder.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                return child;
            }
        }
        return holder;
    }

    private void typeValue(String name, DetailAST ast) {
        if (name.endsWith(mapping)) {
            if (AnnotationUtil.containsAnnotation(ast, anno)) {
                return;
            } else {
                String message = "Failed！The methods no have swagger annotation [" + ast.getText() + "]";
                log(ast.getLineNo(), message);
            }
        }
    }

}
