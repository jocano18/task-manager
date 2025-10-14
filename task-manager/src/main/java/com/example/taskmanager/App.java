package com.example.taskmanager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class App extends JFrame {

    private TaskDAO dao;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskJList;

    public App() {
        dao = new TaskDAO();
        initUI();
        loadTasks();
    }

    private void initUI() {
        setTitle("Task Manager - Creado por Juan David Ocaño Huertas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(root);

        listModel = new DefaultListModel<>();
        taskJList = new JList<>(listModel);
        taskJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(taskJList);
        root.add(scroll, BorderLayout.CENTER);

        // Right panel with buttons and details
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setPreferredSize(new Dimension(260, 0));

        JButton btnRefresh = new JButton("Refrescar");
        btnRefresh.addActionListener(e -> loadTasks());
        right.add(btnRefresh);
        right.add(Box.createRigidArea(new Dimension(0,10)));

        JButton btnAdd = new JButton("Agregar tarea");
        btnAdd.addActionListener(e -> showAddDialog());
        right.add(btnAdd);
        right.add(Box.createRigidArea(new Dimension(0,10)));

        JButton btnComplete = new JButton("Marcar completada");
        btnComplete.addActionListener(e -> markSelectedCompleted());
        right.add(btnComplete);
        right.add(Box.createRigidArea(new Dimension(0,10)));

        JButton btnDelete = new JButton("Eliminar tarea");
        btnDelete.addActionListener(e -> deleteSelected());
        right.add(btnDelete);
        right.add(Box.createVerticalGlue());

        root.add(right, BorderLayout.EAST);
    }

    private void loadTasks() {
        listModel.clear();
        List<Task> tasks = dao.getAll();
        for (Task t : tasks) listModel.addElement(t);
    }

    private void showAddDialog() {
        JTextField titleField = new JTextField();
        JTextArea descArea = new JTextArea(5, 20);
        String[] priorities = {"ALTA", "MEDIA", "BAJA"};
        JComboBox<String> prioBox = new JComboBox<>(priorities);

        JPanel panel = new JPanel(new BorderLayout(5,5));
        JPanel top = new JPanel(new GridLayout(0,1,5,5));
        top.add(new JLabel("Título:"));
        top.add(titleField);
        top.add(new JLabel("Prioridad:"));
        top.add(prioBox);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JLabel("Descripción:"), BorderLayout.CENTER);
        panel.add(new JScrollPane(descArea), BorderLayout.SOUTH);

        int res = JOptionPane.showConfirmDialog(this, panel, "Agregar tarea", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String title = titleField.getText().trim();
            String desc = descArea.getText().trim();
            String prio = (String) prioBox.getSelectedItem();
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El título no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dao.insert(new Task(title, desc, prio));
            loadTasks();
        }
    }

    private void markSelectedCompleted() {
        Task sel = taskJList.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una tarea primero.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        dao.markCompleted(sel.getId());
        loadTasks();
    }

    private void deleteSelected() {
        Task sel = taskJList.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una tarea primero.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this, "Eliminar la tarea seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            dao.delete(sel.getId());
            loadTasks();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            App ex = new App();
            ex.setVisible(true);
        });
    }
}
