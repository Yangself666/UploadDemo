package cn.yangself.servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        两个头一个流
        1、Content-Type
        2、Conten-Dispositon
        3、流：下载文件的数据
         */

        String filename = "/Users/yangself/Downloads/刘弘扬.doc";
        //为了使下载框中显示中文文件名称不出乱码
        String framename = new String(filename.substring(filename.lastIndexOf("/")+1).getBytes("GBK"),"ISO-8859-1");

        String contentType = this.getServletContext().getMimeType(filename);//通过文件名称获取MIME类型
        String contentDisposition = "attachment;filename="+framename;



        //一个流
        File dPath = new File(filename);
        FileInputStream fileInputStream = new FileInputStream(dPath);


        //设置头
        response.setHeader("Content-Type",contentType);
        response.setHeader("Content-Disposition", contentDisposition);

        //把流输出
        ServletOutputStream output = response.getOutputStream();//获取绑定了客户端的输出流

        IOUtils.copy(fileInputStream, output);//把输入流中的数据写入到输出流中

        fileInputStream.close();
        /*
        下载的细节：
        1、下载文件中文名乱码的问题
            ｜-FireFox:Base64编码
            ｜-其他大部分浏览器都使用URL编码。

        通用方案：filename = new String(filename.getBytes("GBK"),"ISO-8859-1");
         */
    }
}
