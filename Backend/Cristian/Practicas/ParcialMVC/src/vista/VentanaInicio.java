
package vista;
 
import controlador.Coordinador;
 
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
 
/**
 * Dashboard principal de DON APARATO.
 * Muestra KPIs, gráficos de ventas (diario / semanal / mensual)
 * y accesos rápidos a los módulos del sistema.
 */
public class VentanaInicio extends JFrame {
 
    private Coordinador coordinador;
 
    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color C_BG       = new Color(15,  22,  40);
    private static final Color C_SURFACE  = new Color(22,  32,  56);
    private static final Color C_SURFACE2 = new Color(28,  40,  68);
    private static final Color C_BORDER   = new Color(45,  60,  95);
    private static final Color C_GOLD     = new Color(230, 160, 30);
    private static final Color C_GOLD2    = new Color(255, 195, 60);
    private static final Color C_GREEN    = new Color(40,  190, 110);
    private static final Color C_BLUE     = new Color(60,  130, 230);
    private static final Color C_PURPLE   = new Color(150, 70,  200);
    private static final Color C_RED      = new Color(220, 70,  70);
    private static final Color C_TEXT     = new Color(220, 228, 245);
    private static final Color C_TEXT2    = new Color(140, 158, 195);
    private static final Color C_CARD_V   = new Color(30,  50,  95);
    private static final Color C_CARD_P   = new Color(20,  80,  55);
    private static final Color C_CARD_C   = new Color(80,  25,  90);
 
    // ── Fuentes ───────────────────────────────────────────────────────────────
    private static final Font F_TITLE   = new Font("SansSerif", Font.BOLD,  22);
    private static final Font F_SUB     = new Font("SansSerif", Font.PLAIN, 11);
    private static final Font F_KPI_VAL = new Font("SansSerif", Font.BOLD,  22);
    private static final Font F_KPI_LBL = new Font("SansSerif", Font.PLAIN, 10);
    private static final Font F_CHART_T = new Font("SansSerif", Font.BOLD,  12);
    private static final Font F_BTN     = new Font("SansSerif", Font.BOLD,  12);
    private static final Font F_BADGE   = new Font("SansSerif", Font.BOLD,  11);
 
    // ── Estado del gráfico ────────────────────────────────────────────────────
    private enum Periodo { DIARIO, SEMANAL, MENSUAL }
    private Periodo periodoActivo = Periodo.SEMANAL;
 
    // ── Datos del dashboard ───────────────────────────────────────────────────
    private int    kpiVentas    = 0;
    private double kpiRecaudado = 0;
    private double kpiDescuentos= 0;
    private int    kpiClientes  = 0;
    private int    kpiProductos = 0;
    private String kpiTopProd   = "—";
    private List<Object[]> datosGrafico = new ArrayList<>();
 
    // ── Refs a componentes que se actualizan ──────────────────────────────────
    private JLabel lblKpiVentas, lblKpiRecaudado, lblKpiDescuentos,
                   lblKpiClientes, lblKpiProductos, lblKpiTop;
    private PanelGrafico panelGrafico;
    private JButton btnDiario, btnSemanal, btnMensual;
 
    // ─────────────────────────────────────────────────────────────────────────
 
    public VentanaInicio(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
        cargarDatos();
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // INIT
    // ═════════════════════════════════════════════════════════════════════════
    private void initComponents() {
        setTitle("SURTI MAS – Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 780);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(1100, 680));
 
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(C_BG);
        setContentPane(root);
 
        root.add(crearHeader(),  BorderLayout.NORTH);
        root.add(crearCuerpo(),  BorderLayout.CENTER);
        root.add(crearFooter(),  BorderLayout.SOUTH);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // HEADER
    // ═════════════════════════════════════════════════════════════════════════
    private JPanel crearHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_SURFACE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, C_GOLD),
                new EmptyBorder(14, 28, 14, 28)));
 
        // Izquierda: logo + título
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        left.setOpaque(false);
 
        JLabel lblDot = new JLabel("◆");
        lblDot.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblDot.setForeground(C_GOLD);
 
        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
 
        JLabel lblTitulo = new JLabel("SURTI MAS");
        lblTitulo.setFont(F_TITLE);
        lblTitulo.setForeground(C_GOLD);
 
        JLabel lblSub = new JLabel("Sistema Integral de Gestión Comercial");
        lblSub.setFont(F_SUB);
        lblSub.setForeground(C_TEXT2);
 
        textos.add(lblTitulo);
        textos.add(lblSub);
        left.add(lblDot);
        left.add(textos);
 
        // Derecha: badge versión + botón refrescar
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
 
        JButton btnRefresh = crearBotonCompacto("⟳ Actualizar", C_BLUE, 120, 30);
        btnRefresh.addActionListener(e -> cargarDatos());
 
        JLabel badge = new JLabel("v1.0");
        badge.setFont(F_BADGE);
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(C_GOLD);
        badge.setBorder(new EmptyBorder(4, 10, 4, 10));
 
        right.add(btnRefresh);
        right.add(badge);
 
        header.add(left,  BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        return header;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // CUERPO
    // ═════════════════════════════════════════════════════════════════════════
    private JPanel crearCuerpo() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(C_BG);
        panel.setBorder(new EmptyBorder(16, 20, 8, 20));
 
        // Fila superior: KPIs
        panel.add(crearFilaKPIs(), BorderLayout.NORTH);
 
        // Centro: gráfico (izq) + módulos (der)
        JPanel centro = new JPanel(new BorderLayout(12, 0));
        centro.setOpaque(false);
        centro.add(crearPanelGrafico(), BorderLayout.CENTER);
        centro.add(crearPanelModulos(), BorderLayout.EAST);
 
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }
 
    // ── Fila de KPIs ─────────────────────────────────────────────────────────
    private JPanel crearFilaKPIs() {
        JPanel fila = new JPanel(new GridLayout(1, 6, 10, 0));
        fila.setOpaque(false);
        fila.setPreferredSize(new Dimension(0, 90));
 
        lblKpiVentas     = new JLabel("0");
        lblKpiRecaudado  = new JLabel("$ 0");
        lblKpiDescuentos = new JLabel("$ 0");
        lblKpiClientes   = new JLabel("0");
        lblKpiProductos  = new JLabel("0");
        lblKpiTop        = new JLabel("—");
 
        fila.add(crearTarjetaKPI("Total Ventas",     lblKpiVentas,     "🛒", C_GOLD));
        fila.add(crearTarjetaKPI("Recaudado",        lblKpiRecaudado,  "💰", C_GREEN));
        fila.add(crearTarjetaKPI("Descuentos",       lblKpiDescuentos, "🏷", C_RED));
        fila.add(crearTarjetaKPI("Clientes",         lblKpiClientes,   "👤", C_BLUE));
        fila.add(crearTarjetaKPI("Productos",        lblKpiProductos,  "📦", C_PURPLE));
        fila.add(crearTarjetaKPI("Más vendido",      lblKpiTop,        "⭐", C_GOLD2));
 
        return fila;
    }
 
    private JPanel crearTarjetaKPI(String label, JLabel lblValor, String icono, Color accent) {
        JPanel card = new JPanel(new BorderLayout(0, 4)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                // Barra de acento superior
                g2.setColor(accent);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), 3, 2, 2));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(12, 14, 10, 14));
 
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
 
        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("SansSerif", Font.PLAIN, 16));
 
        JLabel lblLbl = new JLabel(label);
        lblLbl.setFont(F_KPI_LBL);
        lblLbl.setForeground(C_TEXT2);
 
        top.add(lblIcono, BorderLayout.WEST);
        top.add(lblLbl,   BorderLayout.EAST);
 
        lblValor.setFont(F_KPI_VAL);
        lblValor.setForeground(accent);
 
        card.add(top,     BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
    }
 
    // ── Panel del gráfico ─────────────────────────────────────────────────────
    private JPanel crearPanelGrafico() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 10)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                g2.dispose();
            }
        };
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(16, 18, 14, 18));
 
        // Cabecera del gráfico
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
 
        JLabel lblTit = new JLabel("📈  Ventas por Período");
        lblTit.setFont(F_CHART_T);
        lblTit.setForeground(C_TEXT);
 
        // Botones de período
        JPanel btnsPeriodo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnsPeriodo.setOpaque(false);
 
        btnDiario  = crearBotonPeriodo("7 días",   Periodo.DIARIO);
        btnSemanal = crearBotonPeriodo("4 semanas", Periodo.SEMANAL);
        btnMensual = crearBotonPeriodo("6 meses",  Periodo.MENSUAL);
 
        btnsPeriodo.add(btnDiario);
        btnsPeriodo.add(btnSemanal);
        btnsPeriodo.add(btnMensual);
 
        cabecera.add(lblTit,       BorderLayout.WEST);
        cabecera.add(btnsPeriodo,  BorderLayout.EAST);
 
        // Panel del gráfico en sí
        panelGrafico = new PanelGrafico();
        panelGrafico.setOpaque(false);
 
        contenedor.add(cabecera,    BorderLayout.NORTH);
        contenedor.add(panelGrafico, BorderLayout.CENTER);
        return contenedor;
    }
 
    private JButton crearBotonPeriodo(String texto, Periodo periodo) {
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
                boolean activo = periodoActivo == periodo;
                g2.setColor(activo ? C_GOLD : (hover ? C_BORDER : C_SURFACE2));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 10));
        btn.setForeground(periodoActivo == periodo ? C_BG : C_TEXT2);
        btn.setPreferredSize(new Dimension(78, 24));
        btn.addActionListener(e -> {
            periodoActivo = periodo;
            // Actualizar color de texto de todos los botones
            actualizarColorBotonesPeriodo();
            cargarGrafico();
        });
        return btn;
    }
 
    private void actualizarColorBotonesPeriodo() {
        btnDiario.setForeground(periodoActivo == Periodo.DIARIO   ? C_BG : C_TEXT2);
        btnSemanal.setForeground(periodoActivo == Periodo.SEMANAL ? C_BG : C_TEXT2);
        btnMensual.setForeground(periodoActivo == Periodo.MENSUAL ? C_BG : C_TEXT2);
        btnDiario.repaint();
        btnSemanal.repaint();
        btnMensual.repaint();
    }
 
    // ── Panel de módulos (botones compactos) ──────────────────────────────────
    private JPanel crearPanelModulos() {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_SURFACE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        panel.setPreferredSize(new Dimension(210, 0));
 
        JLabel lblTit = new JLabel("Módulos");
        lblTit.setFont(F_CHART_T);
        lblTit.setForeground(C_TEXT);
        lblTit.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTit.setBorder(new EmptyBorder(0, 0, 14, 0));
 
        panel.add(lblTit);
        panel.add(crearBotonModulo("🛒  Ventas",     "Registrar compras",    C_CARD_V, C_GOLD,   () -> coordinador.abrirVentanaPrincipal()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearBotonModulo("📦  Productos",  "Catálogo e inventario", C_CARD_P, C_GREEN,  () -> coordinador.abrirVentanaProductos()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(crearBotonModulo("👤  Clientes",   "Gestión de clientes",   C_CARD_C, C_PURPLE, () -> coordinador.abrirVentanaClientes()));
        panel.add(Box.createVerticalGlue());
 
        // Nota de acceso rápido
        JLabel lblHint = new JLabel("<html><center>Haz clic en una<br>tarjeta para acceder</center></html>");
        lblHint.setFont(new Font("SansSerif", Font.ITALIC, 10));
        lblHint.setForeground(C_TEXT2);
        lblHint.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblHint);
 
        return panel;
    }
 
    private JPanel crearBotonModulo(String titulo, String desc, Color bg, Color accent, Runnable accion) {
        JPanel btn = new JPanel(new BorderLayout(0, 3)) {
            private boolean hover = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                    @Override public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                    @Override public void mouseClicked(MouseEvent e) { accion.run(); }
                });
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color fondo = hover ? bg.brighter() : bg;
                g2.setColor(fondo);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                // Barra izquierda de acento
                g2.setColor(accent);
                g2.fill(new RoundRectangle2D.Float(0, 0, 4, getHeight(), 3, 3));
                g2.dispose();
            }
        };
        btn.setOpaque(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(10, 14, 10, 10));
 
        JLabel lblT = new JLabel(titulo);
        lblT.setFont(F_BTN);
        lblT.setForeground(Color.WHITE);
 
        JLabel lblD = new JLabel(desc);
        lblD.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblD.setForeground(new Color(190, 210, 235));
 
        btn.add(lblT, BorderLayout.CENTER);
        btn.add(lblD, BorderLayout.SOUTH);
        return btn;
    }
 
    // ── Footer ────────────────────────────────────────────────────────────────
    private JPanel crearFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(C_SURFACE);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, C_BORDER));
        footer.setPreferredSize(new Dimension(0, 30));
        JLabel lbl = new JLabel("© 2024 SURTI MAS – Todos los derechos reservados");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lbl.setForeground(C_TEXT2);
        footer.add(lbl);
        return footer;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // CARGA DE DATOS
    // ═════════════════════════════════════════════════════════════════════════
    private void cargarDatos() {
        try {
            kpiVentas     = coordinador.totalVentas();
            kpiRecaudado  = coordinador.totalRecaudado();
            kpiDescuentos = coordinador.totalDescuentos();
            kpiClientes   = coordinador.totalClientes();
            kpiProductos  = coordinador.totalProductos();
            kpiTopProd    = coordinador.productoTopVentas();
        } catch (Exception ex) {
            // Si BD no está disponible, los KPIs quedan en 0
        }
 
        lblKpiVentas.setText(String.valueOf(kpiVentas));
        lblKpiRecaudado.setText(formatear(kpiRecaudado));
        lblKpiDescuentos.setText(formatear(kpiDescuentos));
        lblKpiClientes.setText(String.valueOf(kpiClientes));
        lblKpiProductos.setText(String.valueOf(kpiProductos));
        String top = kpiTopProd != null && kpiTopProd.length() > 14
                ? kpiTopProd.substring(0, 13) + "…" : kpiTopProd;
        lblKpiTop.setText(top != null ? top : "—");
 
        cargarGrafico();
    }
 
    private void cargarGrafico() {
        try {
            switch (periodoActivo) {
                case DIARIO:  datosGrafico = coordinador.ventasDiario();  break;
                case SEMANAL: datosGrafico = coordinador.ventasSemanal(); break;
                case MENSUAL: datosGrafico = coordinador.ventasMensual(); break;
                default:      datosGrafico = coordinador.ventasSemanal(); break;
            }
        } catch (Exception ex) {
            datosGrafico = new ArrayList<>();
        }
        panelGrafico.setDatos(datosGrafico, periodoActivo);
        panelGrafico.repaint();
    }
 
    private static String formatear(double valor) {
        if (valor >= 1_000_000)
            return String.format("$ %.1fM", valor / 1_000_000);
        if (valor >= 1_000)
            return String.format("$ %.0fK", valor / 1_000);
        return String.format("$ %.0f", valor);
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // FÁBRICA
    // ═════════════════════════════════════════════════════════════════════════
    private JButton crearBotonCompacto(String texto, Color color, int w, int h) {
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
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, 11));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(w, h));
        return btn;
    }
 
    // ═════════════════════════════════════════════════════════════════════════
    // PANEL GRÁFICO — barras dibujadas con Graphics2D + clic interactivo
    // ═════════════════════════════════════════════════════════════════════════
    private class PanelGrafico extends JPanel {
 
        private List<Object[]> datos      = new ArrayList<>();
        private Periodo        periodo    = Periodo.SEMANAL;
        private int            barraHover = -1;
        private int            barraSelec = -1;
 
        // Geometría calculada en paint y reutilizada en hit-test
        private int[] xBarras;
        private int[] hBarras;
        private int   barW, padL, padT, grafH;
 
        public void setDatos(List<Object[]> datos, Periodo periodo) {
            this.datos      = datos != null ? datos : new ArrayList<>();
            this.periodo    = periodo;
            this.barraHover = -1;
            this.barraSelec = -1;
        }
 
        // ── Listeners de ratón registrados en el bloque de instancia ─────────
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override public void mouseMoved(MouseEvent e) {
                    int idx = indiceBarra(e.getX(), e.getY());
                    if (idx != barraHover) { barraHover = idx; repaint(); }
                }
            });
 
            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) {
                    int idx = indiceBarra(e.getX(), e.getY());
                    if (idx >= 0 && idx < datos.size()) {
                        barraSelec = idx;
                        repaint();
                        mostrarResumen(idx, e.getLocationOnScreen());
                    }
                }
                @Override public void mouseExited(MouseEvent e) {
                    barraHover = -1; repaint();
                }
            });
        }
 
        /** Devuelve el índice de la barra bajo el punto dado, o -1. */
        private int indiceBarra(int mx, int my) {
            if (xBarras == null || datos.isEmpty()) return -1;
            for (int i = 0; i < xBarras.length; i++) {
                int y = padT + grafH - hBarras[i];
                if (mx >= xBarras[i] && mx <= xBarras[i] + barW
                        && my >= y && my <= padT + grafH)
                    return i;
            }
            return -1;
        }
 
        /** Muestra el popup de resumen flotante junto al cursor. */
        private void mostrarResumen(int idx, Point screenPos) {
            Object[] punto   = datos.get(idx);
            String   fecha   = punto[0].toString();
            int      nVentas = (int)    punto[1];
            double   total   = (double) punto[2];
 
            String etiqPeriodo;
            switch (periodo) {
                case DIARIO:  etiqPeriodo = "Día";    break;
                case SEMANAL: etiqPeriodo = "Semana"; break;
                default:      etiqPeriodo = "Mes";    break;
            }
 
            JDialog popup = new JDialog((Frame) SwingUtilities.getWindowAncestor(PanelGrafico.this));
            popup.setUndecorated(true);
            popup.setAlwaysOnTop(true);
            popup.setFocusableWindowState(false);
 
            JPanel contenido = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(18, 26, 48, 245));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                    g2.setColor(C_GOLD);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 13, 13));
                    g2.dispose();
                }
            };
            contenido.setOpaque(false);
            contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
            contenido.setBorder(new EmptyBorder(12, 16, 12, 16));
 
            JLabel lblTit = new JLabel(etiqPeriodo + ": " + abreviarFechaLarga(fecha, periodo));
            lblTit.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblTit.setForeground(C_GOLD);
            lblTit.setAlignmentX(Component.LEFT_ALIGNMENT);
 
            JSeparator sep = new JSeparator();
            sep.setForeground(new Color(60, 80, 130));
            sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
 
            contenido.add(lblTit);
            contenido.add(Box.createVerticalStrut(8));
            contenido.add(sep);
            contenido.add(Box.createVerticalStrut(8));
            contenido.add(filaResumen("🛒  Ventas realizadas",
                    nVentas + (nVentas == 1 ? " compra" : " compras"), C_GOLD2));
            contenido.add(Box.createVerticalStrut(6));
            contenido.add(filaResumen("💰  Total recaudado",
                    Coordinador.formatearMoneda(total), C_GREEN));
            contenido.add(Box.createVerticalStrut(6));
            contenido.add(filaResumen("📊  Ticket promedio",
                    nVentas > 0 ? Coordinador.formatearMoneda(total / nVentas) : "—", C_BLUE));
 
            popup.add(contenido);
            popup.pack();
 
            // Posicionar cerca del cursor sin salirse de pantalla
            int px = screenPos.x + 14;
            int py = screenPos.y - popup.getHeight() / 2;
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            if (px + popup.getWidth()  > screen.width)  px = screenPos.x - popup.getWidth() - 14;
            if (py + popup.getHeight() > screen.height) py = screen.height - popup.getHeight() - 10;
            if (py < 0) py = 10;
 
            popup.setLocation(px, py);
            popup.setVisible(true);
 
            // Cerrar al siguiente clic o al salir del panel
            addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e) { popup.dispose(); removeMouseListener(this); }
                @Override public void mouseExited(MouseEvent e)  { popup.dispose(); removeMouseListener(this); }
            });
        }
 
        /** Fila etiqueta + valor alineada en BorderLayout. */
        private JPanel filaResumen(String label, String valor, Color colorValor) {
            JPanel fila = new JPanel(new BorderLayout(12, 0));
            fila.setOpaque(false);
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel lblL = new JLabel(label);
            lblL.setFont(new Font("SansSerif", Font.PLAIN, 11));
            lblL.setForeground(new Color(160, 175, 210));
            JLabel lblV = new JLabel(valor);
            lblV.setFont(new Font("SansSerif", Font.BOLD, 12));
            lblV.setForeground(colorValor);
            fila.add(lblL, BorderLayout.WEST);
            fila.add(lblV, BorderLayout.EAST);
            return fila;
        }
 
        /** Versión larga de la fecha para el título del popup. */
        private String abreviarFechaLarga(String fecha, Periodo p) {
            if (fecha == null || fecha.length() < 10) return fecha != null ? fecha : "";
            String[] partes = fecha.split("-");
            if (partes.length < 3) return fecha;
            String[] mesesL = {"Enero","Febrero","Marzo","Abril","Mayo","Junio",
                               "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
            int mesIdx = 0;
            try { mesIdx = Integer.parseInt(partes[1]) - 1; } catch (NumberFormatException ignored) {}
            String nomMes = (mesIdx >= 0 && mesIdx < 12) ? mesesL[mesIdx] : partes[1];
            switch (p) {
                case MENSUAL: return nomMes + " " + partes[0];
                case SEMANAL: return "Semana del " + partes[2] + " " + nomMes;
                default:      return partes[2] + " de " + nomMes + " " + partes[0];
            }
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
 
            int W = getWidth(), H = getHeight();
            padL = 55; int padR = 16; padT = 24; int padB = 40;
            int grafW = W - padL - padR;
            grafH = H - padT - padB;
 
            // Fondo
            g2.setColor(C_SURFACE2);
            g2.fillRoundRect(padL - 8, padT - 8, grafW + 16, grafH + 16, 10, 10);
 
            if (datos.isEmpty()) {
                g2.setColor(C_TEXT2);
                g2.setFont(new Font("SansSerif", Font.ITALIC, 13));
                String msg = "No hay ventas registradas en este período";
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(msg, (W - fm.stringWidth(msg)) / 2, H / 2);
                g2.dispose();
                return;
            }
 
            // Escala Y
            double maxVal = datos.stream().mapToDouble(d -> (double) d[2]).max().orElse(1);
            if (maxVal <= 0) maxVal = 1;
            maxVal *= 1.15;
 
            // Cuadrícula
            int nLineas = 4;
            for (int i = 0; i <= nLineas; i++) {
                int y = padT + grafH - (int)((double) i / nLineas * grafH);
                g2.setColor(new Color(60, 80, 120));
                g2.setStroke(new BasicStroke(0.6f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 1f, new float[]{4f, 4f}, 0f));
                g2.drawLine(padL, y, padL + grafW, y);
                g2.setStroke(new BasicStroke(1));
                g2.setColor(C_TEXT2);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                double val = maxVal * i / nLineas;
                String lbl = val >= 1_000_000 ? String.format("%.1fM", val / 1_000_000)
                           : val >= 1_000     ? String.format("%.0fK", val / 1_000)
                           :                    String.format("%.0f",   val);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(lbl, padL - fm.stringWidth(lbl) - 5, y + 4);
            }
 
            // Geometría de barras
            int n = datos.size();
            barW = Math.min(50, Math.max(8, grafW / Math.max(n * 2, 1) - 2));
            int gap = Math.max(4, (grafW - barW * n) / (n + 1));
            if (gap * (n + 1) + barW * n > grafW) {
                barW = Math.max(6, (grafW - (n + 1) * 4) / n);
                gap  = Math.max(4, (grafW - barW * n) / (n + 1));
            }
 
            xBarras = new int[n];
            hBarras = new int[n];
 
            g2.setStroke(new BasicStroke(1));
            for (int i = 0; i < n; i++) {
                Object[] punto = datos.get(i);
                double total  = (double) punto[2];
                int    nVtas  = (int)    punto[1];
                String fecha  = punto[0].toString();
 
                int bH = (int)((total / maxVal) * grafH);
                bH = Math.max(bH, total > 0 ? 4 : 0);
                int bX = padL + gap + i * (barW + gap);
                int bY = padT + grafH - bH;
 
                xBarras[i] = bX;
                hBarras[i] = bH;
 
                boolean esHover = (i == barraHover);
                boolean esSelec = (i == barraSelec);
 
                if (bH > 0) {
                    Color cTop = esSelec ? Color.WHITE
                               : esHover ? C_GOLD2.brighter() : C_GOLD2;
                    Color cBot = esSelec ? C_GOLD
                               : esHover ? C_GOLD : C_GOLD.darker();
                    g2.setPaint(new GradientPaint(bX, bY, cTop, bX, padT + grafH, cBot));
                    g2.fill(new RoundRectangle2D.Float(bX, bY, barW, bH, 5, 5));
 
                    // Brillo
                    g2.setColor(new Color(255, 255, 255, esHover ? 80 : 50));
                    g2.fill(new RoundRectangle2D.Float(bX + 1, bY + 1, barW - 2, Math.min(bH - 1, 8), 4, 4));
 
                    // Borde blanco si seleccionada
                    if (esSelec) {
                        g2.setColor(Color.WHITE);
                        g2.setStroke(new BasicStroke(1.5f));
                        g2.draw(new RoundRectangle2D.Float(bX, bY, barW, bH, 5, 5));
                        g2.setStroke(new BasicStroke(1));
                    }
 
                    // Nº ventas encima
                    g2.setColor(esHover || esSelec ? Color.WHITE : C_GOLD2);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                    String valStr = nVtas + (nVtas == 1 ? " vta" : " vtas");
                    FontMetrics fmV = g2.getFontMetrics();
                    int tx = bX + (barW - fmV.stringWidth(valStr)) / 2;
                    g2.drawString(valStr, Math.max(padL, tx), Math.max(padT + 12, bY - 4));
                }
 
                // Etiqueta eje X (dorada si hover/selec)
                g2.setColor(esHover || esSelec ? C_GOLD : C_TEXT2);
                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                String etiq = abreviarFecha(fecha, periodo);
                FontMetrics fmX = g2.getFontMetrics();
                int ex = bX + (barW - fmX.stringWidth(etiq)) / 2;
                g2.drawString(etiq, Math.max(padL, ex), padT + grafH + 16);
            }
 
            // Ejes
            g2.setColor(C_BORDER);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawLine(padL, padT + grafH, padL + grafW, padT + grafH);
            g2.drawLine(padL, padT, padL, padT + grafH);
 
            g2.dispose();
        }
 
        private String abreviarFecha(String fecha, Periodo p) {
            if (fecha == null || fecha.length() < 10) return fecha != null ? fecha : "";
            String[] partes = fecha.split("-");
            if (partes.length < 3) return fecha;
            String[] meses = {"Ene","Feb","Mar","Abr","May","Jun",
                              "Jul","Ago","Sep","Oct","Nov","Dic"};
            int mesIdx = 0;
            try { mesIdx = Integer.parseInt(partes[1]) - 1; } catch (NumberFormatException ignored) {}
            String nomMes = (mesIdx >= 0 && mesIdx < 12) ? meses[mesIdx] : partes[1];
            switch (p) {
                case MENSUAL: return nomMes + "/" + partes[0].substring(2);
                default:      return partes[2] + " " + nomMes;
            }
        }
    }
}