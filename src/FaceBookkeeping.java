import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FaceBookkeeping extends JFrame {
    private static String income, expenses;
    private Font font = new Font("SanSerif", Font.BOLD, 14);
    private static JTextArea JTextArea = new JTextArea();
    private JButton button = new JButton("Ввести доходы");
    private JButton button2 = new JButton("Ввести расходы");
    private JButton button3 = new JButton("Диапозон расходов");
    private JButton button4 = new JButton("Диапозон доходов");
    private JButton button5 = new JButton("Посчитать разницу");
    private WorkingSide work = new WorkingSide();

    public static void main(String[] args) {
        new FaceBookkeeping();
    }

    public FaceBookkeeping() {
        super("Бухгалтерия");
        setLayout(null);
        JTextArea.setBounds(280, 50, 470, 460);
        JTextArea.setFont(font);
        JTextArea.setEditable(false);
        add(JTextArea);
//        JScrollPane jScrollPane = new JScrollPane(JTextArea);
//        jScrollPane.setPreferredSize(new Dimension(200, 470));
//        jScrollPane.setViewportView(JTextArea);
//        jScrollPane.setBounds(280, 50, 470, 460);
//        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        JPanel panel = new JPanel();
//        panel.add(jScrollPane);
//        getContentPane().add(panel, BorderLayout.SOUTH);

        button.setBounds(55, 70, 200, 30);
        button.setFont(font);
        add(button);

        button2.setBounds(55, 160, 200, 30);
        button2.setFont(font);
        add(button2);

        button3.setBounds(55, 260, 200, 30);
        button3.setFont(font);
        add(button3);

        button4.setBounds(55, 360, 200, 30);
        button4.setFont(font);
        add(button4);

        button5.setBounds(55, 450, 200, 30);
        button5.setFont(font);
        add(button5);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                income = JOptionPane.showInputDialog(null, "Введите доход");
                swingUtil("Вы ввели " + income);
                Integer income = Integer.parseInt(FaceBookkeeping.income);
                work.insertToBDIncome(income);
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expenses = JOptionPane.showInputDialog(null, "Введите расходы");
                swingUtil("Вы ввели " + expenses);
                Integer exp = Integer.parseInt(expenses);
                work.insertToBDExpenses(exp);
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = JOptionPane.showInputDialog(null, "Введите начальную дату ГГГГ-ММ-ДД");
                String to = JOptionPane.showInputDialog(null, "Введите конечную дату ГГГГ-ММ-ДД");
                if (from.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") && to.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
                    work.selectToBDExpenses(from, to);
                    for (int i = 0; i < WorkingSide.integers.size(); i++) {
                        swingUtil("Расходы: " + WorkingSide.integers.get(i));
                    }
                    WorkingSide.integers.clear();
                }else
                    swingUtil("Ошибка! Введите строго ГГГГ-ММ-ДД");
            }
        });
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = JOptionPane.showInputDialog(null, "Введите начальную дату ГГГГ-ММ-ДД");
                String to = JOptionPane.showInputDialog(null, "Введите конечную дату ГГГГ-ММ-ДД");
                if (from.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") && to.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
                    work.selectToBDIncome(from, to);
                    for (int i = 0; i < WorkingSide.integers.size(); i++) {
                        swingUtil("Доходы: " + WorkingSide.integers.get(i));
                    }
                    WorkingSide.integers.clear();
                }else
                    swingUtil("Ошибка! Введите дату строго ГГГГ-ММ-ДД");
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String from = JOptionPane.showInputDialog(null, "Введите начальную дату ГГГГ-ММ-ДД");
                String to = JOptionPane.showInputDialog(null, "Введите конечную дату ГГГГ-ММ-ДД");
                if (from.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}") && to.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")){
                    int result = work.calculation(from, to);
                    if (result > 0)
                        swingUtil("Остаток ваших доходов: " + result);
                    else if (result < 0)
                        swingUtil("Расходы больше доходов:" + result);
                }else
                    swingUtil("Ошибка! Введите дату строго ГГГГ-ММ-ДД");
            }
        });
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    protected static void swingUtil(String value) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JTextArea.append(value + " рублей" + "\n");
            }
        });
    }
}
