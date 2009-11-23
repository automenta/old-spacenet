/*
 * Copyright (c) 2003-2009 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package automenta.spacenet.space.jme.video;




abstract public class SwingJme  {



    
//    static final Logger logger = Logger.getLogger(SwingJme.class);
//
//    static {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {    	logger.error(e);      }
//    }
//
//
//    private SwingFrame frame;
//	private DisplaySystem displaySystem;
//    JmeSwingLoop swingLoop;
//
//	private Vector2 pixelSize = new Vector2(640, 480);
//
//    public SwingJme(Spacetime spaceTime) {
//    	super(spaceTime);
//
//        frame = new SwingFrame();
//        // center the frame
//        frame.setLocationRelativeTo(null);
//        // show frame
//        frame.setVisible(true);
//    }
//
//
//    // **************** SWING FRAME ****************
//
//    // Our custom Swing frame... Nothing really special here.
//    class SwingFrame extends JFrame {
//        private static final long serialVersionUID = 1L;
//
//        JPanel contentPane;
//        JPanel mainPanel = new JPanel();
//        LWJGLCanvas canvas = null;
//        JButton coolButton = new JButton();
//        JButton uncoolButton = new JButton();
//        JPanel spPanel = new JPanel();
//        JScrollPane scrollPane = new JScrollPane();
//        JTree jTree1 = new JTree();
//        JCheckBox scaleBox = new JCheckBox("Scale GL Image");
//        JPanel colorPanel = new JPanel();
//        JLabel colorLabel = new JLabel("BG Color:");
//
//
//        // Construct the frame
//        public SwingFrame() {
//            addWindowListener(new WindowAdapter() {
//                @Override
//				public void windowClosing(WindowEvent e) {
//                    dispose();
//                }
//            });
//
//            init();
//            pack();
//        }
//
//        // Component initialization
//        private void init() {
//            contentPane = (JPanel) this.getContentPane();
//            contentPane.setLayout(new BorderLayout());
//
//            mainPanel.setLayout(new GridBagLayout());
//
//            setTitle("JME - SWING INTEGRATION TEST");
//
//            // -------------GL STUFF------------------
//
//            // make the canvas:
//            displaySystem = DisplaySystem.getDisplaySystem(LWJGLSystemProvider.LWJGL_SYSTEM_IDENTIFIER);
//            displaySystem.registerCanvasConstructor("AWT", LWJGLAWTCanvasConstructor.class);
//            canvas = (LWJGLCanvas)displaySystem.createCanvas((int)pixelSize.x(), (int)pixelSize.y());
//            canvas.setUpdateInput(true);
//            canvas.setTargetRate(60);
//
//            // add a listener... if window is resized, we can do something about
//            // it.
//            canvas.addComponentListener(new ComponentAdapter() {
//                @Override
//				public void componentResized(ComponentEvent ce) {
//                    doResize();
//                }
//            });
//
//            // Setup key and mouse input
//            KeyInput.setProvider(KeyInput.INPUT_AWT);
//            KeyListener kl = (KeyListener) KeyInput.get();
//            canvas.addKeyListener(kl);
//            AWTMouseInput.setup(canvas, false);
//
//            // Important! Here is where we add the guts to the panel:
//            swingLoop = new JmeSwingLoop(SwingJme.this, pixelSize);
//            canvas.setImplementor(swingLoop);
//
//            // -----------END OF GL STUFF-------------
//
//            coolButton.setText("Cool Button");
//            uncoolButton.setText("Uncool Button");
//
//            colorPanel.setBackground(java.awt.Color.black);
//            colorPanel.setToolTipText("Click here to change Panel BG color.");
//            colorPanel.setBorder(BorderFactory.createRaisedBevelBorder());
//            colorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
//                @Override
//				public void mouseClicked(MouseEvent e) {
//                    final java.awt.Color color = JColorChooser.showDialog(
//                            SwingFrame.this, "Choose new background color:",
//                            colorPanel.getBackground());
//                    if (color == null)
//                        return;
//                    colorPanel.setBackground(color);
//                    Callable<?> call = new Callable<Object>() {
//                        public Object call() throws Exception {
//                            canvas.setBackground(color);
//                            return null;
//                        }
//                    };
//                    GameTaskQueueManager.getManager().render(call);
//                }
//            });
//
//            scaleBox.setOpaque(false);
//            scaleBox.setSelected(true);
//            scaleBox.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//                    if (canvas != null)
//                        doResize();
//                }
//            });
//
//            spPanel.setLayout(new BorderLayout());
//            contentPane.add(mainPanel, BorderLayout.WEST);
//            mainPanel.add(scaleBox,
//                    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
//                            GridBagConstraints.CENTER,
//                            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
//                                    5), 0, 0));
//            mainPanel.add(colorLabel,
//                    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
//                            GridBagConstraints.CENTER,
//                            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
//                                    5), 0, 0));
//            mainPanel.add(colorPanel, new GridBagConstraints(0, 2, 1, 1, 0.0,
//                    0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
//                    new Insets(5, 5, 0, 5), 25, 25));
//            mainPanel.add(coolButton,
//                    new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
//                            GridBagConstraints.CENTER,
//                            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
//                                    5), 0, 0));
//            mainPanel.add(uncoolButton,
//                    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
//                            GridBagConstraints.CENTER,
//                            GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0,
//                                    5), 0, 0));
//            mainPanel.add(spPanel, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0,
//                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                    new Insets(5, 5, 0, 5), 0, 0));
//            spPanel.add(scrollPane, BorderLayout.CENTER);
//
//            scrollPane.setViewportView(jTree1);
//            canvas.setBounds(0, 0, (int)pixelSize.x(), (int)pixelSize.y());
//            contentPane.add(canvas, BorderLayout.CENTER);
//        }
//
//        protected void doResize() {
//            if (scaleBox != null && scaleBox.isSelected()) {
//                swingLoop.resizeCanvas(canvas.getWidth(), canvas.getHeight());
//                pixelSize.set(canvas.getWidth(), canvas.getHeight());
//            } else {
//                swingLoop.resizeCanvas((int)pixelSize.x(), (int)pixelSize.y());
//            }
//            ((JMECanvas)canvas).makeDirty();
//        }
//
//        // Overridden so we can exit when window is closed
//        @Override
//		protected void processWindowEvent(WindowEvent e) {
//            super.processWindowEvent(e);
//            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
//                System.exit(0);
//            }
//        }
//    }
//
//
//	@Override public DisplaySystem getDisplaySystem() {
//		return displaySystem;
//	}
//
//
//
//	@Override protected Camera getJmeCamera() {
//		return swingLoop.getCamera();
//	}
//
//
//	@Override
//	public Vector2 getPixelSize() {
//		return pixelSize;
//	}
//
//
//	@Override
//	public Node getRootNode() {
//		return swingLoop.getRootNode();
//	}
}
