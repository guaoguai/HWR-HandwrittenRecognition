import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Listener extends MouseAdapter implements ActionListener {
    private final String path = Main.PATH + "TrainingData\\";   //定义训练集数据路径

    private final Main panel;
    private final JComboBox<String> cbItem;

    private final Graphics2D g;

    private final int[][] pixel = new int[40][40];  //初始化像素数组
    private final int[][] sample = new int[40][40]; //初始化样本数组

    public Listener(Main panel, JComboBox<String> cbItem) {

        this.panel = panel;
        this.cbItem = cbItem;
        this.g = (Graphics2D) panel.getGraphics();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(Main.IDENTIFY)) Identify();     //识别操作
        if (e.getActionCommand().equals(Main.SAVE)) Save();             //保存操作

    }//重写事件响应方法

    /**
     * KNN实际上是没有训练过程的,因此也没有模型参数（训练数据时就在学习这个参数）
     * KNN在验证过程中计算验证样本点和已知样本点的距离,这时在学习超参数K,超参数是模型外面的参数
     */
    private void Identify(){
        //定义KNN
        Knn knn = new Knn();
        //获取训练集文件夹的文件列表
        String[] fileList = new File(path).list();
        //读取文件,开始计算
        if (fileList != null) {
            for (String fileName : fileList) {
                //从文件中导入数据
                File file = new File(path + fileName);
                String number = file.getName().substring(0, 1);
                FileReader in;

                try {

                    in = new FileReader(file);

                    for (int j = 0; j < 40; j++) {
                        for (int k = 0; k < 40; k++) {
                            sample[j][k] = in.read() - '0';  //逐字节读取
                        }
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //开始进行欧拉距离和KNN运算；
                knn.sort(sample, pixel, 40, number);
            }
            JOptionPane.showMessageDialog(panel, "预测数字为：" + knn.predict());

        }
        //重置画板
        panel.repaint();

    }//识别操作

    private void Save() {
        //进行文件操作
        if (cbItem.getSelectedItem() != null) {
            //获取所选数字
            String selectedNumber = cbItem.getSelectedItem().toString();
            //将当前时间设置为文件名
            String fileName = selectedNumber + "-" + new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + ".txt";
            //创建文件夹
            {
                File file = new File(path);
                if (!file.exists() && !file.isDirectory()) if (file.mkdirs()) System.out.println(path + "创建成功");
            }
            //创建并写入文件
            {
                File file = new File(path + fileName);
                try {
                    if (!file.exists()) if (file.createNewFile()) System.out.println(fileName + "创建成功");

                    FileWriter out = new FileWriter(file);

                    for (int i = 0; i < 40; i++) {
                        for (int j = 0; j < 40; j++) {
                            out.write(pixel[i][j] + "");
                        }
                    }

                    out.flush();
                    out.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //重置画板
        panel.repaint();

    }//保存操作


    private void pixelReset() {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                pixel[i][j] = 0;
            }

        }

    }//像素复位

    public void mousePressed(MouseEvent e) {
        pixelReset();

    }//鼠标按下

    public void mouseDragged(MouseEvent e) {
        //绘制所写
        g.setColor(Color.WHITE);
        int x = e.getX();
        int y = e.getY();
        g.fillRect(x, y, 10, 10);

        for (int i = x; i < x + 20; i++) {
            for (int j = y; j < y + 20; j++) {
                pixel[i / 10][j / 10] = 1;
            }

        }

    }//鼠标拖动

    public void mouseReleased(MouseEvent e) {

    }//释放鼠标
}
