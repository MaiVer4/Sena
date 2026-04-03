
package vista;
 
import controlador.Coordinador;
import controlador.Coordinador.ResultadoVenta;
import modelo.dto.CompraDTO;
import modelo.dto.ItemCarritoDTO;
import modelo.dto.ProductoDTO;
 
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
 
public class VentanaPrincipalM extends JFrame {
 
    private Coordinador coordinador;
 
    // ── Colores ───────────────────────────────────────────────────────────────
    private static final Color C_HEADER      = new Color(26,  35,  56);
    private static final Color C_LINEA       = new Color(200, 140, 30);
    private static final Color C_TITULO      = new Color(230, 160, 30);
    private static final Color C_BODY        = new Color(240, 242, 245);
    private static final Color C_FOOTER      = new Color(26,  35,  56);
    private static final Color C_BTN_COMPRA  = new Color(40,  140, 80);
    private static final Color C_BTN_LIMPIAR = new Color(180, 50,  40);
    private static final Color C_VERSION     = new Color(230, 160, 30);
    private static final Color C_BADGE_A     = new Color(200, 50,  40);
    private static final Color C_BADGE_B     = new Color(40,  130, 60);
    private static final Color C_BADGE_C     = new Color(40,  100, 180);
    private static final Color C_BADGE_SIN   = new Color(90,  90,  90);
    private static final Color C_TBL_HDR     = new Color(30,  45,  80);
    private static final Color C_TBL_PAR     = new Color(245, 247, 252);
    private static final Color C_SEL         = new Color(200, 140, 30,  50);
    private static final Color C_VERDE       = new Color(40,  140, 80);
    private static final Color C_NARANJA     = new Color(160, 90,  10);
    private static final Color C_CARRITO_HDR = new Color(30,  100, 55);
    private static final Color C_AZUL        = new Color(60,  90,  170);
 
    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_LABEL   = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font F_SECTION = new Font("SansSerif", Font.BOLD,  13);
    private static final Font F_BTN     = new Font("SansSerif", Font.BOLD,  13);
    private static final Font F_TBL     = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font F_TBL_HDR = new Font("SansSerif", Font.BOLD,  12);
 
    // ── Campos cliente ────────────────────────────────────────────────────────
    private JTextField txtNombre, txtApellido, txtEdad, txtTelefono, txtCedula;
    private JComboBox<String> cmbTipo;
 
    // ── Buscador de producto + carrito ────────────────────────────────────────
    private JTextField  txtBuscadorProducto, txtCantidad;
    private JLabel      lblStockDisponible, lblPrecioUnitario;
    private ProductoDTO productoSeleccionado       = null;
    private String      nombreProductoSeleccionado = "";
    private boolean     seleccionando              = false;
 
    // ── Carrito (ítems en memoria) ────────────────────────────────────────────
    private final List<ItemCarritoDTO> carrito = new ArrayList<>();
    private DefaultTableModel modeloCarrito;
    private JLabel lblCarritoTotal, lblCarritoDesc, lblCarritoReal;
    private static final String[] COL_CARRITO =
            {"Producto", "Precio Unit.", "Cant.", "Subtotal", ""};
 
    // ── Panel resultado ───────────────────────────────────────────────────────
    private JPanel panelResultado;
    private JLabel lblResCliente, lblResTipo, lblResTotalBruto,
                   lblResDescuento, lblResTotalReal, lblResItems;
 
    // ── Historial expandible ──────────────────────────────────────────────────
    private JPanel        panelHistorial;
    private DefaultTableModel modeloHist;
    private JTable        tablaHist;
    private JLabel        lblTotalCompras, lblTotalVendido, lblTotalDescuentos, lblTotalReal;
    private static final String[] COL_HIST =
            {"", "#Venta", "Fecha y Hora", "Cliente", "Ítems", "Total Bruto", "Descuento", "Total Real"};
 
    // ─────────────────────────────────────────────────────────────────────────
 
    public VentanaPrincipalM(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // INIT
    // ═════════════════════════════════════════════════════════════════════════
    private void initComponents() {
        setTitle("DON APARATO – Sistema de Ventas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1350, 860);
        setLocationRelativeTo(null);
        setResizable(true);
 
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(C_BODY);
        setContentPane(root);
 
        root.add(crearHeader(),  BorderLayout.NORTH);
        root.add(crearCuerpo(),  BorderLayout.CENTER);
        root.add(crearFooter(),  BorderLayout.SOUTH);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // HEADER
    // ═════════════════════════════════════════════════════════════════════════
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
        btnVolver.addActionListener(e -> coordinador.volverAlInicio());
 
        JLabel lblTitulo = new JLabel("DON APARATO");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(C_TITULO);
 
        JLabel lblSub = new JLabel("Sistema de Gestión de Ventas");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(new Color(170, 190, 215));
 
        left.add(btnVolver);
        left.add(Box.createVerticalStrut(6));
        left.add(lblTitulo);
        left.add(Box.createVerticalStrut(4));
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
 
    // ═════════════════════════════════════════════════════════════════════════
    // CUERPO — SplitPane: formulario arriba | historial abajo
    // ═════════════════════════════════════════════════════════════════════════
    private JSplitPane crearCuerpo() {
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                crearPanelVenta(), crearPanelHistorial());
        split.setDividerLocation(450);
        split.setDividerSize(6);
        split.setResizeWeight(0.5);
        split.setBorder(null);
        return split;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // PANEL SUPERIOR — cliente + buscador + carrito + resultado + botones
    // ═════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelVenta() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(C_BODY);
        panel.setBorder(new EmptyBorder(12, 20, 6, 20));
 
        // Izquierda: datos cliente + buscador
        JPanel izq = new JPanel();
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));
        izq.setOpaque(false);
        izq.add(crearSeccionCliente());
        izq.add(Box.createVerticalStrut(8));
        izq.add(crearSeccionBuscador());
        izq.add(Box.createVerticalStrut(8));
 
        // Panel resultado (oculto inicialmente)
        panelResultado = crearPanelResultado();
        panelResultado.setVisible(false);
        izq.add(panelResultado);
        izq.add(Box.createVerticalStrut(8));
        izq.add(crearPanelBotones());
 
        // Derecha: carrito
        panel.add(izq,               BorderLayout.CENTER);
        panel.add(crearPanelCarrito(), BorderLayout.EAST);
        return panel;
    }
 
    // ── Datos del cliente ─────────────────────────────────────────────────────
    private JPanel crearSeccionCliente() {
        JPanel panel = crearPanelSeccion("Datos del Cliente");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
 
        JPanel fila1 = new JPanel(new GridLayout(1, 4, 12, 0));
        fila1.setOpaque(false);
        fila1.add(crearCampo("Nombre:",    txtNombre   = crearTF("")));
        fila1.add(crearCampo("Apellido:",  txtApellido = crearTF("Ej: García")));
        fila1.add(crearCampo("Edad:",      txtEdad     = crearTF("Ej: 35")));
        fila1.add(crearCampo("Teléfono:",  txtTelefono = crearTF("Ej: 3001234567")));
 
        JPanel fila2 = new JPanel(new GridLayout(1, 4, 12, 0));
        fila2.setOpaque(false);
        fila2.setBorder(new EmptyBorder(8, 0, 0, 0));
        fila2.add(crearCampo("C.C. (Cédula):", txtCedula = crearTF("Ej: 1000123456")));
        String[] tipos = {"-- Sin tipo --", "A", "B", "C"};
        cmbTipo = new JComboBox<>(tipos);
        cmbTipo.setFont(F_LABEL);
        fila2.add(crearCampo("Tipo:", cmbTipo));
        fila2.add(new JPanel() {{ setOpaque(false); }});
        fila2.add(new JPanel() {{ setOpaque(false); }});
 
        JPanel badges = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        badges.setOpaque(false);
        badges.setBorder(new EmptyBorder(6, 0, 0, 0));
        badges.add(crearBadge("A – 40%",             C_BADGE_A));
        badges.add(crearBadge("B – 20%",             C_BADGE_B));
        badges.add(crearBadge("C – 10%",             C_BADGE_C));
        badges.add(crearBadge("Sin tipo – Sin desc.", C_BADGE_SIN));
 
        panel.add(fila1);
        panel.add(fila2);
        panel.add(badges);
        return panel;
    }
 
    // ── Buscador de producto ──────────────────────────────────────────────────
    private JPanel crearSeccionBuscador() {
        JPanel panel = crearPanelSeccion("Agregar Producto al Carrito");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
 
        txtBuscadorProducto = crearTF("Escribe nombre del producto...");
        txtCantidad         = crearTF("Cant.");
        txtCantidad.setPreferredSize(new Dimension(70, 32));
 
        lblPrecioUnitario = new JLabel("$ —");
        lblPrecioUnitario.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblPrecioUnitario.setForeground(C_VERDE);
 
        lblStockDisponible = new JLabel("Stock: —");
        lblStockDisponible.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblStockDisponible.setForeground(new Color(130, 140, 160));
 
        JButton btnAgregar = crearBoton("＋ Añadir al carrito", C_CARRITO_HDR, 180, 34);
        btnAgregar.addActionListener(e -> accionAgregarAlCarrito());
 
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        fila.setOpaque(false);
        fila.add(wrap("Producto:", txtBuscadorProducto, 280));
        fila.add(wrap("Precio:", lblPrecioUnitario, 120));
        fila.add(wrap("Cantidad:", txtCantidad, 70));
        fila.add(btnAgregar);
 
        JPanel filaInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        filaInfo.setOpaque(false);
        filaInfo.add(lblStockDisponible);
 
        panel.add(fila);
        panel.add(filaInfo);
 
        // ── Ventana flotante de autocompletado ────────────────────────────────
        JWindow ventanaSuger = new JWindow(this);
        ventanaSuger.setAlwaysOnTop(true);
        ventanaSuger.setFocusableWindowState(false);
 
        DefaultListModel<ProductoDTO> listModel = new DefaultListModel<>();
        JList<ProductoDTO> lista = new JList<>(listModel);
        lista.setFont(F_LABEL);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBackground(Color.WHITE);
        lista.setFixedCellHeight(48);
        lista.setFocusable(false);
        lista.setCellRenderer((l, p, idx, sel, foc) -> {
            JPanel row = new JPanel(new BorderLayout(10, 2));
            row.setBackground(sel ? new Color(235, 242, 255) : Color.WHITE);
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(235, 238, 245)),
                    new EmptyBorder(5, 12, 5, 12)));
            JLabel nom  = new JLabel(p.getNombre());
            nom.setFont(new Font("SansSerif", Font.BOLD, 12));
            nom.setForeground(new Color(25, 35, 60));
            boolean stock = p.getStock() > 0;
            JLabel inf  = new JLabel((stock ? "📦 " + p.getStock() : "⚠ Sin stock")
                    + "  💲 " + Coordinador.formatearMoneda(p.getPrecio()));
            inf.setFont(new Font("SansSerif", Font.PLAIN, 11));
            inf.setForeground(stock ? new Color(40, 130, 60) : new Color(200, 60, 60));
            JPanel txt = new JPanel(new BorderLayout(0, 2));
            txt.setOpaque(false);
            txt.add(nom, BorderLayout.NORTH);
            txt.add(inf, BorderLayout.SOUTH);
            JLabel cat = new JLabel(p.getCategoria());
            cat.setFont(new Font("SansSerif", Font.ITALIC, 10));
            cat.setForeground(new Color(140, 150, 170));
            row.add(txt, BorderLayout.CENTER);
            row.add(cat, BorderLayout.EAST);
            return row;
        });
 
        JScrollPane scrollSuger = new JScrollPane(lista);
        scrollSuger.setBorder(BorderFactory.createLineBorder(new Color(180, 195, 220)));
        ventanaSuger.add(scrollSuger);
 
        // Listeners autocompletado
        txtBuscadorProducto.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e)  { if (!seleccionando) actualizarSuger(listModel, lista, ventanaSuger); }
            public void removeUpdate(javax.swing.event.DocumentEvent e)  { if (!seleccionando) actualizarSuger(listModel, lista, ventanaSuger); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { if (!seleccionando) actualizarSuger(listModel, lista, ventanaSuger); }
        });
 
        txtBuscadorProducto.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (!ventanaSuger.isVisible()) return;
                int idx = lista.getSelectedIndex();
                int tam = listModel.getSize();
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:   lista.setSelectedIndex(Math.min(idx+1, tam-1)); lista.ensureIndexIsVisible(lista.getSelectedIndex()); e.consume(); break;
                    case KeyEvent.VK_UP:     if (idx > 0) { lista.setSelectedIndex(idx-1); lista.ensureIndexIsVisible(lista.getSelectedIndex()); } e.consume(); break;
                    case KeyEvent.VK_ENTER:  ProductoDTO sel = lista.getSelectedValue(); if (sel != null) seleccionarProducto(sel, ventanaSuger); e.consume(); break;
                    case KeyEvent.VK_ESCAPE: ventanaSuger.setVisible(false); e.consume(); break;
                }
            }
        });
 
        lista.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                int i = lista.locationToIndex(e.getPoint());
                if (i >= 0) { lista.setSelectedIndex(i); seleccionarProducto(lista.getSelectedValue(), ventanaSuger); }
            }
        });
 
        txtBuscadorProducto.addFocusListener(new FocusAdapter() {
            @Override public void focusLost(FocusEvent e) { ventanaSuger.setVisible(false); }
        });
 
        return panel;
    }
 
    private JPanel wrap(String label, JComponent comp, int w) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(80, 90, 110));
        if (w > 0) comp.setPreferredSize(new Dimension(w, 32));
        p.add(lbl,  BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }
 
    // ── Carrito (panel derecho) ───────────────────────────────────────────────
    private JPanel crearPanelCarrito() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(420, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 210, 225)),
                        "🛒  Carrito de Compra", TitledBorder.LEFT, TitledBorder.TOP,
                        F_SECTION, C_CARRITO_HDR),
                new EmptyBorder(6, 10, 10, 10)));
 
        // Tabla del carrito
        modeloCarrito = new DefaultTableModel(COL_CARRITO, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        JTable tbl = new JTable(modeloCarrito) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                c.setBackground(row % 2 == 0 ? Color.WHITE : C_TBL_PAR);
                c.setForeground(new Color(40, 50, 70));
                if (c instanceof JLabel && col >= 1 && col <= 3)
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                return c;
            }
        };
        tbl.setFont(F_TBL);
        tbl.setRowHeight(28);
        tbl.setShowGrid(false);
        tbl.setIntercellSpacing(new Dimension(0, 0));
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.getTableHeader().setFont(F_TBL_HDR);
        tbl.getTableHeader().setBackground(C_TBL_HDR);
        tbl.getTableHeader().setForeground(Color.WHITE);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        // Columna eliminar: botón ✕
        tbl.getColumn("").setCellRenderer((t, val, sel, foc, row, col) -> {
            JButton btn = new JButton("✕");
            btn.setFont(new Font("SansSerif", Font.BOLD, 10));
            btn.setForeground(Color.WHITE);
            btn.setBackground(C_BTN_LIMPIAR);
            btn.setBorder(new EmptyBorder(2, 6, 2, 6));
            btn.setOpaque(true);
            return btn;
        });
        tbl.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int col = tbl.columnAtPoint(e.getPoint());
                int row = tbl.rowAtPoint(e.getPoint());
                if (col == 4 && row >= 0) eliminarItemCarrito(row);
            }
        });
 
        int[] anchos = {145, 85, 40, 85, 30};
        for (int i = 0; i < anchos.length; i++)
            tbl.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
 
        JScrollPane scroll = new JScrollPane(tbl);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        scroll.getViewport().setBackground(Color.WHITE);
 
        // Totales del carrito
        JPanel totales = new JPanel(new GridLayout(3, 1, 0, 2));
        totales.setOpaque(false);
        totales.setBorder(new EmptyBorder(6, 0, 0, 0));
        lblCarritoTotal = lblTotal("Total bruto:", new Color(40, 55, 90));
        lblCarritoDesc  = lblTotal("Descuento:",   C_NARANJA);
        lblCarritoReal  = lblTotal("Total real:",  C_VERDE);
        totales.add(lblCarritoTotal);
        totales.add(lblCarritoDesc);
        totales.add(lblCarritoReal);
 
        panel.add(scroll,   BorderLayout.CENTER);
        panel.add(totales,  BorderLayout.SOUTH);
        return panel;
    }
 
    private JLabel lblTotal(String prefijo, Color color) {
        JLabel lbl = new JLabel(prefijo + "  $ 0");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(color);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        return lbl;
    }
 
    // ── Panel resultado de venta ──────────────────────────────────────────────
    private JPanel crearPanelResultado() {
        JPanel panel = crearPanelSeccion("✅  Venta Realizada");
        panel.setLayout(new GridLayout(2, 3, 14, 4));
 
        lblResCliente   = crearLblRes("—");
        lblResTipo      = crearLblRes("—");
        lblResItems     = crearLblRes("—");
        lblResTotalBruto= crearLblRes("—");
        lblResDescuento = crearLblRes("—");
        lblResTotalReal = crearLblRes("—");
 
        panel.add(crearGrupo("Cliente:",      lblResCliente));
        panel.add(crearGrupo("Tipo:",         lblResTipo));
        panel.add(crearGrupo("Productos:",    lblResItems));
        panel.add(crearGrupo("Total Bruto:",  lblResTotalBruto));
        panel.add(crearGrupo("Descuento:",    lblResDescuento));
        panel.add(crearGrupo("Total Real:",   lblResTotalReal));
        return panel;
    }
 
    // ── Botones ───────────────────────────────────────────────────────────────
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
 
        JButton btnVender  = crearBoton("✔  Confirmar Venta", C_BTN_COMPRA,  200, 40);
        JButton btnLimpiar = crearBoton("🗑  Limpiar Todo",   C_BTN_LIMPIAR, 160, 40);
 
        btnVender.addActionListener(e  -> accionConfirmarVenta());
        btnLimpiar.addActionListener(e -> accionLimpiarTodo());
 
        panel.add(btnVender);
        panel.add(btnLimpiar);
        return panel;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // PANEL INFERIOR — historial de ventas expandible
    // ═════════════════════════════════════════════════════════════════════════
    public JPanel crearPanelHistorial() {
        panelHistorial = new JPanel(new BorderLayout(0, 8));
        panelHistorial.setBackground(C_BODY);
        panelHistorial.setBorder(new EmptyBorder(4, 20, 10, 20));
 
        // Cabecera
        JPanel cab = new JPanel(new BorderLayout(10, 0));
        cab.setOpaque(false);
        JLabel lblTit = new JLabel("📋  Historial de Ventas");
        lblTit.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTit.setForeground(new Color(40, 55, 90));
        JButton btnRef = crearBoton("⟳ Actualizar", C_AZUL, 130, 30);
        btnRef.addActionListener(e -> cargarHistorial());
        cab.add(lblTit, BorderLayout.WEST);
        cab.add(btnRef, BorderLayout.EAST);
 
        // Tabla historial: una fila por VENTA (agrupada)
        modeloHist = new DefaultTableModel(COL_HIST, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        tablaHist = new JTable(modeloHist) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                String tipo = getValueAt(row, 0) != null ? getValueAt(row, 0).toString() : "";
                boolean esDetalle = tipo.equals("DET");
                if (isRowSelected(row)) {
                    c.setBackground(C_SEL);
                } else if (esDetalle) {
                    c.setBackground(new Color(248, 252, 248));
                    c.setForeground(new Color(60, 100, 60));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : C_TBL_PAR);
                    c.setForeground(new Color(40, 50, 70));
                }
                if (c instanceof JLabel && col >= 5)
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.RIGHT);
                if (c instanceof JLabel && col >= 1 && col <= 4)
                    ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                if (col == 6 && !esDetalle && !isRowSelected(row)) {
                    String v = getValueAt(row, col) != null ? getValueAt(row, col).toString() : "";
                    if (!v.equals("Sin descuento")) { c.setForeground(C_NARANJA); c.setFont(F_TBL_HDR); }
                }
                if (col == 7 && !esDetalle && !isRowSelected(row)) { c.setForeground(C_VERDE); c.setFont(F_TBL_HDR); }
                // Columna expandir/colapsar
                if (col == 0) {
                    if (!esDetalle) {
                        JLabel lbl = new JLabel(tipo.equals("EXP") ? "▼" : "▶");
                        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
                        lbl.setForeground(C_AZUL);
                        lbl.setHorizontalAlignment(SwingConstants.CENTER);
                        lbl.setBackground(c.getBackground());
                        lbl.setOpaque(true);
                        return lbl;
                    } else {
                        return new JLabel(""); // vacío para filas de detalle
                    }
                }
                return c;
            }
        };
        tablaHist.setFont(F_TBL);
        tablaHist.setRowHeight(28);
        tablaHist.setShowGrid(false);
        tablaHist.setIntercellSpacing(new Dimension(0, 0));
        tablaHist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaHist.getTableHeader().setFont(F_TBL_HDR);
        tablaHist.getTableHeader().setBackground(C_TBL_HDR);
        tablaHist.getTableHeader().setForeground(Color.WHITE);
        tablaHist.getTableHeader().setReorderingAllowed(false);
        tablaHist.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        ((DefaultTableCellRenderer) tablaHist.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
 
        // Ocultar columna de tipo (col 0) visualmente pero mantenerla en el modelo
        tablaHist.getColumnModel().getColumn(0).setMinWidth(28);
        tablaHist.getColumnModel().getColumn(0).setMaxWidth(28);
        tablaHist.getColumnModel().getColumn(0).setPreferredWidth(28);
        int[] anchos = {28, 60, 140, 160, 55, 115, 145, 115};
        for (int i = 0; i < anchos.length; i++)
            tablaHist.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
 
        // Clic en fila → expandir/colapsar detalle
        tablaHist.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = tablaHist.rowAtPoint(e.getPoint());
                if (row < 0) return;
                String tipo = modeloHist.getValueAt(row, 0) != null
                        ? modeloHist.getValueAt(row, 0).toString() : "";
                if (!tipo.equals("DET")) toggleDetalle(row);
            }
        });
 
        JScrollPane scroll = new JScrollPane(tablaHist);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        scroll.getViewport().setBackground(Color.WHITE);
 
        // Totalizadores
        JPanel tots = crearPanelTotales();
 
        panelHistorial.add(cab,    BorderLayout.NORTH);
        panelHistorial.add(scroll, BorderLayout.CENTER);
        panelHistorial.add(tots,   BorderLayout.SOUTH);
        return panelHistorial;
    }
 
    private JPanel crearPanelTotales() {
        JPanel p = new JPanel(new GridLayout(1, 4, 12, 0));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(6, 0, 0, 0));
 
        lblTotalCompras    = new JLabel("0");
        lblTotalVendido    = new JLabel("$ 0");
        lblTotalDescuentos = new JLabel("$ 0");
        lblTotalReal       = new JLabel("$ 0");
 
        p.add(crearTarjetaTotal("Nº Ventas",          lblTotalCompras,    new Color(60,  90, 170)));
        p.add(crearTarjetaTotal("Total Vendido",       lblTotalVendido,    new Color(30, 100,  70)));
        p.add(crearTarjetaTotal("Total Descuentos",    lblTotalDescuentos, new Color(160, 90,  10)));
        p.add(crearTarjetaTotal("Total Real Cobrado",  lblTotalReal,       new Color(40, 120,  60)));
        return p;
    }
 
    private JPanel crearTarjetaTotal(String t, JLabel v, Color c) {
        JPanel card = new JPanel(new BorderLayout(0, 3));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(8, 12, 8, 12)));
        JLabel lt = new JLabel(t);
        lt.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lt.setForeground(new Color(100, 110, 130));
        v.setFont(new Font("SansSerif", Font.BOLD, 15));
        v.setForeground(c);
        card.add(lt, BorderLayout.NORTH);
        card.add(v,  BorderLayout.CENTER);
        return card;
    }
 
    // ── Footer ────────────────────────────────────────────────────────────────
    private JPanel crearFooter() {
        JPanel f = new JPanel(new FlowLayout(FlowLayout.CENTER));
        f.setBackground(C_FOOTER);
        f.setPreferredSize(new Dimension(0, 32));
        JLabel l = new JLabel("© 2024 DON APARATO – Todos los derechos reservados");
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(new Color(140, 160, 190));
        f.add(l);
        return f;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA — AUTOCOMPLETADO
    // ═════════════════════════════════════════════════════════════════════════
 
    private void actualizarSuger(DefaultListModel<ProductoDTO> model,
                                  JList<ProductoDTO> lista, JWindow ventana) {
        String texto = txtBuscadorProducto.getText().trim();
        if (!nombreProductoSeleccionado.isEmpty() &&
            texto.equalsIgnoreCase(nombreProductoSeleccionado)) {
            ventana.setVisible(false); return;
        }
        if (texto.isEmpty()) { limpiarSeleccionProducto(); ventana.setVisible(false); return; }
        try {
            List<ProductoDTO> res = coordinador.listarProductos(texto);
            model.clear();
            if (res.isEmpty()) { ventana.setVisible(false); return; }
            for (ProductoDTO p : res) model.addElement(p);
            Point loc = txtBuscadorProducto.getLocationOnScreen();
            int alto  = Math.min(res.size() * 48 + 4, 250);
            ventana.setBounds(loc.x, loc.y + txtBuscadorProducto.getHeight(),
                    txtBuscadorProducto.getWidth(), alto);
            ventana.setVisible(true);
            lista.clearSelection();
        } catch (Exception ex) { ventana.setVisible(false); }
    }
 
    private void seleccionarProducto(ProductoDTO p, JWindow ventana) {
        seleccionando              = true;
        productoSeleccionado       = p;
        nombreProductoSeleccionado = p.getNombre();
        ventana.setVisible(false);
        txtBuscadorProducto.setText(p.getNombre());
        seleccionando = false;
 
        lblPrecioUnitario.setText(Coordinador.formatearMoneda(p.getPrecio()));
        if (p.getStock() > 0) {
            lblStockDisponible.setText("✔  Stock: " + p.getStock() + " unidades");
            lblStockDisponible.setForeground(new Color(40, 130, 60));
        } else {
            lblStockDisponible.setText("✘  Sin stock");
            lblStockDisponible.setForeground(new Color(200, 60, 60));
        }
        txtCantidad.setText("1");
        txtCantidad.requestFocus();
        txtCantidad.selectAll();
    }
 
    private void limpiarSeleccionProducto() {
        productoSeleccionado       = null;
        nombreProductoSeleccionado = "";
        seleccionando              = false;
        lblPrecioUnitario.setText("$ —");
        lblStockDisponible.setText("Stock: —");
        lblStockDisponible.setForeground(new Color(130, 140, 160));
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA — CARRITO
    // ═════════════════════════════════════════════════════════════════════════
 
    private void accionAgregarAlCarrito() {
        final ProductoDTO prod = productoSeleccionado;
        if (prod == null || !txtBuscadorProducto.getText().trim()
                .equalsIgnoreCase(nombreProductoSeleccionado)) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un producto de la lista.", "Sin producto", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int cant;
        try {
            cant = Integer.parseInt(txtCantidad.getText().trim());
            if (cant <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe ser un número mayor a 0.", "Cantidad inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cant > prod.getStock()) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + prod.getStock(), "Stock", JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        // Si el mismo producto ya está en el carrito, sumar cantidad
        for (ItemCarritoDTO item : carrito) {
            if (item.getNombreProducto().equalsIgnoreCase(prod.getNombre())) {
                int nueva = item.getCantidad() + cant;
                if (nueva > prod.getStock()) {
                    JOptionPane.showMessageDialog(this,
                            "Stock insuficiente para esa cantidad total.", "Stock", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                item.setCantidad(nueva);
                refrescarTablaCarrito();
                limpiarCampoProducto();
                return;
            }
        }
 
        carrito.add(new ItemCarritoDTO(prod, cant));
        refrescarTablaCarrito();
        limpiarCampoProducto();
    }
 
    private void eliminarItemCarrito(int fila) {
        if (fila >= 0 && fila < carrito.size()) {
            carrito.remove(fila);
            refrescarTablaCarrito();
        }
    }
 
    private void refrescarTablaCarrito() {
        modeloCarrito.setRowCount(0);
        double totalBruto = 0;
        for (ItemCarritoDTO item : carrito) {
            modeloCarrito.addRow(new Object[]{
                    item.getNombreProducto(),
                    Coordinador.formatearMoneda(item.getPrecioUnitario()),
                    item.getCantidad(),
                    Coordinador.formatearMoneda(item.getSubtotal()),
                    "✕"
            });
            totalBruto += item.getSubtotal();
        }
        // Calcular descuento según tipo seleccionado
        String tipoSel = cmbTipo.getSelectedItem() != null ? cmbTipo.getSelectedItem().toString() : "";
        String tipo = tipoSel.startsWith("--") ? "Sin tipo" : tipoSel;
        double porcDesc = coordinador.getProcesos().calcularDescuento(tipo);
        double descuento = totalBruto * porcDesc;
        double totalReal = totalBruto - descuento;
 
        lblCarritoTotal.setText("Total bruto:   " + Coordinador.formatearMoneda(totalBruto));
        lblCarritoDesc.setText("Descuento " + (porcDesc > 0 ? (int)(porcDesc*100) + "%" : "0%")
                + ":   " + Coordinador.formatearMoneda(descuento));
        lblCarritoReal.setText("Total real:   " + Coordinador.formatearMoneda(totalReal));
    }
 
    private void limpiarCampoProducto() {
        txtBuscadorProducto.setText("");
        txtCantidad.setText("");
        limpiarSeleccionProducto();
        txtBuscadorProducto.requestFocus();
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA — CONFIRMAR VENTA
    // ═════════════════════════════════════════════════════════════════════════
 
    private void accionConfirmarVenta() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El carrito está vacío. Agrega al menos un producto.", "Carrito vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tipoSel = cmbTipo.getSelectedItem() != null ? cmbTipo.getSelectedItem().toString() : "";
        String tipo    = tipoSel.startsWith("--") ? "Sin tipo" : tipoSel;
 
        ResultadoVenta resultado = coordinador.realizarVenta(
                txtNombre.getText(), txtApellido.getText(),
                txtEdad.getText(),   txtTelefono.getText(), txtCedula.getText(),
                tipo, new ArrayList<>(carrito));
 
        if (resultado == null) return;
 
        // Mostrar resultado
        lblResCliente.setText(resultado.usuario.getNombre() + " " + resultado.usuario.getApellido());
        lblResTipo.setText(tipo.equals("Sin tipo") ? "Sin tipo" : "Tipo " + tipo);
        lblResItems.setText(resultado.totalItems() + " producto(s)");
        lblResTotalBruto.setText(Coordinador.formatearMoneda(resultado.totalBruto));
        lblResDescuento.setText(resultado.tieneDescuento()
                ? resultado.getPorcentajeEntero() + "% → " + Coordinador.formatearMoneda(resultado.valorDescuento)
                : "Sin descuento");
        lblResTotalReal.setText(Coordinador.formatearMoneda(resultado.totalReal));
        panelResultado.setVisible(true);
 
        // Vaciar carrito y limpiar producto
        carrito.clear();
        refrescarTablaCarrito();
        limpiarCampoProducto();
        cargarHistorial();
        revalidate(); repaint();
    }
 
    private void accionLimpiarTodo() {
        txtNombre.setText(""); txtApellido.setText("");
        txtEdad.setText("");   txtTelefono.setText("");
        txtCedula.setText(""); cmbTipo.setSelectedIndex(0);
        carrito.clear();
        refrescarTablaCarrito();
        limpiarCampoProducto();
        panelResultado.setVisible(false);
        lblResCliente.setText("—"); lblResTipo.setText("—");
        lblResItems.setText("—");   lblResTotalBruto.setText("—");
        lblResDescuento.setText("—"); lblResTotalReal.setText("—");
        revalidate(); repaint();
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA — HISTORIAL EXPANDIBLE
    // ═════════════════════════════════════════════════════════════════════════
 
    public void cargarHistorial() {
        modeloHist.setRowCount(0);
        double sumBruto = 0, sumDesc = 0, sumReal = 0;
        int    numVentas = 0;
 
        try {
            List<CompraDTO> items = coordinador.listarCompras(null);
            if (items.isEmpty()) {
                lblTotalCompras.setText("0");
                lblTotalVendido.setText(Coordinador.formatearMoneda(0));
                lblTotalDescuentos.setText(Coordinador.formatearMoneda(0));
                lblTotalReal.setText(Coordinador.formatearMoneda(0));
                return;
            }
 
            // Agrupar ítems por venta_id (campo id de CompraDTO = venta_id en el JOIN)
            java.util.LinkedHashMap<Integer, List<CompraDTO>> porVenta = new java.util.LinkedHashMap<>();
            for (CompraDTO c : items) {
                porVenta.computeIfAbsent(c.getId(), k -> new ArrayList<>()).add(c);
            }
 
            for (java.util.Map.Entry<Integer, List<CompraDTO>> entry : porVenta.entrySet()) {
                int ventaId = entry.getKey();
                List<CompraDTO> grupo = entry.getValue();
                CompraDTO cab = grupo.get(0); // cabecera (datos del cliente y totales)
 
                String descStr = cab.tieneDescuento()
                        ? cab.getPorcentajeDescuentoEntero() + "% → "
                          + Coordinador.formatearMoneda(cab.getValorDescuento())
                        : "Sin descuento";
 
                // Fila cabecera de venta (colapsada por defecto)
                modeloHist.addRow(new Object[]{
                        "COL",                              // estado: COL = colapsado
                        ventaId,
                        cab.getFechaHoraStr(),
                        cab.getClienteNombreCompleto(),
                        grupo.size() + " ítem(s)",
                        Coordinador.formatearMoneda(cab.getTotalBruto()),
                        descStr,
                        Coordinador.formatearMoneda(cab.getTotalReal())
                });
 
                sumBruto += cab.getTotalBruto();
                sumDesc  += cab.getValorDescuento();
                sumReal  += cab.getTotalReal();
                numVentas++;
            }
 
            lblTotalCompras.setText(String.valueOf(numVentas));
            lblTotalVendido.setText(Coordinador.formatearMoneda(sumBruto));
            lblTotalDescuentos.setText(Coordinador.formatearMoneda(sumDesc));
            lblTotalReal.setText(Coordinador.formatearMoneda(sumReal));
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar historial:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    /** Expande o colapsa el detalle de una fila de venta. */
    private void toggleDetalle(int row) {
        String estado = modeloHist.getValueAt(row, 0).toString();
        int ventaId   = (int) modeloHist.getValueAt(row, 1);
 
        if (estado.equals("COL")) {
            // Expandir: insertar filas de detalle debajo
            try {
                List<CompraDTO> items = coordinador.listarCompras(null);
                List<CompraDTO> grupo = new ArrayList<>();
                for (CompraDTO c : items) { if (c.getId() == ventaId) grupo.add(c); }
 
                int insertAt = row + 1;
                for (CompraDTO item : grupo) {
                    modeloHist.insertRow(insertAt++, new Object[]{
                            "DET",
                            "",
                            "   ↳  " + item.getProductoNombre(),
                            "Cant: " + item.getCantidad(),
                            "Unit: " + Coordinador.formatearMoneda(item.getValorUnitario()),
                            Coordinador.formatearMoneda(item.getTotalBruto()),
                            "", ""
                    });
                }
                modeloHist.setValueAt("EXP", row, 0); // marcar como expandida
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cargar detalle:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
 
        } else if (estado.equals("EXP")) {
            // Colapsar: eliminar filas de detalle
            int next = row + 1;
            while (next < modeloHist.getRowCount() &&
                   "DET".equals(modeloHist.getValueAt(next, 0))) {
                modeloHist.removeRow(next);
            }
            modeloHist.setValueAt("COL", row, 0);
        }
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // FÁBRICA DE COMPONENTES
    // ═════════════════════════════════════════════════════════════════════════
 
    private JPanel crearPanelSeccion(String titulo) {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 210, 225)),
                        titulo, TitledBorder.LEFT, TitledBorder.TOP,
                        F_SECTION, new Color(40, 55, 90)),
                new EmptyBorder(6, 12, 8, 12)));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return p;
    }
 
    private JPanel crearCampo(String label, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setOpaque(false);
        JLabel l = new JLabel(label);
        l.setFont(F_LABEL);
        l.setForeground(new Color(60, 70, 90));
        p.add(l,     BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }
 
    private JTextField crearTF(String ph) {
        JTextField tf = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner() && !ph.isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(160, 170, 185));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(ph, getInsets().left + 2,
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
 
    private JLabel crearLblRes(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(new Color(30, 80, 160));
        return l;
    }
 
    private JPanel crearGrupo(String d, JLabel v) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setOpaque(false);
        JLabel ld = new JLabel(d);
        ld.setFont(new Font("SansSerif", Font.PLAIN, 11));
        ld.setForeground(new Color(100, 110, 130));
        p.add(ld, BorderLayout.NORTH);
        p.add(v,  BorderLayout.CENTER);
        return p;
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
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(false);
        lbl.setBorder(new EmptyBorder(3, 8, 3, 8));
        return lbl;
    }
}