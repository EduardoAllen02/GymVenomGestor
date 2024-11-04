package vista;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableColumnModel;

public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private final JTable table;
    private final Action action;
    private final JButton renderButton;
    private final JButton editButton;
    private Object editorValue;
    private boolean isButtonColumnEditor;

    public ButtonColumn(JTable table, Action action, int column) {
        this.table = table;
        this.action = action;
        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editorValue = value;
        editButton.setText((value == null) ? "" : value.toString());
        isButtonColumnEditor = true;
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        isButtonColumnEditor = false;
        return editorValue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        fireEditingStopped();
        ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "" + row);
        action.actionPerformed(event);
    }

    @Override
    public boolean stopCellEditing() {
        isButtonColumnEditor = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
