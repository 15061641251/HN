package com.sbq.web.activiti;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.form.FormEngine;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.scripting.ScriptingEngines;

import java.io.*;

/**
 * 自定义显示JSP页面表单引擎，formkey中指定具体文件路径
 * 由于默认的表单必须和流程定义文件，一起打包，部署的时候，存数据库中，默认引擎会读取数据库中的字节，不方便随时更改表单样式等
 * 自定义的话，自己读取文件流进行呈现
 * Created by zhangyuan on 16/9/10.
 */
public class MyJSPFormEngine implements FormEngine {
    @Override
    public String getName() {
        return "myJSPForm";
    }

    @Override
    public Object renderStartForm(StartFormData startForm) {
        if (startForm.getFormKey() == null) {
            return null;
        }
        String formTemplateString = getFormTemplateString(startForm, startForm.getFormKey());
        ScriptingEngines scriptingEngines = Context.getProcessEngineConfiguration().getScriptingEngines();
        return scriptingEngines.evaluate(formTemplateString, ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE, null);
    }

    @Override
    public Object renderTaskForm(TaskFormData taskForm) {
        if (taskForm.getFormKey() == null) {
            return null;
        }
        String formTemplateString = getFormTemplateString(taskForm, taskForm.getFormKey());
        ScriptingEngines scriptingEngines = Context.getProcessEngineConfiguration().getScriptingEngines();
        TaskEntity task = (TaskEntity) taskForm.getTask();
        return scriptingEngines.evaluate(formTemplateString, ScriptingEngines.DEFAULT_SCRIPTING_LANGUAGE, task.getExecution());
    }

    protected String getFormTemplateString(FormData formInstance, String formKey) {

//        String deploymentId = formInstance.getDeploymentId();
//
//        ResourceEntity resourceStream = Context
//                .getCommandContext()
//                .getResourceEntityManager()
//                .findResourceByDeploymentIdAndResourceName(deploymentId, formKey);
//
//        if (resourceStream == null) {
//            throw new ActivitiObjectNotFoundException("Form with formKey '" + formKey + "' does not exist", String.class);
//        }
//
//        byte[] resourceBytes = resourceStream.getBytes();

        //流读取类路径下面的文件

        String filePath = this.getClass().getResource(formKey).getPath();
        byte[] resourceBytes = getBytes(filePath);

        String encoding = "UTF-8";
        String formTemplateString = "";
        try {
            formTemplateString = new String(resourceBytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ActivitiException("Unsupported encoding of :" + encoding, e);
        }
        return formTemplateString;
    }

    private byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
