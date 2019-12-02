package cn.yangself.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        /*
        上传三步
        1.得到工厂
        2.通过工厂创建解析器
        3.解析request，得到FileItem集合
        4.遍历FileItem集合，调用其API完成文件的保存
         */
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();//创建工厂
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);//创建解析器
//        servletFileUpload.setFileSizeMax(100*1024);//限制文件大小为100k
//        servletFileUpload.setSizeMax(1024*1024);//限制整个表单的大小为1M
        try {
            List<FileItem> fileItemList = servletFileUpload.parseRequest(request);
            FileItem file1 = fileItemList.get(0);
            FileItem file2 = fileItemList.get(1);

            System.out.println("普通表单项："+file1.getFieldName()+"="+file1.getString("utf-8"));
            System.out.println("文件表单项：");
            System.out.println("Content-Type:"+file2.getContentType());
            System.out.println("size:"+file2.getSize());
            System.out.println("filename:"+file2.getName());
            int index = file2.getName().lastIndexOf(".");
            String path = file2.getName().substring(index);
            File file = new File("/Users/yangself/IdeaProjects/exam"+path);
            /*
            文件必须保存到WEB-INF下，目的是不让浏览器直接访问到
            文件名乱码使用request.setCharacterEncoding("utf-8")，内部会进行调用
            上传文件同名问题，我们需要为每个文件添加名称前缀，这个前缀不能重复，uuid是非常好的方法
            目录打散问题：不能在一个目录下存放过多的文件
             */
            file2.write(file);
        } catch (Exception e) {
            if (e instanceof FileUploadBase.FileSizeLimitExceededException){
                request.setAttribute("msg","您上传的文件超出了100kb");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            if (e instanceof FileUploadBase.SizeLimitExceededException){
                request.setAttribute("msg","您的表单超过了1M");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }

    }

}
