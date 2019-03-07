package de.cherry.workbench.self.interpreter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cherry.workbench.self.interpreter.Interpreter;
import de.cherry.workbench.self.interpreter.dto.CallDTO;
import de.cherry.workbench.self.interpreter.dto.ExecutableDTO;
import de.cherry.workbench.self.interpreter.dto.TypeSaveObject;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class InterpreterRest {


  @PostMapping("/getCtClass")
  public HashMap<String, String> getStruckture(@RequestBody String className) throws ClassNotFoundException {
    Class<?> aClass = Class.forName(className);
    return Interpreter.getStruckture(aClass);
  }

  @GetMapping("/findCallabalsForClass")
  public List<ExecutableDTO> getCallabas(@RequestParam("className") String className) throws ClassNotFoundException {
    Class<?> aClass = Class.forName(className);
    TypeSaveObject typeSaveObject = new TypeSaveObject(aClass);
    List<java.lang.reflect.Executable> callabals = Interpreter.callabals(typeSaveObject);
    return ExecutableDTO.from(callabals);
  }

  @PostMapping("/call")
  public TypeSaveObject get(
      @RequestBody CallDTO call) throws Exception {
    TypeSaveObject typeSaveObject = TypeSaveObject.form(call.getClazz(), call.getObject());
    ArrayList<Object> parsedParams = new ArrayList<>();
    Class[] parsedParamClasses = new Class[call.getParams().length];
    ObjectMapper objectMapper = new ObjectMapper();
    String[] paramsClasses = call.getParamsClasses();
    String[] params = call.getParams();
    for (int i = 0; i < paramsClasses.length; i++) {
      Class<?> valueType = ClassUtils.getClass(paramsClasses[i]);
      parsedParamClasses[i] = valueType;
      parsedParams.add(objectMapper.readValue(params[i], valueType));
    }
    return Interpreter.call(call.getExecutable(), typeSaveObject, parsedParams, parsedParamClasses);
  }


  /*@PostMapping("/call")
  public String get(
      @RequestBody String call) throws Exception {
   return call;
  }*/

}
