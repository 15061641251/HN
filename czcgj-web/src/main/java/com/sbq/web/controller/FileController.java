package com.sbq.web.controller;

import com.sbq.annotation.RequestLog;
import com.sbq.common.Constants;
import com.sbq.entity.MyFile;
import com.sbq.service.IMyFileService;
import com.sbq.tools.DateUtil;
import com.sbq.tools.FileType;
import com.sbq.tools.FileTypeUtil;
import com.sbq.tools.FileUtil;
import com.xiaoleilu.hutool.util.ImageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangyuan on 2017/3/28.
 */
@Controller
@RequestMapping(value = "/file")
public class FileController extends BaseController {

    @Autowired
    private IMyFileService myFileService;

    /**
     * 通用文件上传器
     *
     * @param upfile
     * @param req
     * @return
     * @throws Exception
     */
    @RequestLog(moduleName = "文件上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(@RequestParam("file") CommonsMultipartFile upfile, HttpServletRequest req) throws Exception {


        if (upfile.isEmpty()) {
            return renderRequestError();
        }

        MyFile myFile = new MyFile();
        myFile.setStateflag(0);
        myFile.setCreate_time(new Date());
        myFile.setTime_stamp(new Date());

        String path = req.getRealPath("/upload");

        /**
         * 上传文件根文件夹不存在则创建
         */
        FileUtil.judeDirExists(path);


        /**
         * 获取原有文件名称
         */
        String filename = upfile.getOriginalFilename();
        myFile.setFile_name(filename);

        /**
         * 获取当天日期,以日期为单位存储文件夹
         */
        String current_day = DateUtil.getCurrentDateString("yyyy-MM-dd");

        /**
         * 生成UUID
         */
        String uuid = UUID.randomUUID().toString();
        myFile.setUuid(uuid);

        /**
         * 文件夹不存在则创建
         */
        String save_path = path + "/" + current_day;
        FileUtil.judeDirExists(save_path);

        /**
         * 获取后缀名
         */
        String suffix_name = filename.substring(filename.lastIndexOf("."));

        /**
         * 服务器保存的文件名称
         */
        String file_save_name = uuid + suffix_name;
        myFile.setFile_save_name(file_save_name);

        /**
         * 服务器文件保存路径
         */
        String file_path = save_path + "/" + file_save_name;
        myFile.setFile_path(file_path);

        /**
         * 客户端本地文件路径
         */
        String local_file_path = req.getParameter("local_file_path");
        myFile.setLocal_file_path(local_file_path);

        /**
         * 开始写入文件
         */
        OutputStream os = new FileOutputStream(new File(save_path, file_save_name));
        InputStream is = upfile.getInputStream();

        //用于分析文件类型
        InputStream is2 = upfile.getInputStream();

        int length = 0;
        byte[] buffer = new byte[1024];
        while ((length = is.read(buffer)) != -1) {
            os.write(buffer, 0, length);
        }
        is.close();
        os.close();

        FileType ft = FileTypeUtil.getType(is2);

        long file_type = 0;
        if (FileType.JPEG == ft || FileType.PNG == ft || FileType.GIF == ft || FileType.TIFF == ft || FileType.BMP == ft) {
            file_type = 1;
        } else if (FileType.AVI == ft || FileType.RM == ft || FileType.MPG == ft || FileType.MP4 == ft || FileType.FLV == ft) {
            file_type = 2;
        } else if (FileType.RAM == ft || FileType.WAV == ft || FileType.MP3 == ft || FileType.AMR == ft) {
            file_type = 2;
        } else if (FileType.PPT_XLS_DOC == ft || FileType.PPTX_XLSX_DOCX == ft || FileType.WPS == ft || FileType.PDF == ft || FileType.ZIP == ft || FileType.RAR == ft) {
            file_type = 3;
        } else {
            file_type = 3;
        }
        myFile.setFile_type(file_type);


        /**
         * 根据后缀名处理对应属性
         */
        if (file_type == 1) {
            String zip_file_name = uuid + "_zip" + suffix_name;
//            String results = new CompressImgUtil().compressPic(
//                save_path
//                    + System.getProperty("file.separator"),
//                save_path
//                    + System.getProperty("file.separator"),
//                file_save_name, zip_file_name, 300, 300, true);
//
//            if (results.equals("ok")) {
            //设置压缩保存地址
//                myFile.setFile_zip_path(save_path + System.getProperty("file.separator") + zip_file_name);
//            }


            //TODO:图片压缩性能可能存在问题
            ImageUtil.scale(new File(save_path
                    + System.getProperty("file.separator") + file_save_name),
                new File(save_path
                    + System.getProperty("file.separator") + zip_file_name), 6);
            myFile.setFile_zip_path(save_path + System.getProperty("file.separator") + zip_file_name);

        } else if (file_type == 2) {
            //设置播放时长
            long second = 0;
            String msec = req.getParameter("msec");
            if (StringUtils.isNoneBlank(msec)) {
                second = Long.valueOf(msec);
            }
            myFile.setVideo_second(second);
        }

        /**
         * 获取文件大小
         */
        long fileSize = upfile.getSize();
        DecimalFormat df = new DecimalFormat("#.##");
        Double msize = 0.0;
        String file_size = "";
        if (fileSize >= 1048576)// 大于1M
        {
            msize = Double.valueOf(fileSize) / (1024 * 1024);
            file_size = df.format(msize) + "M";
        } else {
            msize = Double.valueOf(fileSize) / 1024;
            file_size = df.format(msize) + "KB";
        }
        myFile.setFile_size(file_size);


        //写入数据库
        myFileService.insertMyFile(myFile);

        Map result = new HashMap<>();
        result.put("int_id", myFile.getInt_id());
        result.put("uuid", myFile.getUuid());
        result.put("file_name", myFile.getFile_name());
        result.put("file_path", myFile.getFile_path());
        result.put("file_zip_path", myFile.getFile_zip_path());
        result.put("local_file_path", myFile.getLocal_file_path());
        result.put("file_web_path", Constants.DOWN_LOAD_PATH + myFile.getUuid());

        if (file_type == 1) {
            result.put("file_zip_web_path", Constants.DOWN_LOAD_PATH + uuid + "&zip=1");
        }


        return renderSuccess(result);
    }

    /**
     * 通用附件下载器
     * 通过uuid下载
     *
     * @param uuid
     * @param response
     */
    @RequestLog(moduleName = "文件下载")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public Object downLoadFile(@RequestParam("uuid") String uuid, @RequestParam(value = "zip", required = false) Integer zip, HttpServletResponse response) throws IOException {
        //查询附件原始名称,下载输出

        OutputStream out = null;
        InputStream inputStream = null;

        if (StringUtils.isBlank(uuid)) {
            Object resultMap = renderRequestError();
//            out = response.getOutputStream();
//
//            String result = jacksonObjectMapper.writeValueAsString(resultMap);
//
//
//            response.setCharacterEncoding("utf-8");
            return renderRequestError();

        } else {

            //TODO:可结合权限，限制是否可下载
            MyFile myFile = myFileService.selectByUUID(uuid);
            if (myFile == null) {
                return renderRequestError();
            }

            String filename = myFile.getFile_name();
            String filepath = myFile.getFile_path();
            String file_zip_path = myFile.getFile_zip_path();


            File file = new File(filepath);
            filename = new String(filename.getBytes(), "ISO-8859-1");

            out = response.getOutputStream();
            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);
            response.addHeader("Content-Length", "" + file.length());

            if (zip != null && zip.intValue() > 0) {
                if (StringUtils.isNoneBlank(file_zip_path)) {
                    filepath = file_zip_path;//使用压缩保存的地址
                }

            }

            inputStream = new FileInputStream(file);
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                out.write(b, 0, length);
            }
            out.close();
            inputStream.close();

            return null;
        }

    }
}
