package cn.iliker.mall.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

//生成二维码
public class QRCodeUtil {

    //二维码编码方式
    private static final String qrcode_Charset = "utf-8";
    //生产二维码图片格式
    private static final String qrcode_FormatName = "JPG";
    //二维码尺度
    private static final int qrcode_Size = 300;
    //二维码logo宽度
    private static final int logo_Width = 25;
    //二维码logo长度
    private static final int logo_Height = 25;

    /***
     * 方法： 生产二维码图片 不带logo
     * 参数：
     *
     * @param context 二维码内容
     */
    public static BufferedImage creatImage(String context) throws Exception {
        //定义二维码的基本参数 存放在HashTable中
        Hashtable<EncodeHintType, Object> hint = new Hashtable<>();
        //二维码的错误识别率
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //二维码编码
        hint.put(EncodeHintType.CHARACTER_SET, qrcode_Charset);

        //定义bit矩阵  参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(context, BarcodeFormat.QR_CODE, qrcode_Size, qrcode_Size, hint);
        //获取二维码的长宽
        int qrcode_Width = bitMatrix.getWidth();
        int qrcode_Height = bitMatrix.getHeight();

        //生产图片
        BufferedImage image = new BufferedImage(qrcode_Width, qrcode_Height, BufferedImage.TYPE_INT_BGR);
        for (int i = 0; i < qrcode_Width; i++) {
            for (int j = 0; j < qrcode_Height; j++) {            /***黑色*****白色***/
                image.setRGB(i, j, bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;


    }

    /***
     * 方法： 生产二维码图片带logo
     * 参数：
     *
     * @param context      二维码内容
     * @param imgPath      二维码存放路径
     * @param needCompress logo图片是否需要压缩
     */
    private static BufferedImage creatImage(String context, String imgPath, boolean needCompress) throws Exception {
        //定义二维码的基本参数 存放在HashTable中
        Hashtable<EncodeHintType, Object> hint = new Hashtable<>();
        //二维码的错误识别率
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //二维码编码
        hint.put(EncodeHintType.CHARACTER_SET, qrcode_Charset);

        //定义bit矩阵  参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
        BitMatrix bitMatrix = new MultiFormatWriter().encode(context, BarcodeFormat.QR_CODE, qrcode_Size, qrcode_Size, hint);
        //获取二维码的长宽
        int qrcode_Width = bitMatrix.getWidth();
        int qrcode_Height = bitMatrix.getHeight();

        //生产图片
        BufferedImage image = new BufferedImage(qrcode_Width, qrcode_Height, BufferedImage.TYPE_INT_BGR);
        for (int i = 0; i < qrcode_Width; i++) {
            for (int j = 0; j < qrcode_Height; j++) {            /***黑色*****白色***/
                image.setRGB(i, j, bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        QRCodeUtil.insertLogoImage(image, imgPath, needCompress);
        return image;


    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */

    private static void insertLogoImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        //判断logo是否存在
        File file = new File(imgPath);
        if (!file.exists()) {
            System.out.println("logo图片不存在");
            return;
        }
        //读取logo图片
        Image src = ImageIO.read(file);
        //获取logo图片的长宽
        int logo_width = src.getWidth(null);
        int logo_height = src.getHeight(null);
        //判断是否需要压缩logo图片
        if (needCompress) {
            if (logo_width > logo_Width) {
                logo_width = logo_Width;
            }
            if (logo_height > logo_Height) {
                logo_height = logo_Height;
            }

            //压缩图片
            //image.SCALE_SMOOTH //平滑优先搜索
            //image.SCALE_FAST//速度优先
            //image.SCALE_AREA_AVERAGING //区域均值
            //image.SCALE_REPLICATE //像素复制型缩放
            //image.SCALE_DEFAULT //默认缩放模式
            Image image = src.getScaledInstance(logo_width, logo_height, Image.SCALE_SMOOTH);
            //定义图片
            Image tag = new BufferedImage(logo_width, logo_height, BufferedImage.TYPE_INT_BGR);

            //绘制压缩后的图片
            Graphics g = tag.getGraphics();
            g.drawImage(tag, 0, 0, null);
            g.dispose();
            src = image;//修改logo图片大小
        }
        //二维码中插入logo
        Graphics2D graph = source.createGraphics();
        //坐标
        int x = (qrcode_Size - logo_width) / 2;
        int y = (qrcode_Size - logo_height) / 2;
        /*
         * img - 要绘制的指定图像。如果 img 为 null，则此方法不执行任何动作。
         *  x - x 坐标。
         *  y - y 坐标。
         *  width - 矩形的宽度。
         *  height - 矩形的高度。
         *  observer - 当转换了更多图像时要通知的对象。
         * */
        graph.drawImage(src, x, y, logo_width, logo_height, null);
        graph.setStroke(new BasicStroke(3f));
        //保存图片
        Shape shape = new RoundRectangle2D.Float(x, y, logo_width, logo_height, 6, 6);
        //设置图片连接处的方式
        graph.draw(shape);
        graph.dispose();

    }


    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param imgPath      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.creatImage(content, imgPath,
                needCompress);
        ImageIO.write(image, qrcode_FormatName, output);
    }


    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param needCompress 是否压缩LOGO
     * @throws Exception
     */
    public static ByteArrayInputStream encode(String content, String imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.creatImage(content, imgPath,
                needCompress);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "jpeg", outputStream);

        ByteArrayInputStream input = new ByteArrayInputStream(
                outputStream.toByteArray());
        input.close();

        outputStream.close();
        return input;
    }


}
