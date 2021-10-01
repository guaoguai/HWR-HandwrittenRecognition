import javax.swing.*;
import java.awt.*;
/**
 * @Title：手写识别(基于KNN)
 * @author guaoguai
 * @CreateDate: 2021-10-1
 */
public class Main extends JPanel {

    static final String PATH = ".\\";        //文件相对路径

    static final String IDENTIFY = "识别";
    static final String SAVE = "保存此样本";

    public static void main(String[] args) {
        new Main().run();//运行
    }

    private void run() {
        /*窗口*/
        JFrame jf = new JFrame();
        initJFrame(jf);

        /*控件*/
        JButton[] buttons = new JButton[2]; // 按钮
        JComboBox<String> cbItem = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"});//复选框
        addComponent(jf, buttons, cbItem);//添加控件
        jf.setVisible(true);    //显示控件

        /*监听器*/
        Listener listener = new Listener(this, cbItem);
        addListener(listener, buttons);//添加监听

    }//运行

    private void initJFrame(JFrame jf) {
        //设置标题
        jf.setTitle("手写数字识别");

        //设置窗体大小,窗体的大小也会影响精度
        jf.setSize(400, 450);

        //设置流式布局,组件按照加入的先后顺序按照设置的对齐方式从左向右排列,一行排满到下一行开始继续排列
        jf.setLayout(new FlowLayout());

        //设置此窗体是否可由用户调整大小
        jf.setResizable(false);

        //设置在关闭窗体的同时,终止程序的运行,会带来一定的便利性
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //设置窗口相对于指定组件的位置,如果组件当前未显示或者c为null,则此窗口将置于屏幕的中央
        jf.setLocationRelativeTo(null);

    }//初始化窗体,设置样式

    private void addComponent(JFrame jf, JButton[] buttons, JComboBox<String> cbItem) {

        buttons[0] = new JButton(IDENTIFY);
        buttons[1] = new JButton(SAVE);

        buttons[0].setPreferredSize(new Dimension(100, 30));
        buttons[1].setPreferredSize(new Dimension(100, 30));

        //JPanel设置大小应该用setPreferredSize,不要问,问就是适合
        this.setPreferredSize(new Dimension(400, 400));
        this.setBackground(Color.black);

        jf.add(buttons[0]);
        jf.add(buttons[1]);
        jf.add(cbItem);
        jf.add(this);

    }//添加控件

    private void addListener(Listener listener, JButton[] buttons) {

        buttons[0].addActionListener(listener);        //识别按钮
        buttons[1].addActionListener(listener);        //保存按钮

        //鼠标监听器分了两种,如果要监听鼠标移动动作,一定是加上motionListener;
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

    }//添加监听
}
