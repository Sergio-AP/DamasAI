import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Panel extends JPanel{
	private Color color2=Color.BLACK;
	private Color color1=Color.BLACK;
	private Image fondo;
	private JButton rst;
	private VentanaTablero win;
	public Panel(VentanaTablero win){
		super();
		this.win=win;
		this.setLayout(null);
		this.setPreferredSize(new Dimension(750, 750));
		Tablero tablero = new Tablero(this);
		this.add(tablero);
		tablero.setBounds(27,50,696,696);
		rst = new JButton("Reiniciar");
		rst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				rst.setActionCommand("Open");
				String cmd = e.getActionCommand();
				if(cmd.equals("Open")){
                                    win.dispose();
                                    new VentanaTablero();
				}
			}
		});
		
		
		fondo=new ImageIcon("fondo.jpeg" ).getImage();
		setColor(1);

	}
	public void paintComponent(Graphics g){
		g.drawImage(fondo, 0, 0,750,750, this);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvatica",100,40));
		g.setColor(color1);
		g.drawString("JUGADOR 1", 27, 40);
		g.setColor(color2);
		g.drawString("JUGADOR 2", 500, 40);
                this.add(rst);
                rst.setBounds(320,2,110,44);
                rst.setBackground(new Color(250,250,0));
                rst.setOpaque(true);
                rst.setBorderPainted(false);
                
	}

	public void setColor(int turno) {
		if(turno==-1){
                    this.color2 = Color.RED;
                    this.color1=Color.BLACK;
                }
		if(turno==1){
                    this.color2 = Color.BLACK;
                    this.color1=new Color(30,50,255);
                }
                repaint();
	}


}
