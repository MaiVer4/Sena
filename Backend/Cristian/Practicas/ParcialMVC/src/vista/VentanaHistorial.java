package vista;
 
import controlador.Coordinador;
import modelo.dto.CompraDTO;
 
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
 
/**
 * Ventana del historial de compras – muestra todas las ventas registradas.
 */
public class VentanaHistorial extends JFrame {
 
    private Coordinador coordinador;
 
    // ── Paleta de colores ────────────────────────────────────────────────────
    private static final Color C_HEADER   = new Color(26,  35,  56);
    private static final Color C_LINEA    = new Color(200, 140, 30);
    private static final Color C_TITULO   = new Color(230, 160, 30);
    private static final Color C_BODY     = new Color(240, 242, 245);
    private static final Color C_FOOTER   = new Color(26,  35,  56);
    private static final Color C_VERSION  = new Color(230, 160, 30);
    private static final Color C_AZUL     = new Color(60,  90,  170);
    private static final Color C_VERDE    = new Color(40,  140, 80);
    private static final Color C_GRIS     = new Color(90,  90,  100);
    private static final Color C_NARANJA  = new Color(180, 100, 20);
    private static final Color C_TBL_HDR  = new Color(30,  45,  80);
    private static final Color C_TBL_PAR  = new Color(245, 247, 252);
    private static final Color C_SEL      = new Color(200, 140, 30, 50);
 
    private static final Font F_LABEL    = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font F_BOLD     = new Font("SansSerif", Font.BOLD,  12);
    private static final Font F_BTN      = new Font("SansSerif", Font.BOLD,  12);
    private static final Font F_RESUMEN  = new Font("SansSerif", Font.BOLD,  13);
 
    // ── Tabla ─────────────────────────────────────────────────────────────────
    private JTable tabla;
    private DefaultTableModel modeloTabla;
 
    private static final String[] COLUMNAS = {
        "#", "Fecha y Hora", "Cliente", "Cédula", "Tipo",
        "Producto", "Cant.", "Valor Unit.", "Total Bruto",
        "Descuento", "Total Real"
    };
 
    // ── Filtros ───────────────────────────────────────────────────────────────
    private JTextField txtBuscar;
 
    // ── Totalizadores ─────────────────────────────────────────────────────────
    private JLabel lblTotalCompras, lblTotalVendido, lblTotalDescuentos, lblTotalReal;
 
    public VentanaHistorial(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
        cargarTabla(null);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // CONSTRUCCIÓN DE UI
    // ═════════════════════════════════════════════════════════════════════════
 
    private void initComponents() {
        setTitle("SURTI MAS – Historial de Compras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setResizable(true);
 
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(C_BODY);
        setContentPane(root);
 
        root.add(crearHeader(),    BorderLayout.NORTH);
        root.add(crearCuerpo(),    BorderLayout.CENTER);
        root.add(crearFooter(),    BorderLayout.SOUTH);
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
 
        JButton btnVolver = crearBotonHeader("← Menú Principal");
        btnVolver.addActionListener(e -> coordinador.volverDesdeHistorial());
        left.add(btnVolver);
        left.add(Box.createVerticalStrut(6));
 
        JLabel lblTitulo = new JLabel("SURTI MAS");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(C_TITULO);
        left.add(lblTitulo);
 
        JLabel lblSub = new JLabel("Historial de Compras");
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
        wrapper.add(sep,   BorderLayout.SOUTH);
        return wrapper;
    }
 
    // ── Cuerpo principal ──────────────────────────────────────────────────────
    private JPanel crearCuerpo() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(C_BODY);
        panel.setBorder(new EmptyBorder(16, 20, 10, 20));
 
        panel.add(crearBarraFiltros(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(),   BorderLayout.CENTER);
        panel.add(crearPanelResumen(), BorderLayout.SOUTH);
 
        return panel;
    }
 
    // ── Barra de filtros ──────────────────────────────────────────────────────
    private JPanel crearBarraFiltros() {
        JPanel barra = new JPanel(new BorderLayout(10, 0));
        barra.setOpaque(false);
        barra.setBorder(new EmptyBorder(0, 0, 6, 0));
 
        txtBuscar = crearTextField("Buscar por cliente, cédula o producto…");
 
        JButton btnBuscar  = crearBoton("🔍 Buscar",      C_AZUL,   120, 34);
        JButton btnTodos   = crearBoton("↺ Todos",         C_GRIS,   100, 34);
        JButton btnRefresh = crearBoton("⟳ Actualizar",   C_VERDE,  120, 34);
 
        btnBuscar.addActionListener(e  -> cargarTabla(txtBuscar.getText().trim()));
        btnTodos.addActionListener(e   -> { txtBuscar.setText(""); cargarTabla(null); });
        btnRefresh.addActionListener(e -> { txtBuscar.setText(""); cargarTabla(null); });
        txtBuscar.addActionListener(e  -> cargarTabla(txtBuscar.getText().trim()));
 
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        botones.setOpaque(false);
        botones.add(btnBuscar);
        botones.add(btnTodos);
        botones.add(btnRefresh);
 
        barra.add(txtBuscar, BorderLayout.CENTER);
        barra.add(botones,   BorderLayout.EAST);
        return barra;
    }
 
    // ── Tabla de compras ──────────────────────────────────────────────────────
    private JScrollPane crearPanelTabla() {
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        tabla = new JTable(modeloTabla) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(C_SEL);
                    c.setForeground(new Color(80, 50, 10));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : C_TBL_PAR);
                    c.setForeground(new Color(40, 50, 70));
                }
                // Alinear columnas numéricas a la derecha
                if (c instanceof JLabel && col >= 6) {
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                }
                // Colorear columna descuento
                if (col == 9 && !isRowSelected(row)) {
                    String val = getValueAt(row, col) != null
                            ? getValueAt(row, col).toString() : "";
                    if (!val.equals("Sin descuento")) {
                        c.setForeground(C_NARANJA);
                        c.setFont(F_BOLD);
                    }
                }
                // Total real en verde
                if (col == 10 && !isRowSelected(row)) {
                    c.setForeground(C_VERDE);
                    c.setFont(F_BOLD);
                }
                return c;
            }
        };
        tabla.setFont(F_LABEL);
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setFont(F_BOLD);
        tabla.getTableHeader().setBackground(C_TBL_HDR);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
 
        // Anchos de columnas
        int[] anchos = {40, 140, 150, 100, 55, 160, 45, 110, 110, 120, 110};
        for (int i = 0; i < anchos.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }
 
    // ── Panel de resumen / totalizadores ──────────────────────────────────────
    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 14, 0));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(8, 0, 0, 0));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
 
        lblTotalCompras    = new JLabel("0");
        lblTotalVendido    = new JLabel("$ 0");
        lblTotalDescuentos = new JLabel("$ 0");
        lblTotalReal       = new JLabel("$ 0");
 
        panel.add(crearTarjetaResumen("Total Compras",       lblTotalCompras,    new Color(60, 90, 170)));
        panel.add(crearTarjetaResumen("Total Vendido",       lblTotalVendido,    new Color(30, 100, 70)));
        panel.add(crearTarjetaResumen("Total Descuentos",    lblTotalDescuentos, new Color(180, 100, 20)));
        panel.add(crearTarjetaResumen("Total Real Cobrado",  lblTotalReal,       new Color(40, 120, 60)));
 
        return panel;
    }
 
    private JPanel crearTarjetaResumen(String titulo, JLabel lblValor, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 4));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(10, 14, 10, 14)));
 
        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTit.setForeground(new Color(100, 110, 130));
 
        lblValor.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValor.setForeground(color);
 
        card.add(lblTit,   BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
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
    // LÓGICA
    // ═════════════════════════════════════════════════════════════════════════
 
    /** Carga (o recarga) la tabla desde BD y actualiza los totalizadores. */
    public void cargarTabla(String filtro) {
        modeloTabla.setRowCount(0);
        try {
            List<CompraDTO> lista = coordinador.listarCompras(filtro);
 
            double sumaBruto      = 0;
            double sumaDescuento  = 0;
            double sumaReal       = 0;
 
            for (CompraDTO c : lista) {
                String descStr = c.tieneDescuento()
                        ? c.getPorcentajeDescuentoEntero() + "% → "
                          + Coordinador.formatearMoneda(c.getValorDescuento())
                        : "Sin descuento";
 
                modeloTabla.addRow(new Object[]{
                        c.getId(),
                        c.getFechaHoraStr(),
                        c.getClienteNombreCompleto(),
                        c.getClienteCedula(),
                        c.getClienteTipo(),
                        c.getProductoNombre(),
                        c.getCantidad(),
                        Coordinador.formatearMoneda(c.getValorUnitario()),
                        Coordinador.formatearMoneda(c.getTotalBruto()),
                        descStr,
                        Coordinador.formatearMoneda(c.getTotalReal())
                });
 
                sumaBruto     += c.getTotalBruto();
                sumaDescuento += c.getValorDescuento();
                sumaReal      += c.getTotalReal();
            }
 
            // Actualizar totalizadores
            lblTotalCompras.setText(String.valueOf(lista.size()));
            lblTotalVendido.setText(Coordinador.formatearMoneda(sumaBruto));
            lblTotalDescuentos.setText(Coordinador.formatearMoneda(sumaDescuento));
            lblTotalReal.setText(Coordinador.formatearMoneda(sumaReal));
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar el historial:\n" + ex.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // FÁBRICA DE COMPONENTES
    // ═════════════════════════════════════════════════════════════════════════
 
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
 