package cn.yangself.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        /*
        上传三步
         */
        //工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //解析得到List
        try {
            List<FileItem> list = sfu.parseRequest(request);
            FileItem fi = list.get(1);
            //如何得根路径
            //1、得到文件保存的路径
            String root = this.getServletContext().getRealPath("/WEB-INF/files/");

            //2、生成两层目录
            //得到文件名称，通过文件名称获得hashcode，转换成16进制，获取前两个字符用来生成目录
            String filename = fi.getName();

            //处理文件名的绝对路径问题
            int index = filename.lastIndexOf("\\");
            if (index!=-1){
                filename = filename.substring(index+1);
            }

            //给文件名称添加uuid前缀，处理文件重名问题
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replaceAll("-", "");
            String savename = uuid + "_"+filename;
            //得到hashcode
            int hCode = filename.hashCode();
            String hex = Integer.toHexString(hCode);

            //获取hex的钱连个字符，与root连接在一起，生成一个完整的路径
            File dirFile = new File(root,hex.charAt(0)+"/"+hex.charAt(1));

            //创建目录链
            dirFile.mkdirs();

            //创建目标文件
            File destFile = new File(dirFile,savename);

            //保存
            fi.write(destFile);

            /*
            下载的要求
            两个头一个流
            Content-Type:你传递给客户端的文件是什么MIME类型，例如：image/pjepg
            Content-Disposition:它的默认值为inline，表示在浏览器窗口中打开：attachment;filename=xxx
            流：要下载的文件数据！
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
