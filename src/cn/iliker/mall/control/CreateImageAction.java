package cn.iliker.mall.control;

import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.stateattr.OrderState;
import cn.iliker.mall.service.ITOrderSvc;
import cn.iliker.mall.utils.QRCodeUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

public class CreateImageAction extends ActionSupport {
    private ByteArrayInputStream inputStream;
    private ITOrderSvc orderSvc;
    private TOrder tOrder;

    public TOrder gettOrder() {
        return tOrder;
    }

    public void settOrder(TOrder tOrder) {
        this.tOrder = tOrder;
    }

    private final static int WIDTH = 60;

    private final static int HEIGHT = 20;

    public void setOrderSvc(ITOrderSvc orderSvc) {
        this.orderSvc = orderSvc;
    }

    public ByteArrayInputStream getInputStream() {
        return inputStream;
    }

    private void setInputStream(ByteArrayInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private String QRCodeContent;

    public void setQRCodeContent(String QRCodeContent) {
        this.QRCodeContent = QRCodeContent;
    }

    public String getQRCodeContent() {
        return QRCodeContent;
    }

    private static String createRandom() {
        String str = "0123456789qwertyuiopasdfghjklzxcvbnm";

        char[] rands = new char[4];

        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            rands[i] = str.charAt(random.nextInt(36));
        }

        return new String(rands);
    }

    private void drawBackground(Graphics g) {
        // 画背景
        g.setColor(new Color(0xDCDCDC));

        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 随机产生 120 个干扰点

        for (int i = 0; i < 120; i++) {
            int x = (int) (Math.random() * WIDTH);

            int y = (int) (Math.random() * HEIGHT);

            int red = (int) (Math.random() * 255);

            int green = (int) (Math.random() * 255);

            int blue = (int) (Math.random() * 255);

            g.setColor(new Color(red, green, blue));

            g.drawOval(x, y, 1, 0);
        }
    }

    private void drawRands(Graphics g, String rands) {
        g.setColor(Color.BLACK);

        g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));

        // 在不同的高度上输出验证码的每个字符

        g.drawString("" + rands.charAt(0), 1, 17);

        g.drawString("" + rands.charAt(1), 16, 15);

        g.drawString("" + rands.charAt(2), 31, 18);

        g.drawString("" + rands.charAt(3), 46, 16);

    }

    public String createcode() throws Exception {
        ActionContext.getContext().getSession().remove("checkCode");
        HttpServletResponse response = ServletActionContext.getResponse();

        // 设置浏览器不要缓存此图片
        response.setHeader("Pragma", "no-cache");

        response.setHeader("Cache-Control", "no-cache");

        response.setDateHeader("Expires", 0);

        String rands = createRandom();

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        // 产生图像
        drawBackground(g);

        drawRands(g, rands);

        // 结束图像 的绘制 过程， 完成图像
        g.dispose();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "jpeg", outputStream);

        ByteArrayInputStream input = new ByteArrayInputStream(
                outputStream.toByteArray());

        this.setInputStream(input);

        ActionContext.getContext().getSession().put("checkCode", rands);

        input.close();

        outputStream.close();
        return SUCCESS;
    }

    public String createQRCode() {
        String url = ServletActionContext.getServletContext().getRealPath("/images/logo.png");
        File file = new File(url);
        try {
            if (file.exists()) {
                this.setInputStream(QRCodeUtil.encode("http://iliker.cn/wx/index.jsp?superiornum=" + QRCodeContent, file.getPath(), false));
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String pickupCode() {
        TOrder foundOrder = orderSvc.findById(tOrder.getId());
        /*等待配货订单状态才能自提
        * {\"ilikerAppOrderID\":" + webOrder.getId() + "}
        * */
        if (OrderState.ADMEASUREPRODUCT.getName().equals(foundOrder.getOrderstate())) {
            try {
                this.setInputStream(QRCodeUtil.encode("{\"ilikerAppOrderID\":" + foundOrder.getId() + "}", "", false));
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return NONE;
    }
}
