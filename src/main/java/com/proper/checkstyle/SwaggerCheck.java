package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SwaggerCheck extends AbstractCheck {
    private static final String SWAGGER_ANNOTATION = "@ApiOperation";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF, TokenTypes.ANNOTATION, TokenTypes.IDENT};
    }

    @Override
    public void visitToken(DetailAST ast) {
        // do sth.
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
