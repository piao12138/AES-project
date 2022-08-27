import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class demo extends JFrame  implements ActionListener {



    /**
     * 配置GUI界面
     */
    int flag = 0;
    String a;
    Icon icon;
    //设置按钮
    JButton Encrypt = new JButton("加密并生成二维码");
    JButton Decrypt = new JButton("解密");

    //设置文本框
    myJTextPane txt1 = new  myJTextPane();
    //二维码生成框Jlabel类型
    JLabel jimageLabel = new JLabel();
    //使用另一个类（myJTextPane，新建别的类）的方法
    myJTextPane txt3 = new  myJTextPane();
    myJTextPane txt4 = new  myJTextPane();


    /**
     * 设置标签
     */

    JLabel jl1 = new JLabel("输入要加密的明文:");
    JLabel jl2 = new JLabel("二维码显示:");
    JLabel jl3 = new JLabel("密文内容:");
    JLabel jl4 = new JLabel("解密后的密文:");



    public  void init() {

        /**
         * 设置窗口
         */
        JFrame frame = new JFrame("保密信息二维码生成器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(220, 160);
        frame.setSize(540, 390);
        frame.setLayout(null);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //创建并添加各菜单
        JMenu menuView = new JMenu("查看");
        menuBar.add(menuView);


        //将按钮添加到窗口
        frame.add(Encrypt);
        frame.add(Decrypt);

        Encrypt.setSize(180,40);
        Encrypt.setLocation(22,200);

        Decrypt.setSize(180,40);
        Decrypt.setLocation(22,250);

        /**
         * 设置输入明文文本框的参数
         */

        //配置明文输入框的滚轮
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(16,30,200,55);
        scrollPane1.setViewportView(txt1);
        scrollPane1.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane1);

        //配置明文输入框的基本配置（大小，字体，位置等）
        txt1.setPreferredSize(new Dimension(44, 40));
        txt1.setFont(new Font("微软雅黑",Font.BOLD,13));
        txt1.setSize(200, 55);
        txt1.setLocation(16, 30);


        //设置生成二维码的显示参数
        jimageLabel.setSize(134,134);
        jimageLabel.setLocation(300,30);
        frame.add(jimageLabel);




        /**
         * 设置输出密文文本框的参数
         */

        //配置密文输出框的滚轮
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(16,120,200,55);
        scrollPane.setViewportView(txt3);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);

        //配置密文输出框的基本配置（大小，字体，位置等）
        txt3.setPreferredSize(new Dimension(44, 40));
        txt3.setFont(new Font("微软雅黑",Font.BOLD,13));
        txt3.setSize(200, 55);
        txt3.setLocation(16, 120);
        txt3.setEditable(false);



        /**
         * 设置解密后的明文文本框的参数
         */

        //配置解密后输出框的滚轮
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(270,220,200,55);
        scrollPane2.setViewportView(txt4);
        scrollPane2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane2);

        //配置解密输出框的基本配置（大小，位置，字体等）
        txt4.setPreferredSize(new Dimension(44, 40));
        txt4.setFont(new Font("微软雅黑",Font.BOLD,13));
        txt4.setSize(200, 55);
        txt4.setLocation(270, 220);
        txt4.setEditable(false);


        /**
         * 设置标签的参数
         */
        jl1.setBounds(20,0,194,29);
        frame.add(jl1);
        jl2.setBounds(300,0,194,29);
        frame.add(jl2);
        jl3.setBounds(20,90,194,29);
        frame.add(jl3);
        jl4.setBounds(280,190,194,29);
        frame.add(jl4);


        jl1.setFont(new Font("微软雅黑",Font.BOLD,13));
        jl2.setFont(new Font("微软雅黑",Font.BOLD,13));
        jl3.setFont(new Font("微软雅黑",Font.BOLD,13));
        jl4.setFont(new Font("微软雅黑",Font.BOLD,13));

        Encrypt.addActionListener(this);
        Decrypt.addActionListener(this);

        /**
         * 设置菜单
         */
        JMenuBar menuBar1;
        menuBar1=new JMenuBar();
        JMenu menu1;
        menu1=new JMenu("关于作者");
        JMenuItem writer1,writer2,writer3,writer4;
        writer1=new JMenuItem("张会凌");
        writer2=new JMenuItem("竺超阳");
        writer3=new JMenuItem("杨依源");
        writer4=new JMenuItem("朱传伟");
        menu1.add( writer1);menu1.add( writer2);menu1.add( writer3);menu1.add( writer4);
        menuBar1.add(menu1);
        frame.setJMenuBar(menuBar1);
        frame.setVisible(true);

    }


    /**
     * 生成和使用加解密参数、加密函数、解密函数、二维码生成函数、二维码识别函数
     */

    public static  String default_key = randomStr();//随机生成加密需要的default_key
    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();
    static final String charset = "utf-8";
    static final String AES = "AES";

    /**
     *
     * 生成随机密钥
     * @return
     */

    static String randomStr()  {
        int length = 10;
        char[] ss = new char[length];//用于保存随机生成的字符串；
        int i=0;
        while(i<length) {
            int f = (int) (Math.random()*3%3);
            if(f==0)
                ss[i] = (char) ('A'+Math.random()*26);
            else if(f==1)
                ss[i] = (char) ('a'+Math.random()*26);
            else
                ss[i] = (char) ('0'+Math.random()*10);
            i++;
        }
        return new String(ss);
    }

    /**
     * AES加密
     */

    public static String encode(String encodeRules, String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
//            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(AES);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode = content.getBytes(charset);
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byte_AES = cipher.doFinal(byte_encode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            //11.将字符串返回
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//加密失败返回空
    }

    /**
     * return 密文
     * */

    public static String encode(String content){
        return encode(default_key, content);
    }

    /**
     * 调用加密函数，接受密文并，return密文
     * */

    public static String encoded(String content){
        String encode = encode(content);//AES加密
        if(encode == null) return null;//加密失败返回空
        try {
            String str = encoder.encodeToString(encode.getBytes(charset));//Base64加密
            return str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成二维码
     */

    private static final String QR_CODE_IMAGE_PATH = "D:/D/Documents/Desktop/test1.png";//生成二维码的存放位置
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


    /**
     * 扫描二维码
     */

    public static String deEncodeByPath(String path) {
        String content = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            //这里判断如果识别不了带LOGO的图片，重新添加上一个属性
            try {
                image = ImageIO.read(new File(path));
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
                //设置编码格式
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                //设置优化精度
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                //设置复杂模式开启（我使用这种方式就可以识别微信的二维码了）
                hints.put(DecodeHintType.PURE_BARCODE,Boolean.TYPE);
                Result result = new MultiFormatReader().decode(binaryBitmap, hints);//解码
                content = result.getText();
            } catch (IOException exception) {
                e.printStackTrace();
            } catch (NotFoundException exception) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 调用解密函数
     */

    public static String decoded(String content){
        try {
            String s = new String(decoder.decode(content), charset);//Base64解密
            String decode = decode(s);//AES解密
            return decode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;//解密失败返回空
    }

    /**
     * AES解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */

    public static String decode(String encodeRules, String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
//            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw = original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, AES);
            //6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(AES);
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content = new BASE64Decoder().decodeBuffer(content);

            /**
             * 解密
             */

            byte [] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode,charset);
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//解密失败返回空
    }

    /**
     * 调用AES解密函数，return 解密后的密文
     */

    public static String decode(String content){
        return decode(default_key, content);
    }

    /**
     *从输入窗口获取明文，并调用加密函数，接收密文，然后在输出窗口输出密文，对密文进行二维码化，显示在窗口上
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * 将输入的明文进行加密
         */
        if (e.getSource() == Encrypt) {

            //如果不是第一次加密，将二维码区清空，避免二维码叠加
            if (flag == 1) {
                jimageLabel.setIcon(null);
            }
            a = txt1.getText();

            //调用函数执行加密操作
            long timeStart = System.currentTimeMillis();
            String str = encoded(a);
            long timeEnd = System.currentTimeMillis();
            double time = (timeEnd - timeStart);

            //放入二维码
            try {
                generateQRCodeImage(str, 67 + 12 * 5, 67 + 12 * 5, QR_CODE_IMAGE_PATH);
            } catch (WriterException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                icon = new ImageIcon(ImageIO.read(new File(QR_CODE_IMAGE_PATH)));//获取相应路径下的图片文件
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //将指定位置的二维码读取到jimagelabel里
            jimageLabel.setIcon(icon);
            txt3.setText(str);  //+"\n"+"加密时间为"+time+"ms"
            flag=1;
        }

        /**
         * 将生成的密文解密
         */
        if(e.getSource() == Decrypt) {
            String decotor = deEncodeByPath("D:/D/Documents/Desktop/test1.png");//二维码图片路径
            String s1 = decoded(decotor);
            txt4.setText(s1);
        }
    }

    /**
     * 主函数
     */
    public static void main(String[] args)throws IOException  {
        new demo().init();
    }
}

