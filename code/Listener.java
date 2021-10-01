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
    private final String path = Main.PATH + "TrainingData\\";   //����ѵ��������·��

    private final Main panel;
    private final JComboBox<String> cbItem;

    private final Graphics2D g;

    private final int[][] pixel = new int[40][40];  //��ʼ����������
    private final int[][] sample = new int[40][40]; //��ʼ����������

    public Listener(Main panel, JComboBox<String> cbItem) {

        this.panel = panel;
        this.cbItem = cbItem;
        this.g = (Graphics2D) panel.getGraphics();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals(Main.IDENTIFY)) Identify();     //ʶ�����
        if (e.getActionCommand().equals(Main.SAVE)) Save();             //�������

    }//��д�¼���Ӧ����

    /**
     * KNNʵ������û��ѵ�����̵�,���Ҳû��ģ�Ͳ�����ѵ������ʱ����ѧϰ���������
     * KNN����֤�����м�����֤���������֪������ľ���,��ʱ��ѧϰ������K,��������ģ������Ĳ���
     */
    private void Identify(){
        //����KNN
        Knn knn = new Knn();
        //��ȡѵ�����ļ��е��ļ��б�
        String[] fileList = new File(path).list();
        //��ȡ�ļ�,��ʼ����
        if (fileList != null) {
            for (String fileName : fileList) {
                //���ļ��е�������
                File file = new File(path + fileName);
                String number = file.getName().substring(0, 1);
                FileReader in;

                try {

                    in = new FileReader(file);

                    for (int j = 0; j < 40; j++) {
                        for (int k = 0; k < 40; k++) {
                            sample[j][k] = in.read() - '0';  //���ֽڶ�ȡ
                        }
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //��ʼ����ŷ�������KNN���㣻
                knn.sort(sample, pixel, 40, number);
            }
            JOptionPane.showMessageDialog(panel, "Ԥ������Ϊ��" + knn.predict());

        }
        //���û���
        panel.repaint();

    }//ʶ�����

    private void Save() {
        //�����ļ�����
        if (cbItem.getSelectedItem() != null) {
            //��ȡ��ѡ����
            String selectedNumber = cbItem.getSelectedItem().toString();
            //����ǰʱ������Ϊ�ļ���
            String fileName = selectedNumber + "-" + new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date()) + ".txt";
            //�����ļ���
            {
                File file = new File(path);
                if (!file.exists() && !file.isDirectory()) if (file.mkdirs()) System.out.println(path + "�����ɹ�");
            }
            //������д���ļ�
            {
                File file = new File(path + fileName);
                try {
                    if (!file.exists()) if (file.createNewFile()) System.out.println(fileName + "�����ɹ�");

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
        //���û���
        panel.repaint();

    }//�������


    private void pixelReset() {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                pixel[i][j] = 0;
            }

        }

    }//���ظ�λ

    public void mousePressed(MouseEvent e) {
        pixelReset();

    }//��갴��

    public void mouseDragged(MouseEvent e) {
        //������д
        g.setColor(Color.WHITE);
        int x = e.getX();
        int y = e.getY();
        g.fillRect(x, y, 10, 10);

        for (int i = x; i < x + 20; i++) {
            for (int j = y; j < y + 20; j++) {
                pixel[i / 10][j / 10] = 1;
            }

        }

    }//����϶�

    public void mouseReleased(MouseEvent e) {

    }//�ͷ����
}
