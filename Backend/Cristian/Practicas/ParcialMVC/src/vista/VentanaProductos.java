package vista;
 
import controlador.Coordinador;
import modelo.dto.ProductoDTO;
 
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
 
/**
 * Ventana del módulo de Productos – CRUD completo con tabla y formulario.
 */
public class VentanaProductos extends JFrame {
 
    private Coordinador coordinador;
 
    // ── Colores (paleta unificada DON APARATO) ────────────────────────────────
    private static final Color C_HEADER    = new Color(26, 35, 56);
    private static final Color C_LINEA     = new Color(200, 140, 30);
    private static final Color C_TITULO    = new Color(230, 160, 30);
    private static final Color C_BODY      = new Color(240, 242, 245);
    private static final Color C_FOOTER    = new Color(26, 35, 56);
    private static final Color C_CARD      = Color.WHITE;
    private static final Color C_VERDE     = new Color(40, 140, 80);
    private static final Color C_AZUL      = new Color(60, 90, 170);
    private static final Color C_AMARILLO  = new Color(190, 130, 10);
    private static final Color C_ROJO      = new Color(180, 50, 40);
    private static final Color C_VERSION   = new Color(230, 160, 30);
    private static final Color C_TBL_HDR   = new Color(30, 45, 80);
    private static final Color C_TBL_PAR   = new Color(245, 247, 252);
    private static final Color C_TBL_IMPAR = Color.WHITE;
    private static final Color C_SEL       = new Color(60, 100, 200, 60);
 
    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_LABEL  = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font F_BOLD   = new Font("SansSerif", Font.BOLD,  13);
    private static final Font F_BTN    = new Font("SansSerif", Font.BOLD,  12);
    private static final Font F_SECCION= new Font("SansSerif", Font.BOLD,  13);
 
    // ── Tabla ─────────────────────────────────────────────────────────────────
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private static final String[] COLUMNAS =
            {"ID", "Nombre", "Categoría", "Precio ($)", "Stock", "Descripción"};
 
    // ── Campos del formulario ─────────────────────────────────────────────────
    private JTextField txtNombre, txtCategoria, txtPrecio, txtStock, txtBuscar;
    private JTextArea  txtDescripcion;
    private JLabel     lblIdOculto;   // guarda el ID del producto seleccionado
 
    // ── Estado ────────────────────────────────────────────────────────────────
    private enum Modo { NINGUNO, AGREGAR, EDITAR }
    private Modo modoActual = Modo.NINGUNO;
 
    public VentanaProductos(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
        cargarTabla(null);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // CONSTRUCCIÓN DE LA UI
    // ═════════════════════════════════════════════════════════════════════════
 
    private void initComponents() {
        setTitle("SURTI MAS – Gestión de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);
 
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(C_BODY);
        setContentPane(root);
 
        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearCuerpo(), BorderLayout.CENTER);
        root.add(crearFooter(), BorderLayout.SOUTH);
    }
 
    // ── Header ────────────────────────────────────────────────────────────────
    private JPanel crearHeader() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(C_HEADER);
 
        JPanel inner = new JPanel(new BorderLayout());
        inner.setBackground(C_HEADER);
        inner.setBorder(new EmptyBorder(16, 28, 12, 28));
 
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
 
        // Botón volver
        JButton btnVolver = crearBotonHeader("← Menú Principal");
        btnVolver.addActionListener(e -> coordinador.volverDesdeProductos());
        left.add(btnVolver);
        left.add(Box.createVerticalStrut(6));
 
        JLabel lblTitulo = new JLabel("SURTI MAS");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(C_TITULO);
        left.add(lblTitulo);
 
        JLabel lblSub = new JLabel("Módulo de Gestión de Productos");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(new Color(170, 190, 215));
        left.add(Box.createVerticalStrut(3));
        left.add(lblSub);
 
        inner.add(left, BorderLayout.WEST);
        inner.add(crearBadge("v1.0", C_VERSION), BorderLayout.EAST);
 
        JSeparator sep = new JSeparator();
        sep.setForeground(C_LINEA);
        sep.setBackground(C_LINEA);
 
        wrapper.add(inner, BorderLayout.CENTER);
        wrapper.add(sep, BorderLayout.SOUTH);
        return wrapper;
    }
 
    // ── Cuerpo principal: tabla izquierda + formulario derecha ────────────────
    private JSplitPane crearCuerpo() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelTabla(), crearPanelFormulario());
        split.setDividerLocation(700);
        split.setDividerSize(5);
        split.setBorder(null);
        split.setBackground(C_BODY);
        return split;
    }
 
    // ── Panel izquierdo: buscador + tabla ─────────────────────────────────────
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(C_BODY);
        panel.setBorder(new EmptyBorder(16, 20, 12, 10));
 
        // Barra de búsqueda
        JPanel barraTop = new JPanel(new BorderLayout(8, 0));
        barraTop.setOpaque(false);
 
        txtBuscar = crearTextField("Buscar por nombre…");
        JButton btnBuscar = crearBoton("🔍 Buscar", C_AZUL, 120, 34);
        JButton btnMostrarTodos = crearBoton("↺ Todos", new Color(90, 90, 100), 100, 34);
 
        btnBuscar.addActionListener(e -> cargarTabla(txtBuscar.getText().trim()));
        btnMostrarTodos.addActionListener(e -> { txtBuscar.setText(""); cargarTabla(null); });
        txtBuscar.addActionListener(e -> cargarTabla(txtBuscar.getText().trim()));
 
        JPanel botonesTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        botonesTop.setOpaque(false);
        botonesTop.add(btnBuscar);
        botonesTop.add(btnMostrarTodos);
 
        barraTop.add(txtBuscar, BorderLayout.CENTER);
        barraTop.add(botonesTop, BorderLayout.EAST);
 
        // Tabla
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        tabla = new JTable(modeloTabla) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(C_SEL);
                    c.setForeground(new Color(20, 30, 60));
                } else {
                    c.setBackground(row % 2 == 0 ? C_TBL_IMPAR : C_TBL_PAR);
                    c.setForeground(new Color(40, 50, 70));
                }
                return c;
            }
        };
        tabla.setFont(F_LABEL);
        tabla.setRowHeight(30);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setFont(F_BOLD);
        tabla.getTableHeader().setBackground(C_TBL_HDR);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
 
        // Anchos de columna
        int[] anchos = {50, 200, 120, 110, 60, 160};
        for (int i = 0; i < anchos.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
 
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarFormularioDesdeTabla();
        });
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        scroll.getViewport().setBackground(Color.WHITE);
 
        // Botones inferiores de acción sobre tabla
        JPanel botonesTbl = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botonesTbl.setOpaque(false);
        JButton btnAgregar  = crearBoton("＋ Agregar",   C_VERDE,    120, 36);
        JButton btnEditar   = crearBoton("✏ Editar",     C_AMARILLO, 110, 36);
        JButton btnEliminar = crearBoton("🗑 Eliminar",  C_ROJO,     110, 36);
 
        btnAgregar.addActionListener(e  -> activarModoAgregar());
        btnEditar.addActionListener(e   -> activarModoEditar());
        btnEliminar.addActionListener(e -> accionEliminar());
 
        botonesTbl.add(btnAgregar);
        botonesTbl.add(btnEditar);
        botonesTbl.add(btnEliminar);
 
        panel.add(barraTop,   BorderLayout.NORTH);
        panel.add(scroll,     BorderLayout.CENTER);
        panel.add(botonesTbl, BorderLayout.SOUTH);
        return panel;
    }
 
    // ── Panel derecho: formulario ─────────────────────────────────────────────
    private JPanel crearPanelFormulario() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(C_BODY);
        outer.setBorder(new EmptyBorder(16, 10, 12, 20));
 
        JPanel card = new JPanel();
        card.setBackground(C_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 210, 225)),
                        "Detalle del Producto", TitledBorder.LEFT, TitledBorder.TOP,
                        F_SECCION, new Color(40, 55, 90)),
                new EmptyBorder(10, 14, 14, 14)));
 
        // ID oculto
        lblIdOculto = new JLabel("0");
        lblIdOculto.setVisible(false);
        card.add(lblIdOculto);
 
        // Campos
        txtNombre      = crearTextField("Ej: Lavadora LG 14kg");
        txtCategoria   = crearTextField("Ej: Lavadoras");
        txtPrecio      = crearTextField("Ej: 1800000");
        txtStock       = crearTextField("Ej: 10");
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setFont(F_LABEL);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 225)),
                new EmptyBorder(5, 8, 5, 8)));
        txtDescripcion.setBackground(new Color(250, 251, 253));
 
        card.add(campoCon("Nombre *",        txtNombre));
        card.add(Box.createVerticalStrut(10));
        card.add(campoCon("Categoría *",     txtCategoria));
        card.add(Box.createVerticalStrut(10));
        card.add(campoCon("Precio ($) *",    txtPrecio));
        card.add(Box.createVerticalStrut(10));
        card.add(campoCon("Stock *",         txtStock));
        card.add(Box.createVerticalStrut(10));
 
        JPanel descPanel = new JPanel(new BorderLayout(0, 4));
        descPanel.setOpaque(false);
        descPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        JLabel lDesc = new JLabel("Descripción");
        lDesc.setFont(F_LABEL);
        lDesc.setForeground(new Color(60, 70, 90));
        descPanel.add(lDesc, BorderLayout.NORTH);
        descPanel.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);
        card.add(descPanel);
        card.add(Box.createVerticalStrut(18));
 
        // Botones del formulario
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
 
        JButton btnGuardar   = crearBoton("💾 Guardar",   C_VERDE,  130, 38);
        JButton btnCancelar  = crearBoton("✖ Cancelar",   C_ROJO,   120, 38);
        JButton btnLimpiarF  = crearBoton("↺ Limpiar",    new Color(90,90,100), 110, 38);
 
        btnGuardar.addActionListener(e  -> accionGuardar());
        btnCancelar.addActionListener(e -> cancelarFormulario());
        btnLimpiarF.addActionListener(e -> limpiarFormulario());
 
        btnPanel.add(btnGuardar);
        btnPanel.add(btnCancelar);
        btnPanel.add(btnLimpiarF);
 
        card.add(btnPanel);
 
        // Nota de campos obligatorios
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblNota.setForeground(new Color(140, 150, 170));
        lblNota.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(Box.createVerticalStrut(6));
        card.add(lblNota);
 
        bloquearFormulario(true);
 
        outer.add(card, BorderLayout.CENTER);
        return outer;
    }
 
    // ── Footer ────────────────────────────────────────────────────────────────
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(C_FOOTER);
        footer.setPreferredSize(new Dimension(0, 32));
        JLabel lbl = new JLabel("© 2024 SURTI MAS – Todos los derechos reservados");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(140, 160, 190));
        footer.add(lbl);
        return footer;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA DE ACCIONES
    // ═════════════════════════════════════════════════════════════════════════
 
    /** Carga (o recarga) la tabla desde BD. Si texto es null/vacío trae todos. */
    private void cargarTabla(String filtro) {
        modeloTabla.setRowCount(0);
        try {
            List<ProductoDTO> lista = coordinador.listarProductos(filtro);
            for (ProductoDTO p : lista) {
                modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getCategoria(),
                        Coordinador.formatearMoneda(p.getPrecio()),
                        p.getStock(),
                        p.getDescripcion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar productos:\n" + ex.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    /** Rellena el formulario con la fila seleccionada en la tabla. */
    private void cargarFormularioDesdeTabla() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        lblIdOculto.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
        // precio viene formateado con $; lo limpiamos para edición
        String precioStr = modeloTabla.getValueAt(fila, 3).toString()
                .replace("$", "").replace(".", "").replace(",", ".").trim();
        txtPrecio.setText(precioStr);
        txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtDescripcion.setText(modeloTabla.getValueAt(fila, 5) != null
                ? modeloTabla.getValueAt(fila, 5).toString() : "");
        // Formulario en modo lectura hasta que se pulse Editar
        if (modoActual == Modo.NINGUNO) bloquearFormulario(true);
    }
 
    private void activarModoAgregar() {
        modoActual = Modo.AGREGAR;
        limpiarFormulario();
        bloquearFormulario(false);
        tabla.clearSelection();
        txtNombre.requestFocus();
    }
 
    private void activarModoEditar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un producto de la tabla para editar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modoActual = Modo.EDITAR;
        bloquearFormulario(false);
        txtNombre.requestFocus();
    }
 
    private void accionGuardar() {
        // Validar campos obligatorios
        String nombre = txtNombre.getText().trim();
        String cat    = txtCategoria.getText().trim();
        String precStr= txtPrecio.getText().trim().replace(",", ".");
        String stkStr = txtStock.getText().trim();
 
        if (nombre.isEmpty() || cat.isEmpty() || precStr.isEmpty() || stkStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos obligatorios (*)",
                    "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        double precio;
        int    stock;
        try {
            precio = Double.parseDouble(precStr);
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El precio debe ser un número positivo.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            stock = Integer.parseInt(stkStr);
            if (stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El stock debe ser un número entero positivo.", "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }
 
        int id = Integer.parseInt(lblIdOculto.getText());
        String desc = txtDescripcion.getText().trim();
        ProductoDTO p = new ProductoDTO(id, nombre, cat, desc, precio, stock);
 
        try {
            if (modoActual == Modo.AGREGAR) {
                coordinador.agregarProducto(p);
                JOptionPane.showMessageDialog(this,
                        "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                coordinador.editarProducto(p);
                JOptionPane.showMessageDialog(this,
                        "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            cargarTabla(null);
            cancelarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar:\n" + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un producto de la tabla para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombre = modeloTabla.getValueAt(fila, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar el producto:\n\"" + nombre + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
 
        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        try {
            coordinador.eliminarProducto(id);
            JOptionPane.showMessageDialog(this,
                    "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(null);
            cancelarFormulario();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar:\n" + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void cancelarFormulario() {
        modoActual = Modo.NINGUNO;
        limpiarFormulario();
        bloquearFormulario(true);
        tabla.clearSelection();
    }
 
    private void limpiarFormulario() {
        lblIdOculto.setText("0");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtDescripcion.setText("");
    }
 
    /** Habilita o deshabilita la edición del formulario. */
    private void bloquearFormulario(boolean bloquear) {
        Color bg = bloquear ? new Color(235, 237, 242) : new Color(250, 251, 253);
        txtNombre.setEditable(!bloquear);      txtNombre.setBackground(bg);
        txtCategoria.setEditable(!bloquear);   txtCategoria.setBackground(bg);
        txtPrecio.setEditable(!bloquear);      txtPrecio.setBackground(bg);
        txtStock.setEditable(!bloquear);       txtStock.setBackground(bg);
        txtDescripcion.setEditable(!bloquear); txtDescripcion.setBackground(bg);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // FÁBRICA DE COMPONENTES
    // ═════════════════════════════════════════════════════════════════════════
 
    private JPanel campoCon(String labelText, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(F_LABEL);
        lbl.setForeground(new Color(60, 70, 90));
        p.add(lbl, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }
 
    private JTextField crearTextField(String placeholder) {
        JTextField tf = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(160, 170, 185));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(placeholder,
                            getInsets().left + 2,
                            (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
                    g2.dispose();
                }
            }
        };
        tf.setFont(F_LABEL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 225)),
                new EmptyBorder(5, 8, 5, 8)));
        tf.setBackground(new Color(250, 251, 253));
        tf.setForeground(new Color(30, 40, 60));
        return tf;
    }
 
    private JButton crearBoton(String texto, Color color, int w, int h) {
        JButton btn = new JButton(texto) {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
                setOpaque(false); setContentAreaFilled(false);
                setBorderPainted(false); setFocusPainted(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? color.darker() : color);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(F_BTN);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(w, h));
        return btn;
    }
 
    private JButton crearBotonHeader(String texto) {
        JButton btn = new JButton(texto) {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                });
                setOpaque(false); setContentAreaFilled(false);
                setBorderPainted(false); setFocusPainted(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? new Color(60, 75, 110) : new Color(45, 58, 90));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(new Color(200, 215, 240));
        btn.setPreferredSize(new Dimension(155, 28));
        return btn;
    }
 
    private JLabel crearBadge(String texto, Color color) {
        JLabel lbl = new JLabel(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        lbl.setForeground(Color.WHITE);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setPreferredSize(new Dimension(55, 28));
        lbl.setOpaque(false);
        return lbl;
    }
}