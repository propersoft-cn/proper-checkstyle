package com.proper.checkstyle;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

public class PassController {

    @PutMapping
    @ApiOperation("blah blah")
    public String update(String id) {
        return "";
    }

    @GetMapping
    @ApiOperation("测试‍")
    public String get(String ids) {
        return "";
    }

    @ApiOperation("wa‍")
    @DeleteMapping
    public String delete(String id) {
        return "";
    }

    @PostMapping
    @ApiOperation("post")
    public String post() {
        return "";
    }

    @ApiOperation("request")
    @RequestMapping
    public String request() {
        return "";
    }

    private void priMethod1() {
        return;
    }

    private void priMethod2() {
        return;
    }

}
