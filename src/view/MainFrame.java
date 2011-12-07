package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import p2p.Broadcaster;
import p2p.DomeinController;
import p2p.FileTableModel;
import p2p.StatusMessage;
import p2p.UserListModel;

/**
 *
 * @author Jimmy
 */
public class MainFrame extends JFrame implements ActionListener, WindowListener, KeyListener {

    private DomeinController dc;
    private FileTableModel model;
    private StatusMessage statmes = new StatusMessage();

    /** Creates new form HoofdFrame */
    public MainFrame(final DomeinController dc) {

        this.dc = dc;
        model = new FileTableModel(dc);
        this.addWindowListener(this);
        initComponents();
        setTableModel();
        setListModel();

        filterTextField.addKeyListener(this);
        toggleLog.setSelected(true);
        toggleLog.addActionListener(this);
        downloadButton.addActionListener(this);
        jTable1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = jTable1.rowAtPoint(e.getPoint());
                    int col = jTable1.columnAtPoint(e.getPoint());
                    if (col == 0) {
                        dc.sendDownloadRequest(jTable1.getValueAt(row, col).toString(), model.getIp(row));
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        welcomeLabel = new javax.swing.JLabel();
        availableLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        topPanel = new javax.swing.JPanel();
        toggleLog = new javax.swing.JToggleButton();
        logoLabel = new javax.swing.JLabel();
        statusPane = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        statusArea = new StatusTextArea(statmes);
        
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        downloadButton = new javax.swing.JButton();
        filterTextField = new javax.swing.JTextField();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemFolder = new javax.swing.JMenuItem();
        jMenuItemDownloads = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tsjakka!");
        setBackground(new java.awt.Color(153, 153, 153));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        jPanel.setBackground(new java.awt.Color(0, 0, 102));

        leftPanel.setBackground(new java.awt.Color(0, 0, 102));
        leftPanel.setPreferredSize(new java.awt.Dimension(174, 495));

        welcomeLabel.setBackground(new java.awt.Color(204, 204, 255));
        welcomeLabel.setFont(new java.awt.Font("Stencil", 0, 16));
        welcomeLabel.setForeground(new java.awt.Color(255, 255, 255));
        try {
            welcomeLabel.setText("Welcome " + dc.getUsername());
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        availableLabel.setFont(new java.awt.Font("Stencil", 0, 12));
        availableLabel.setForeground(new java.awt.Color(255, 255, 255));
        availableLabel.setText("available users:");

        userList.setBackground(new java.awt.Color(0, 0, 0));
        userList.setFont(new java.awt.Font("Stencil", 0, 12));
        userList.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(userList);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
                leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup().addContainerGap().addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(availableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE).addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)).addContainerGap()));
        leftPanelLayout.setVerticalGroup(
                leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup().addGap(23, 23, 23).addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE).addComponent(availableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(29, 29, 29)));

        topPanel.setBackground(new java.awt.Color(0, 0, 102));

        toggleLog.setFont(new java.awt.Font("Stencil", 0, 11));
        toggleLog.setText("Sharing");

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/logoKleiner.png"))); // NOI18N

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
                topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(topPanelLayout.createSequentialGroup().addContainerGap().addComponent(logoLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(toggleLog).addContainerGap(444, Short.MAX_VALUE)));
        topPanelLayout.setVerticalGroup(
                topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(logoLabel, javax.swing.GroupLayout.Alignment.TRAILING).addComponent(toggleLog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE));

        statusPane.setBackground(new java.awt.Color(204, 204, 255));

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        statusArea.setBackground(new java.awt.Color(0, 0, 0));
        statusArea.setColumns(20);
        statusArea.setEditable(false);
        statusArea.setForeground(new java.awt.Color(102, 255, 0));
        statusArea.setRows(4);
        jScrollPane3.setViewportView(statusArea);

        javax.swing.GroupLayout statusPaneLayout = new javax.swing.GroupLayout(statusPane);
        statusPane.setLayout(statusPaneLayout);
        statusPaneLayout.setHorizontalGroup(
                statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE));
        statusPaneLayout.setVerticalGroup(
                statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE));

        jTable1.setFont(new java.awt.Font("Stencil", 0, 11));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String[]{
                    "File name", "File size", "Owned"
                }) {

            Class[] types = new Class[]{
                java.lang.String.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getColumnModel().getColumn(2).setMinWidth(50);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(150);

        downloadButton.setFont(new java.awt.Font("Stencil", 0, 11));
        downloadButton.setText("Download");

        filterTextField.setToolTipText("Enter your filter here.");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelLayout.createSequentialGroup().addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelLayout.createSequentialGroup().addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanelLayout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))).addGroup(jPanelLayout.createSequentialGroup().addGap(211, 211, 211).addComponent(downloadButton)))).addGroup(jPanelLayout.createSequentialGroup().addGap(10, 10, 10).addComponent(statusPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanelLayout.setVerticalGroup(
                jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup().addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanelLayout.createSequentialGroup().addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(filterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(5, 5, 5).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(downloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(statusPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

        jMenuFile.setText("File");

        jMenuItemFolder.setText("Change share folder");
        jMenuItemFolder.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFolderActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemFolder);
        
        jMenuItemDownloads.setText("Change download folder");
        jMenuItemDownloads.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownloadsActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemDownloads);

        jMenuBar.add(jMenuFile);

        jMenuHelp.setText("Help");

        jMenuItemAbout.setText("About");
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }

    private void jMenuItemFolderActionPerformed(java.awt.event.ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(dc.getSharedDirectory());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            dc.changeSharedDir(fc.getSelectedFile().toString());
        }
    }
    
    private void jMenuItemDownloadsActionPerformed(java.awt.event.ActionEvent evt) {
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(dc.getDownloadsDirectory());
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            dc.changeDownloadsDir(fc.getSelectedFile().toString());
        }
    }
    
    private javax.swing.JLabel availableLabel;
    private javax.swing.JButton downloadButton;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemFolder;
    private javax.swing.JMenuItem jMenuItemDownloads;
    private javax.swing.JPanel jPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private StatusTextArea statusArea;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPanel statusPane;
    private javax.swing.JToggleButton toggleLog;
    private javax.swing.JPanel topPanel;
    private javax.swing.JList userList;
    private javax.swing.JLabel welcomeLabel;

    private void setTableModel() {
        jTable1.setModel(model);
    }

    private void setListModel() {
        userList.setModel(new UserListModel(dc));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toggleLog) {
            if (toggleLog.isSelected()) {
                dc.signin();
                toggleLog.setText("Sharing");
                dc.addStatusToArea("You are sharing files.");
                
            }

            if (!(toggleLog.isSelected())) {
                dc.signout();
                toggleLog.setText("Not sharing");
                dc.addStatusToArea("You are no longer sharing files.");
                
            }
        }
        if (e.getSource() == downloadButton) {
            int row = jTable1.getSelectedRow();
            int col = jTable1.getSelectedColumn();
            if (col == 0) {
                dc.sendDownloadRequest(jTable1.getValueAt(row, col).toString(), model.getIp(row));
            }            
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(new Broadcaster(false));
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == e.VK_ENTER) {
            if (filterTextField.getText().trim().equals("")) {
                dc.emptyList();
                dc.addStatusToArea("Filter: none");                
            } else {
                dc.addToFilterList(filterTextField.getText());
                dc.addStatusToArea("Filter: " + dc.getFilterList());
                System.out.println("AddtoStatusArea");               
            }
            filterTextField.setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
