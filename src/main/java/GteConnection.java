import java.io.*;
import java.sql.*;

/**
 * @program: testBlob
 * @description: ${description}
 * @author: RenChao
 * @create: 2019-09-29 11:00
 **/
public class GteConnection {

    public static Connection getConnection() {
        Connection conn = null;
        System.out.println("开始进行加载驱动类");
        // 加载类
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 获取连接
            conn = DriverManager.getConnection("jdbc:oracle:thin:@r1227.erptest.com:1521:test",
                    "apps",
                    "apps");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现了错误！！！");
        }
        return conn;
    }

    public static BlobFiles getBlobFiles() {
        // 一步一步来，先进行获取连接的测试
        Connection connection;
        PreparedStatement preparedStatement;
        String sql;
        ResultSet resultSet;
        Blob blob;
        String fileName;
        String fileType;
        BlobFiles blobFiles = null;
        connection = getConnection();
        if (connection == null) {
            System.out.println("获取链接失败！");
            return null;
        }
        System.out.println("进行SQL语句的查看！");
        sql = "SELECT t.file_name,       t.file_type      ,t.file_byte  FROM cux_test_21101 t where t.file_name = '平台管理员证明上传(admin_prove).pdf'";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fileName = resultSet.getString("file_name");
                fileType = resultSet.getString("file_type");
                blob = resultSet.getBlob("file_byte");
                System.out.println("文件名称为：" + fileName + '.' + fileType);
                System.out.println("文件内容为：" + blob.toString());
                blobFiles = new BlobFiles(blob, fileName, fileType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blobFiles;
    }

    public static void setNewBlobFiles(BlobFiles blobFiles) {
        /// 进行写入文件的测试
        File file;
        byte[] bytes = new  byte[1000];
       // blobFiles = getBlobFiles();
        Blob blob = blobFiles.getBlob();
        String fileName = blobFiles.fileName;//blobFiles.fileName + '.' + blobFiles.fileType;
        Long leng = null;
        Integer length;
        try {
            leng = blob.length();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取文件长度出错！");
        }
        System.out.println("文件长度为" + leng);

        file = new File("C:\\Users\\13192\\Desktop\\HAP进行供应商许可证测试\\生成文件路径\\" + fileName);
        if (file.exists()) {
            System.out.println("文件存在！继续执行下一步骤！");
        } else {
            System.out.println("文件不存在！执行文件创建步骤！");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 读取源文件的内容，复制进新文件内
        try {
            InputStream inputStream = blob.getBinaryStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while ((length = inputStream.read(bytes)) !=-1) {
                fileOutputStream.write(bytes, 0, length);
            }
            fileOutputStream.close();
            System.out.println("文件创建成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static BlobFiles getBlobFiles(Integer p_oid ) {
        // 一步一步来，先进行获取连接的测试
        Connection connection;
        PreparedStatement preparedStatement;
        String sql;
        ResultSet resultSet;
        Blob blob = null;
        String fileName= null;
        String fileType= null;
        BlobFiles blobFiles = null;
        boolean executeFlag;
        connection = getConnection();
        if (connection == null) {
            System.out.println("获取链接失败！");
            return null;
        }
        System.out.println("进行SQL语句的查看！");
        sql = "begin cux_srm_get_vendor_blob_files ( "+ p_oid + "); end ;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            executeFlag = preparedStatement.execute();
          /*if (executeFlag){
              System.out.println("调用存储过程成功！");
          }else{
              System.out.println("调用存储过程失败！，结束！");
              return null;
          }*/

            sql = "SELECT t.oid,t.lefal_represent_id_b from cux_srm_support_blob_files_all t where t.oid = " + p_oid;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                blob = resultSet.getBlob("lefal_represent_id_b");
            }
            if (blob != null){
                blobFiles = new BlobFiles(blob, fileName, fileType);
            }else{
                System.out.println("没有获取到BLOB字段");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blobFiles;
    }

    public static void main(String[] args) {
        // 获取文件的的Blob字段
        BlobFiles blobFiles = getBlobFiles(1);
        //Blob blob = blobFiles.getBlob();
        if(blobFiles!= null){
            setNewBlobFiles(blobFiles);
        }

    }


}
