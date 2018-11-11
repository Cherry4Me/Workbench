package de.cherry.workbench.interpreter;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.cherry.workbench.explorer.TypeSaveObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InterpreterRest {


  @GetMapping("/findCallabalsForClass")
  public List<ExecutableWO> getCallabas(@RequestParam("className") String className) throws ClassNotFoundException {
    Class<?> aClass = Class.forName(className);
    TypeSaveObject typeSaveObject = new TypeSaveObject(aClass);
    List<Executable> callabals = Interpreter.callabals(typeSaveObject);
    return ExecutableWO.from(callabals);
  }

  @PostMapping("/call")
  public TypeSaveObject get(
      @RequestBody CallDTO call) throws Exception {
    TypeSaveObject typeSaveObject = TypeSaveObject.form(call.getClazz(), call.getObject());
    ArrayList<Object> parsedParams = new ArrayList<>();
    ObjectMapper objectMapper = new ObjectMapper();
    String[] paramsClasses = call.getParamsClasses();
    String[] params = call.getParams();
    for (int i = 0; i < paramsClasses.length; i++) {
      parsedParams.add(objectMapper.readValue(params[i], Class.forName(paramsClasses[i])));
    }

    return Interpreter.call(call.getExecutable(), typeSaveObject, parsedParams);
  }


  /*@PostMapping("/call")
  public String get(
      @RequestBody String call) throws Exception {
   return call;
  }*/

}
