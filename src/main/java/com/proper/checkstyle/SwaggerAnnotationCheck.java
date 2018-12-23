package com.proper.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.AnnotationUtil;

/**
 * 校验以Controller.java 结尾的文件中是否包含swagger的注解
 * 仅检查通过注解方式配置的 controller，不检查 xml 方式配置的 controller
 */
public class SwaggerAnnotationCheck extends AbstractCheck {

    public String anno = "ApiOperation";

    public String restController = "RestController";

    public String controller = "Controller";

    public String api = "Api";

    public String mapping = "Mapping";

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (classContains(ast)) {
            DetailAST method = ast.findFirstToken(TokenTypes.OBJBLOCK).findFirstToken(TokenTypes.METHOD_DEF);
            annotation(method);
        }
    }

    private boolean classContains(DetailAST ast) {
        if (AnnotationUtil.containsAnnotation(ast, restController) || AnnotationUtil.containsAnnotation(ast, controller)) {
            if (AnnotationUtil.containsAnnotation(ast, api)) {
                return true;
            }
        } else {
            return false;
        }
        return true;
    }

    private void annotation(DetailAST ast) {
        if (ast.branchContains(TokenTypes.ANNOTATION)) {
            for (DetailAST child = AnnotationUtil.getAnnotationHolder(ast).getFirstChild(); child != null; child = child.getNextSibling()) {
                //if (child.getType() == TokenTypes.ANNOTATION) {
                final DetailAST detailAST = child.getFirstChild();
                final String name = FullIdent.createFullIdent(detailAST.getNextSibling()).getText();
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
