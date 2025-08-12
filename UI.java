import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {
    private Processdata todo;
    private DefaultListModel<TaskItem> listModel;
    private JList<TaskItem> taskList;
    private JTextField taskField;

    public UI() {
        todo = new Processdata();

        setTitle("To-Do List App");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        Color bgColor = new Color(240, 248, 255);
        Color btnColor = new Color(100, 149, 237);

        getContentPane().setBackground(bgColor);

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(bgColor);
        taskField = new JTextField();
        taskField.setFont(font);
        JButton addButton = createStyledButton("Add Task", btnColor, font);

        topPanel.add(taskField, BorderLayout.CENTER);
        topPanel.add(addButton, BorderLayout.EAST);

        // Task list with custom renderer
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(font);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskRenderer());
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(bgColor);
        JButton removeButton = createStyledButton("Remove Task", btnColor, font);
        JButton completeButton = createStyledButton("Mark Completed", btnColor, font);

        bottomPanel.add(removeButton);
        bottomPanel.add(completeButton);

        // Listeners
        addButton.addActionListener(e -> {
            String description = taskField.getText().trim();
            if (!description.isEmpty()) {
                todo.addList(description);
                listModel.addElement(new TaskItem(description, false));
                taskField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a task!");
            }
        });

        removeButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                todo.removeList(index);
                listModel.remove(index);
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to remove!");
            }
        });

        completeButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index != -1) {
                todo.isCompleted(index);
                TaskItem task = listModel.get(index);
                task.setCompleted(true);
                listModel.set(index, task);
            } else {
                JOptionPane.showMessageDialog(this, "Select a task to mark completed!");
            }
        });

        // Add to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Utility: Create styled buttons
    private JButton createStyledButton(String text, Color bgColor, Font font) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }

    // Inner class for task items
    static class TaskItem {
        private String description;
        private boolean completed;

        public TaskItem(String description, boolean completed) {
            this.description = description;
            this.completed = completed;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    // Custom renderer to style completed tasks
    static class TaskRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof TaskItem) {
                TaskItem task = (TaskItem) value;
                if (task.isCompleted()) {
                    setText("✅ " + task.getDescription());
                    setForeground(new Color(0, 128, 0)); // green
                    setFont(getFont().deriveFont(Font.ITALIC | Font.BOLD));
                } else {
                    setText(task.getDescription());
                    setForeground(Color.BLACK);
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
            }
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UI::new);
    }
}
