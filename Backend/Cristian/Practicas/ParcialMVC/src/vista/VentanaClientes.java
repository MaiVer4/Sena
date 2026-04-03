
package vista;
 
import controlador.Coordinador;
import modelo.dto.ClienteDTO;
import modelo.dto.CompraDTO;
 
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
 
/**
 * Módulo de Clientes.
 * Layout: SplitPane vertical.
 *   ▲ Arriba : tabla de clientes + formulario lateral (CRUD)
 *   ▼ Abajo  : historial de compras del cliente seleccionado
 */
public class VentanaClientes extends JFrame {
 
    private Coordinador coordinador;
 
    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color C_HEADER   = new Color(26,  35,  56);
    private static final Color C_LINEA    = new Color(200, 140, 30);
    private static final Color C_TITULO   = new Color(230, 160, 30);
    private static final Color C_BODY     = new Color(240, 242, 245);
    private static final Color C_FOOTER   = new Color(26,  35,  56);
    private static final Color C_CARD     = Color.WHITE;
    private static final Color C_VERSION  = new Color(230, 160, 30);
    private static final Color C_VERDE    = new Color(40,  140,  80);
    private static final Color C_AZUL     = new Color(60,   90, 170);
    private static final Color C_AMARILLO = new Color(190, 130,  10);
    private static final Color C_ROJO     = new Color(180,  50,  40);
    private static final Color C_GRIS     = new Color(90,   90, 100);
    private static final Color C_NARANJA  = new Color(160,  90,  10);
    private static final Color C_BADGE_A  = new Color(200,  50,  40);
    private static final Color C_BADGE_B  = new Color(40,  130,  60);
    private static final Color C_BADGE_C  = new Color(40,  100, 180);
    private static final Color C_BADGE_ST = new Color(90,   90,  90);
    private static final Color C_TBL_HDR  = new Color(30,   45,  80);
    private static final Color C_TBL_PAR  = new Color(245, 247, 252);
    private static final Color C_SEL_CLI  = new Color(120,  40, 110, 55);
    private static final Color C_SEL_HIST = new Color(200, 140,  30, 50);
 
    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_LABEL   = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font F_BOLD    = new Font("SansSerif", Font.BOLD,  13);
    private static final Font F_BTN     = new Font("SansSerif", Font.BOLD,  12);
    private static final Font F_SECCION = new Font("SansSerif", Font.BOLD,  13);
    private static final Font F_TBL     = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font F_TBL_HDR = new Font("SansSerif", Font.BOLD,  12);
 
    // ── Tabla clientes ────────────────────────────────────────────────────────
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private static final String[] COL_CLI =
            {"ID", "Cédula", "Nombre", "Apellido", "Edad", "Teléfono", "Tipo"};
 
    // ── Tabla historial ───────────────────────────────────────────────────────
    private JTable            tablaHist;
    private DefaultTableModel modeloHist;
    private static final String[] COL_HIST =
            {"#", "Fecha y Hora", "Producto", "Cant.", "Total Bruto", "Descuento", "Total Real"};
 
    // ── KPIs historial ────────────────────────────────────────────────────────
    private JLabel lblHistTitulo, lblKpiCompras, lblKpiGastado, lblKpiDescuentos, lblKpiPromedio;
 
    // ── Formulario ────────────────────────────────────────────────────────────
    private JTextField  txtCedula, txtNombre, txtApellido, txtEdad, txtTelefono, txtBuscar;
    private JComboBox<String> cmbTipo;
    private JLabel      lblIdOculto;
 
    // ── Estado ────────────────────────────────────────────────────────────────
    private enum Modo { NINGUNO, AGREGAR, EDITAR }
    private Modo modoActual = Modo.NINGUNO;
 
    // ─────────────────────────────────────────────────────────────────────────
 
    public VentanaClientes(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
        cargarTabla(null);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // INIT
    // ═════════════════════════════════════════════════════════════════════════
    private void initComponents() {
        setTitle("DON APARATO – Gestión de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 820);
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
        btnVolver.addActionListener(e -> coordinador.volverDesdeClientes());
        left.add(btnVolver);
        left.add(Box.createVerticalStrut(6));
 
        JLabel lblTitulo = new JLabel("DON APARATO");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setForeground(C_TITULO);
        left.add(lblTitulo);
 
        JLabel lblSub = new JLabel("Módulo de Gestión de Clientes");
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
 
    // ═════════════════════════════════════════════════════════════════════════
    // CUERPO — SplitPane vertical: CRUD arriba | historial abajo
    // ═════════════════════════════════════════════════════════════════════════
    private JSplitPane crearCuerpo() {
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                crearPanelCRUD(), crearPanelHistorial());
        split.setDividerLocation(400);
        split.setResizeWeight(0.5);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setBackground(C_BODY);
        return split;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // PANEL SUPERIOR — tabla clientes + formulario
    // ═════════════════════════════════════════════════════════════════════════
    private JSplitPane crearPanelCRUD() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelTabla(), crearPanelFormulario());
        split.setDividerLocation(680);
        split.setDividerSize(5);
        split.setBorder(null);
        split.setBackground(C_BODY);
        return split;
    }
 
    // ── Tabla de clientes ─────────────────────────────────────────────────────
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(C_BODY);
        panel.setBorder(new EmptyBorder(14, 20, 8, 10));
 
        // Buscador
        JPanel barraTop = new JPanel(new BorderLayout(8, 0));
        barraTop.setOpaque(false);
        txtBuscar = crearTextField("Buscar por nombre, apellido o cédula…");
        JButton btnBuscar = crearBoton("🔍 Buscar", C_AZUL, 120, 34);
        JButton btnTodos  = crearBoton("↺ Todos",   C_GRIS, 100, 34);
        btnBuscar.addActionListener(e -> cargarTabla(txtBuscar.getText().trim()));
        btnTodos.addActionListener(e  -> { txtBuscar.setText(""); cargarTabla(null); });
        txtBuscar.addActionListener(e -> cargarTabla(txtBuscar.getText().trim()));
        JPanel btnTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnTop.setOpaque(false);
        btnTop.add(btnBuscar);
        btnTop.add(btnTodos);
        barraTop.add(txtBuscar, BorderLayout.CENTER);
        barraTop.add(btnTop,    BorderLayout.EAST);
 
        // Tabla
        modeloTabla = new DefaultTableModel(COL_CLI, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        tabla = new JTable(modeloTabla) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(C_SEL_CLI);
                    c.setForeground(new Color(60, 10, 60));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : C_TBL_PAR);
                    c.setForeground(new Color(40, 50, 70));
                }
                if (col == 6 && !isRowSelected(row)) {
                    String tipo = getValueAt(row, col) != null ? getValueAt(row, col).toString() : "";
                    switch (tipo) {
                        case "A": c.setForeground(C_BADGE_A); break;
                        case "B": c.setForeground(C_BADGE_B); break;
                        case "C": c.setForeground(C_BADGE_C); break;
                        default:  c.setForeground(C_BADGE_ST); break;
                    }
                    c.setFont(F_BOLD);
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
 
        int[] anchos = {40, 110, 120, 120, 45, 110, 55};
        for (int i = 0; i < anchos.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
 
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFormularioDesdeTabla();
                cargarHistorialCliente();   // ← carga historial al seleccionar
            }
        });
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        scroll.getViewport().setBackground(Color.WHITE);
 
        // Botones CRUD
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.setOpaque(false);
        JButton btnAgregar  = crearBoton("＋ Agregar",  C_VERDE,    120, 36);
        JButton btnEditar   = crearBoton("✏ Editar",    C_AMARILLO, 110, 36);
        JButton btnEliminar = crearBoton("🗑 Eliminar", C_ROJO,     110, 36);
        btnAgregar.addActionListener(e  -> activarModoAgregar());
        btnEditar.addActionListener(e   -> activarModoEditar());
        btnEliminar.addActionListener(e -> accionEliminar());
        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
 
        panel.add(barraTop, BorderLayout.NORTH);
        panel.add(scroll,   BorderLayout.CENTER);
        panel.add(botones,  BorderLayout.SOUTH);
        return panel;
    }
 
    // ── Formulario CRUD ───────────────────────────────────────────────────────
    private JPanel crearPanelFormulario() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(C_BODY);
        outer.setBorder(new EmptyBorder(14, 10, 8, 20));
 
        JPanel card = new JPanel();
        card.setBackground(C_CARD);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(200, 210, 225)),
                        "Detalle del Cliente", TitledBorder.LEFT, TitledBorder.TOP,
                        F_SECCION, new Color(120, 40, 110)),
                new EmptyBorder(10, 14, 14, 14)));
 
        lblIdOculto = new JLabel("0");
        lblIdOculto.setVisible(false);
        card.add(lblIdOculto);
 
        txtCedula   = crearTextField("Ej: 1000123456");
        txtNombre   = crearTextField("Ej: Laura");
        txtApellido = crearTextField("Ej: Martínez");
        txtEdad     = crearTextField("Ej: 28");
        txtTelefono = crearTextField("Ej: 3001112233");
        String[] tipos = {"Sin tipo", "A", "B", "C"};
        cmbTipo = new JComboBox<>(tipos);
        cmbTipo.setFont(F_LABEL);
        cmbTipo.setBackground(new Color(250, 251, 253));
 
        card.add(campoCon("C.C. (Cédula) *",  txtCedula));
        card.add(Box.createVerticalStrut(8));
        card.add(campoCon("Nombre *",          txtNombre));
        card.add(Box.createVerticalStrut(8));
        card.add(campoCon("Apellido *",        txtApellido));
        card.add(Box.createVerticalStrut(8));
        card.add(campoCon("Edad *",            txtEdad));
        card.add(Box.createVerticalStrut(8));
        card.add(campoCon("Teléfono *",        txtTelefono));
        card.add(Box.createVerticalStrut(8));
        card.add(campoCon("Tipo de Usuario",   cmbTipo));
        card.add(Box.createVerticalStrut(8));
 
        // Badges referencia
        JPanel badges = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        badges.setOpaque(false);
        badges.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        badges.add(crearBadge("A – 40%",  C_BADGE_A));
        badges.add(crearBadge("B – 20%",  C_BADGE_B));
        badges.add(crearBadge("C – 10%",  C_BADGE_C));
        badges.add(crearBadge("Sin tipo", C_BADGE_ST));
        card.add(badges);
        card.add(Box.createVerticalStrut(14));
 
        // Botones formulario
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        JButton btnGuardar  = crearBoton("💾 Guardar",  C_VERDE,  120, 36);
        JButton btnCancelar = crearBoton("✖ Cancelar",  C_ROJO,   110, 36);
        JButton btnLimpiar  = crearBoton("↺ Limpiar",   C_GRIS,   100, 36);
        btnGuardar.addActionListener(e  -> accionGuardar());
        btnCancelar.addActionListener(e -> cancelarFormulario());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());
        btnPanel.add(btnGuardar);
        btnPanel.add(btnCancelar);
        btnPanel.add(btnLimpiar);
        card.add(btnPanel);
 
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblNota.setForeground(new Color(140, 150, 170));
        lblNota.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(Box.createVerticalStrut(4));
        card.add(lblNota);
 
        bloquearFormulario(true);
        outer.add(card, BorderLayout.CENTER);
        return outer;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // PANEL INFERIOR — historial de compras del cliente seleccionado
    // ═════════════════════════════════════════════════════════════════════════
    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(C_BODY);
        panel.setBorder(new EmptyBorder(4, 20, 10, 20));
 
        // ── Cabecera ──────────────────────────────────────────────────────────
        JPanel cabecera = new JPanel(new BorderLayout(10, 0));
        cabecera.setOpaque(false);
 
        lblHistTitulo = new JLabel("📋  Selecciona un cliente para ver su historial");
        lblHistTitulo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHistTitulo.setForeground(new Color(40, 55, 90));
        cabecera.add(lblHistTitulo, BorderLayout.WEST);
 
        // ── KPIs del cliente ──────────────────────────────────────────────────
        JPanel kpis = new JPanel(new GridLayout(1, 4, 10, 0));
        kpis.setOpaque(false);
 
        lblKpiCompras    = new JLabel("—");
        lblKpiGastado    = new JLabel("—");
        lblKpiDescuentos = new JLabel("—");
        lblKpiPromedio   = new JLabel("—");
 
        kpis.add(crearTarjetaKPI("Compras realizadas",   lblKpiCompras,    new Color(120, 40, 110)));
        kpis.add(crearTarjetaKPI("Total gastado",        lblKpiGastado,    C_VERDE));
        kpis.add(crearTarjetaKPI("Total ahorrado",       lblKpiDescuentos, C_NARANJA));
        kpis.add(crearTarjetaKPI("Ticket promedio",      lblKpiPromedio,   C_AZUL));
 
        // ── Tabla historial ───────────────────────────────────────────────────
        modeloHist = new DefaultTableModel(COL_HIST, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
 
        tablaHist = new JTable(modeloHist) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(C_SEL_HIST);
                    c.setForeground(new Color(80, 50, 10));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : C_TBL_PAR);
                    c.setForeground(new Color(40, 50, 70));
                }
                // Alineación explícita en cada columna
                if (c instanceof JLabel) {
                    JLabel lbl = (JLabel) c;
                    if (col >= 4) {
                        lbl.setHorizontalAlignment(SwingConstants.RIGHT);   // montos
                    } else {
                        lbl.setHorizontalAlignment(SwingConstants.CENTER);  // #, fecha, producto, cant
                    }
                }
                if (col == 5 && !isRowSelected(row)) {
                    String val = getValueAt(row, col) != null ? getValueAt(row, col).toString() : "";
                    if (!val.equals("Sin descuento")) { c.setForeground(C_NARANJA); c.setFont(F_TBL_HDR); }
                }
                if (col == 6 && !isRowSelected(row)) { c.setForeground(C_VERDE); c.setFont(F_TBL_HDR); }
                return c;
            }
        };
        tablaHist.setFont(F_TBL);
        tablaHist.setRowHeight(26);
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
 
        int[] anchos = {40, 145, 200, 45, 120, 155, 120};
        for (int i = 0; i < anchos.length; i++)
            tablaHist.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
 
        JScrollPane scroll = new JScrollPane(tablaHist);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(210, 215, 225)));
        scroll.getViewport().setBackground(Color.WHITE);
 
        panel.add(cabecera, BorderLayout.NORTH);
        panel.add(kpis,     BorderLayout.CENTER);
        panel.add(scroll,   BorderLayout.SOUTH);
 
        // Darle más espacio al scroll
        scroll.setPreferredSize(new Dimension(0, 180));
 
        // Reorganizar: cabecera → kpis → scroll
        panel.removeAll();
        panel.setLayout(new BorderLayout(0, 8));
        JPanel top = new JPanel(new BorderLayout(0, 8));
        top.setOpaque(false);
        top.add(cabecera, BorderLayout.NORTH);
        top.add(kpis,     BorderLayout.CENTER);
        panel.add(top,    BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
 
        return panel;
    }
 
    private JPanel crearTarjetaKPI(String titulo, JLabel valor, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 3)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(color);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), 3, 2, 2));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 225)),
                new EmptyBorder(8, 12, 8, 12)));
 
        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblTit.setForeground(new Color(100, 110, 130));
 
        valor.setFont(new Font("SansSerif", Font.BOLD, 15));
        valor.setForeground(color);
 
        card.add(lblTit, BorderLayout.NORTH);
        card.add(valor,  BorderLayout.CENTER);
        return card;
    }
 
    // ── Footer ────────────────────────────────────────────────────────────────
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(C_FOOTER);
        footer.setPreferredSize(new Dimension(0, 32));
        JLabel lbl = new JLabel("© 2024 DON APARATO – Todos los derechos reservados");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(new Color(140, 160, 190));
        footer.add(lbl);
        return footer;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA — CRUD clientes
    // ═════════════════════════════════════════════════════════════════════════
 
    private void cargarTabla(String filtro) {
        modeloTabla.setRowCount(0);
        try {
            List<ClienteDTO> lista = coordinador.listarClientes(filtro);
            for (ClienteDTO c : lista) {
                modeloTabla.addRow(new Object[]{
                        c.getId(), c.getCedula(), c.getNombre(),
                        c.getApellido(), c.getEdad(), c.getTelefono(), c.getTipoUsuario()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar clientes:\n" + ex.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void cargarFormularioDesdeTabla() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        lblIdOculto.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtCedula.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtApellido.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtEdad.setText(modeloTabla.getValueAt(fila, 4).toString());
        txtTelefono.setText(modeloTabla.getValueAt(fila, 5).toString());
        cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 6).toString());
        if (modoActual == Modo.NINGUNO) bloquearFormulario(true);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // LÓGICA — historial del cliente
    // ═════════════════════════════════════════════════════════════════════════
 
    private void cargarHistorialCliente() {
        int fila = tabla.getSelectedRow();
        modeloHist.setRowCount(0);
 
        // Resetear KPIs
        lblKpiCompras.setText("—");
        lblKpiGastado.setText("—");
        lblKpiDescuentos.setText("—");
        lblKpiPromedio.setText("—");
 
        if (fila < 0) {
            lblHistTitulo.setText("📋  Selecciona un cliente para ver su historial");
            return;
        }
 
        String cedula  = modeloTabla.getValueAt(fila, 1).toString();
        String nombre  = modeloTabla.getValueAt(fila, 2).toString()
                       + " " + modeloTabla.getValueAt(fila, 3).toString();
 
        lblHistTitulo.setText("📋  Historial de compras de: " + nombre
                + "  |  Cédula: " + cedula);
 
        try {
            List<CompraDTO> compras = coordinador.comprasPorCedula(cedula);
 
            if (compras.isEmpty()) {
                lblKpiCompras.setText("0");
                lblKpiGastado.setText(Coordinador.formatearMoneda(0));
                lblKpiDescuentos.setText(Coordinador.formatearMoneda(0));
                lblKpiPromedio.setText(Coordinador.formatearMoneda(0));
                return;
            }
 
            double sumaGastado    = 0;
            double sumaDescuentos = 0;
 
            for (CompraDTO c : compras) {
                String descStr = c.tieneDescuento()
                        ? c.getPorcentajeDescuentoEntero() + "% → "
                          + Coordinador.formatearMoneda(c.getValorDescuento())
                        : "Sin descuento";
 
                modeloHist.addRow(new Object[]{
                        c.getId(),
                        c.getFechaHoraStr(),
                        c.getProductoNombre(),
                        c.getCantidad(),
                        Coordinador.formatearMoneda(c.getTotalBruto()),
                        descStr,
                        Coordinador.formatearMoneda(c.getTotalReal())
                });
 
                sumaGastado    += c.getTotalReal();
                sumaDescuentos += c.getValorDescuento();
            }
 
            double promedio = sumaGastado / compras.size();
 
            lblKpiCompras.setText(String.valueOf(compras.size()));
            lblKpiGastado.setText(Coordinador.formatearMoneda(sumaGastado));
            lblKpiDescuentos.setText(Coordinador.formatearMoneda(sumaDescuentos));
            lblKpiPromedio.setText(Coordinador.formatearMoneda(promedio));
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar historial:\n" + ex.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void activarModoAgregar() {
        modoActual = Modo.AGREGAR;
        limpiarFormulario();
        bloquearFormulario(false);
        tabla.clearSelection();
        txtCedula.requestFocus();
    }
 
    private void activarModoEditar() {
        if (tabla.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un cliente de la tabla para editar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        modoActual = Modo.EDITAR;
        bloquearFormulario(false);
        txtCedula.requestFocus();
    }
 
    private void accionGuardar() {
        String cedula   = txtCedula.getText().trim();
        String nombre   = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String edadStr  = txtEdad.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String tipo     = cmbTipo.getSelectedItem().toString();
 
        if (cedula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()
                || edadStr.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor complete todos los campos obligatorios (*).",
                    "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad <= 0 || edad > 120) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "La edad debe ser un número entero entre 1 y 120.",
                    "Dato inválido", JOptionPane.ERROR_MESSAGE);
            return;
        }
 
        int id = Integer.parseInt(lblIdOculto.getText());
        ClienteDTO c = new ClienteDTO(id, cedula, nombre, apellido, edad, telefono, tipo);
 
        try {
            if (modoActual == Modo.AGREGAR) {
                coordinador.agregarCliente(c);
                JOptionPane.showMessageDialog(this, "Cliente registrado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                coordinador.editarCliente(c);
                JOptionPane.showMessageDialog(this, "Cliente actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            cargarTabla(null);
            cancelarFormulario();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Duplicado", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar:\n" + ex.getMessage(), "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void accionEliminar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un cliente de la tabla para eliminar.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nombre = modeloTabla.getValueAt(fila, 2) + " " + modeloTabla.getValueAt(fila, 3);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar al cliente:\n\"" + nombre + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
 
        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        try {
            coordinador.eliminarCliente(id);
            JOptionPane.showMessageDialog(this, "Cliente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla(null);
            cancelarFormulario();
            // Limpiar historial
            modeloHist.setRowCount(0);
            lblHistTitulo.setText("📋  Selecciona un cliente para ver su historial");
            lblKpiCompras.setText("—"); lblKpiGastado.setText("—");
            lblKpiDescuentos.setText("—"); lblKpiPromedio.setText("—");
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
        txtCedula.setText(""); txtNombre.setText(""); txtApellido.setText("");
        txtEdad.setText(""); txtTelefono.setText(""); cmbTipo.setSelectedIndex(0);
    }
 
    private void bloquearFormulario(boolean bloquear) {
        Color bg = bloquear ? new Color(235, 237, 242) : new Color(250, 251, 253);
        txtCedula.setEditable(!bloquear);   txtCedula.setBackground(bg);
        txtNombre.setEditable(!bloquear);   txtNombre.setBackground(bg);
        txtApellido.setEditable(!bloquear); txtApellido.setBackground(bg);
        txtEdad.setEditable(!bloquear);     txtEdad.setBackground(bg);
        txtTelefono.setEditable(!bloquear); txtTelefono.setBackground(bg);
        cmbTipo.setEnabled(!bloquear);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // FÁBRICA DE COMPONENTES
    // ═════════════════════════════════════════════════════════════════════════
 
    private JPanel campoCon(String labelText, JComponent campo) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(F_LABEL);
        lbl.setForeground(new Color(60, 70, 90));
        p.add(lbl,   BorderLayout.NORTH);
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
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(false);
        lbl.setBorder(new EmptyBorder(3, 8, 3, 8));
        return lbl;
    }
}