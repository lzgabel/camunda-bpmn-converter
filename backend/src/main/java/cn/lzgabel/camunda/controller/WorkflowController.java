package cn.lzgabel.camunda.controller;


import cn.lzgabel.camunda.BpmnBuilder;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

/**
 * 〈功能简述〉<br>
 * 〈〉
 *
 * @author lizhi
 * @date 2021/8/21
 * @since 1.0.0
 */

@RestController
@RequestMapping
public class WorkflowController {


    @GetMapping("/ok")
    public String ok() {
       return "ok";
    }

    @PostMapping(value = "/deploy",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @CrossOrigin
    public byte[] deploy(@RequestBody DeployRequest request) {

        BpmnModelInstance modelInstance = BpmnBuilder.build(request.getJson());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Bpmn.writeModelToStream(outputStream, modelInstance);
        return outputStream.toByteArray();
    }

}
