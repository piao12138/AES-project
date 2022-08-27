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
     * ����GUI����
     */
    int flag = 0;
    String a;
    Icon icon;
    //���ð�ť
    JButton Encrypt = new JButton("���ܲ����ɶ�ά��");
    JButton Decrypt = new JButton("����");

    //�����ı���
    myJTextPane txt1 = new  myJTextPane();
    //��ά�����ɿ�Jlabel����
    JLabel jimageLabel = new JLabel();
    //ʹ����һ���ࣨmyJTextPane���½�����ࣩ�ķ���
    myJTextPane txt3 = new  myJTextPane();
    myJTextPane txt4 = new  myJTextPane();


    /**
     * ���ñ�ǩ
     */

    JLabel jl1 = new JLabel("����Ҫ���ܵ�����:");
    JLabel jl2 = new JLabel("��ά����ʾ:");
    JLabel jl3 = new JLabel("��������:");
    JLabel jl4 = new JLabel("���ܺ������:");



    public  void init() {

        /**
         * ���ô���
         */
        JFrame frame = new JFrame("������Ϣ��ά��������");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(220, 160);
        frame.setSize(540, 390);
        frame.setLayout(null);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //��������Ӹ��˵�
        JMenu menuView = new JMenu("�鿴");
        menuBar.add(menuView);


        //����ť��ӵ�����
        frame.add(Encrypt);
        frame.add(Decrypt);

        Encrypt.setSize(180,40);
        Encrypt.setLocation(22,200);

        Decrypt.setSize(180,40);
        Decrypt.setLocation(22,250);

        /**
         * �������������ı���Ĳ���
         */

        //�������������Ĺ���
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(16,30,200,55);
        scrollPane1.setViewportView(txt1);
        scrollPane1.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane1);

        //�������������Ļ������ã���С�����壬λ�õȣ�
        txt1.setPreferredSize(new Dimension(44, 40));
        txt1.setFont(new Font("΢���ź�",Font.BOLD,13));
        txt1.setSize(200, 55);
        txt1.setLocation(16, 30);


        //�������ɶ�ά�����ʾ����
        jimageLabel.setSize(134,134);
        jimageLabel.setLocation(300,30);
        frame.add(jimageLabel);




        /**
         * ������������ı���Ĳ���
         */

        //�������������Ĺ���
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(16,120,200,55);
        scrollPane.setViewportView(txt3);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane);

        //�������������Ļ������ã���С�����壬λ�õȣ�
        txt3.setPreferredSize(new Dimension(44, 40));
        txt3.setFont(new Font("΢���ź�",Font.BOLD,13));
        txt3.setSize(200, 55);
        txt3.setLocation(16, 120);
        txt3.setEditable(false);



        /**
         * ���ý��ܺ�������ı���Ĳ���
         */

        //���ý��ܺ������Ĺ���
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(270,220,200,55);
        scrollPane2.setViewportView(txt4);
        scrollPane2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(scrollPane2);

        //���ý��������Ļ������ã���С��λ�ã�����ȣ�
        txt4.setPreferredSize(new Dimension(44, 40));
        txt4.setFont(new Font("΢���ź�",Font.BOLD,13));
        txt4.setSize(200, 55);
        txt4.setLocation(270, 220);
        txt4.setEditable(false);


        /**
         * ���ñ�ǩ�Ĳ���
         */
        jl1.setBounds(20,0,194,29);
        frame.add(jl1);
        jl2.setBounds(300,0,194,29);
        frame.add(jl2);
        jl3.setBounds(20,90,194,29);
        frame.add(jl3);
        jl4.setBounds(280,190,194,29);
        frame.add(jl4);


        jl1.setFont(new Font("΢���ź�",Font.BOLD,13));
        jl2.setFont(new Font("΢���ź�",Font.BOLD,13));
        jl3.setFont(new Font("΢���ź�",Font.BOLD,13));
        jl4.setFont(new Font("΢���ź�",Font.BOLD,13));

        Encrypt.addActionListener(this);
        Decrypt.addActionListener(this);

        /**
         * ���ò˵�
         */
        JMenuBar menuBar1;
        menuBar1=new JMenuBar();
        JMenu menu1;
        menu1=new JMenu("��������");
        JMenuItem writer1,writer2,writer3,writer4;
        writer1=new JMenuItem("�Ż���");
        writer2=new JMenuItem("�ó���");
        writer3=new JMenuItem("����Դ");
        writer4=new JMenuItem("�촫ΰ");
        menu1.add( writer1);menu1.add( writer2);menu1.add( writer3);menu1.add( writer4);
        menuBar1.add(menu1);
        frame.setJMenuBar(menuBar1);
        frame.setVisible(true);

    }


    /**
     * ���ɺ�ʹ�üӽ��ܲ��������ܺ��������ܺ�������ά�����ɺ�������ά��ʶ����
     */

    public static  String default_key = randomStr();//������ɼ�����Ҫ��default_key
    static final Base64.Decoder decoder = Base64.getDecoder();
    static final Base64.Encoder encoder = Base64.getEncoder();
    static final String charset = "utf-8";
    static final String AES = "AES";

    /**
     *
     * ���������Կ
     * @return
     */

    static String randomStr()  {
        int length = 10;
        char[] ss = new char[length];//���ڱ���������ɵ��ַ�����
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
     * AES����
     */

    public static String encode(String encodeRules, String content){
        try {
            //1.������Կ��������ָ��ΪAES�㷨,�����ִ�Сд
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            //2.����ecnodeRules�����ʼ����Կ������
            //����һ��128λ�����Դ,���ݴ�����ֽ�����
//            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.����ԭʼ�Գ���Կ
            SecretKey original_key = keygen.generateKey();
            //4.���ԭʼ�Գ���Կ���ֽ�����
            byte [] raw = original_key.getEncoded();
            //5.�����ֽ���������AES��Կ
            SecretKey key = new SecretKeySpec(raw, AES);
            //6.����ָ���㷨AES�Գ�������
            Cipher cipher = Cipher.getInstance(AES);
            //7.��ʼ������������һ������Ϊ����(Encrypt_mode)���߽��ܽ���(Decrypt_mode)�������ڶ�������Ϊʹ�õ�KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.��ȡ�������ݵ��ֽ�����(����Ҫ����Ϊutf-8)��Ȼ��������������ĺ�Ӣ�Ļ�����ľͻ����Ϊ����
            byte [] byte_encode = content.getBytes(charset);
            //9.�����������ĳ�ʼ����ʽ--���ܣ������ݼ���
            byte [] byte_AES = cipher.doFinal(byte_encode);
            //10.�����ܺ������ת��Ϊ�ַ���
            //������Base64Encoder�л��Ҳ�����
            //����취��
            //����Ŀ��Build path�����Ƴ�JRE System Library������ӿ�JRE System Library�����±�����һ�������ˡ�
            String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
            //11.���ַ�������
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//����ʧ�ܷ��ؿ�
    }

    /**
     * return ����
     * */

    public static String encode(String content){
        return encode(default_key, content);
    }

    /**
     * ���ü��ܺ������������Ĳ���return����
     * */

    public static String encoded(String content){
        String encode = encode(content);//AES����
        if(encode == null) return null;//����ʧ�ܷ��ؿ�
        try {
            String str = encoder.encodeToString(encode.getBytes(charset));//Base64����
            return str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * ���ɶ�ά��
     */

    private static final String QR_CODE_IMAGE_PATH = "D:/D/Documents/Desktop/test1.png";//���ɶ�ά��Ĵ��λ��
    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }


    /**
     * ɨ���ά��
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
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);//����
            content = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            //�����ж����ʶ���˴�LOGO��ͼƬ�����������һ������
            try {
                image = ImageIO.read(new File(path));
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                Binarizer binarizer = new HybridBinarizer(source);
                BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
                Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
                //���ñ����ʽ
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
                //�����Ż�����
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                //���ø���ģʽ��������ʹ�����ַ�ʽ�Ϳ���ʶ��΢�ŵĶ�ά���ˣ�
                hints.put(DecodeHintType.PURE_BARCODE,Boolean.TYPE);
                Result result = new MultiFormatReader().decode(binaryBitmap, hints);//����
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
     * ���ý��ܺ���
     */

    public static String decoded(String content){
        try {
            String s = new String(decoder.decode(content), charset);//Base64����
            String decode = decode(s);//AES����
            return decode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;//����ʧ�ܷ��ؿ�
    }

    /**
     * AES����
     * ���ܹ��̣�
     * 1.ͬ����1-4��
     * 2.�����ܺ���ַ������ĳ�byte[]����
     * 3.���������ݽ���
     */

    public static String decode(String encodeRules, String content){
        try {
            //1.������Կ��������ָ��ΪAES�㷨,�����ִ�Сд
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            //2.����ecnodeRules�����ʼ����Կ������
            //����һ��128λ�����Դ,���ݴ�����ֽ�����
//            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.����ԭʼ�Գ���Կ
            SecretKey original_key = keygen.generateKey();
            //4.���ԭʼ�Գ���Կ���ֽ�����
            byte [] raw = original_key.getEncoded();
            //5.�����ֽ���������AES��Կ
            SecretKey key = new SecretKeySpec(raw, AES);
            //6.����ָ���㷨AES�Գ�������
            Cipher cipher = Cipher.getInstance(AES);
            //7.��ʼ������������һ������Ϊ����(Encrypt_mode)���߽���(Decrypt_mode)�������ڶ�������Ϊʹ�õ�KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.�����ܲ����������ݽ�����ֽ�����
            byte [] byte_content = new BASE64Decoder().decodeBuffer(content);

            /**
             * ����
             */

            byte [] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode,charset);
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;//����ʧ�ܷ��ؿ�
    }

    /**
     * ����AES���ܺ�����return ���ܺ������
     */

    public static String decode(String content){
        return decode(default_key, content);
    }

    /**
     *�����봰�ڻ�ȡ���ģ������ü��ܺ������������ģ�Ȼ�����������������ģ������Ľ��ж�ά�뻯����ʾ�ڴ�����
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * ����������Ľ��м���
         */
        if (e.getSource() == Encrypt) {

            //������ǵ�һ�μ��ܣ�����ά������գ������ά�����
            if (flag == 1) {
                jimageLabel.setIcon(null);
            }
            a = txt1.getText();

            //���ú���ִ�м��ܲ���
            long timeStart = System.currentTimeMillis();
            String str = encoded(a);
            long timeEnd = System.currentTimeMillis();
            double time = (timeEnd - timeStart);

            //�����ά��
            try {
                generateQRCodeImage(str, 67 + 12 * 5, 67 + 12 * 5, QR_CODE_IMAGE_PATH);
            } catch (WriterException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                icon = new ImageIcon(ImageIO.read(new File(QR_CODE_IMAGE_PATH)));//��ȡ��Ӧ·���µ�ͼƬ�ļ�
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //��ָ��λ�õĶ�ά���ȡ��jimagelabel��
            jimageLabel.setIcon(icon);
            txt3.setText(str);  //+"\n"+"����ʱ��Ϊ"+time+"ms"
            flag=1;
        }

        /**
         * �����ɵ����Ľ���
         */
        if(e.getSource() == Decrypt) {
            String decotor = deEncodeByPath("D:/D/Documents/Desktop/test1.png");//��ά��ͼƬ·��
            String s1 = decoded(decotor);
            txt4.setText(s1);
        }
    }

    /**
     * ������
     */
    public static void main(String[] args)throws IOException  {
        new demo().init();
    }
}

