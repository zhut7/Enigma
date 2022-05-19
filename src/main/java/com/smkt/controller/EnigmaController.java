package com.smkt.controller;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * @author : zhouhao
 * @date : 2022-05-07 20:38
 **/
@RestController
@RequestMapping("/EnigmaController")
public class EnigmaController extends SuperController {

    @RequestMapping("/coding")
    public HashMap<String, Object> coding() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/smkt.py");
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile(resourceAsStream);
        //interpreter.execfile("D:\\Code\\Enigma\\Enigma-Machine\\smkt\\smkt.py");
        PyFunction pyFunction = interpreter.get("web_encoding", PyFunction.class);

        // 1为编码，0为解码
        String flag = super.GetParameterStr("flag");
        String rule = super.GetParameterStr("rule");
        String key = super.GetParameterStr("key");
        String darkText = super.GetParameterStr("darkText");
        PyObject pyobj;
        if (flag.equals("0")) {
            pyobj = pyFunction.__call__(new PyString(key), new PyString(rule), new PyInteger(0));
            map.put("rule",pyobj.__str__().toString());
        } else {
            pyobj = pyFunction.__call__(new PyString(key), new PyString(rule), new PyInteger(1));
            map.put("darkText",pyobj.__str__().toString());
        }
        return map;
    }
}
