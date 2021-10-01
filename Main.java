import javax.swing.*;
import java.awt.*;
/**
 * @Title����дʶ��(����KNN)
 * @author guaoguai
 * @CreateDate: 2021-10-1
 */
public class Main extends JPanel {

    static final String PATH = ".\\";        //�ļ����·��

    static final String IDENTIFY = "ʶ��";
    static final String SAVE = "���������";

    public static void main(String[] args) {
        new Main().run();//����
    }

    private void run() {
        /*����*/
        JFrame jf = new JFrame();
        initJFrame(jf);

        /*�ؼ�*/
        JButton[] buttons = new JButton[2]; // ��ť
        JComboBox<String> cbItem = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});//��ѡ��
        addComponent(jf, buttons, cbItem);//��ӿؼ�
        jf.setVisible(true);    //��ʾ�ؼ�

        /*������*/
        Listener listener = new Listener(this, cbItem);
        addListener(listener, buttons);//��Ӽ���

    }//����

    private void initJFrame(JFrame jf) {
        //���ñ���
        jf.setTitle("��д����ʶ��");

        //���ô����С,����Ĵ�СҲ��Ӱ�쾫��
        jf.setSize(400, 450);

        //������ʽ����,������ռ�����Ⱥ�˳�������õĶ��뷽ʽ������������,һ����������һ�п�ʼ��������
        jf.setLayout(new FlowLayout());

        //���ô˴����Ƿ�����û�������С
        jf.setResizable(false);

        //�����ڹرմ����ͬʱ,��ֹ���������,�����һ���ı�����
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //���ô��������ָ�������λ��,��������ǰδ��ʾ����cΪnull,��˴��ڽ�������Ļ������
        jf.setLocationRelativeTo(null);

    }//��ʼ������,������ʽ

    private void addComponent(JFrame jf, JButton[] buttons, JComboBox<String> cbItem) {

        buttons[0] = new JButton(IDENTIFY);
        buttons[1] = new JButton(SAVE);

        buttons[0].setPreferredSize(new Dimension(100, 30));
        buttons[1].setPreferredSize(new Dimension(100, 30));

        //JPanel���ô�СӦ����setPreferredSize,��Ҫ��,�ʾ����ʺ�
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(Color.black);

        jf.add(buttons[0]);
        jf.add(buttons[1]);
        jf.add(cbItem);
        jf.add(this);

    }//��ӿؼ�

    private void addListener(Listener listener, JButton[] buttons) {

        buttons[0].addActionListener(listener);        //ʶ��ť
        buttons[1].addActionListener(listener);        //���水ť

        //����������������,���Ҫ��������ƶ�����,һ���Ǽ���motionListener;
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

    }//��Ӽ���
}
