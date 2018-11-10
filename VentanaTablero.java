import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VentanaTablero extends JFrame{
	
	public VentanaTablero(){
		super ("Damas Inglesas");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(new Panel(this));
		this.pack();
		this.setVisible(true);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		VentanaTablero ven = new VentanaTablero();
	}
}
