import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Tablero extends JPanel{

	private Bloque[][] tablero;
	private int turno =1,fichasAzules=12,fichasRojas=12;
	private Panel panel;
	private boolean otrasFichas=true;
        private Bloque[] comer={null,null,null,null};

	public Tablero(Panel panel){
		super();
		this.panel=panel;
		this.setPreferredSize(new Dimension(696,696));
		this.setLayout(null);
		tablero();
	}
	public void tablero(){
		this.tablero = new Bloque[8][4];
		for(int y=0;y<4;y++){
			for(int x=0;x<8;x++){
				if(y==0){
					tablero[x][y]=new Bloque(-1,x,y);
				}
				else if(y==1 && x%2==1){
					tablero[x][y]=new Bloque(-1,x,y);
				}
				else if(y==3){
					tablero[x][y]=new Bloque(1,x,y);
				}
				else if(y==2 && x%2==0){
					tablero[x][y]=new Bloque(1,x,y);
				}
				else{
					tablero[x][y]=new Bloque(0,x,y);
				}
				Bloque t=tablero[x][y];
				this.add(t);
				t.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						Bloque bloque=((Bloque) e.getSource());
                                                
                                                
                                                if(tieneQueComer()){
                                                    otrasFichas=false;
                                                }
                                                if(((Bloque) e.getSource()).isColorSet()){

							((Bloque) e.getSource()).setFicha(tablero[(bloque).getXO()][(bloque).getYO()].getFicha());
							tablero[(bloque).getXO()][(bloque).getYO()].setFicha(0);
							coronarse(bloque);

							if(bloque.isComer()){
								comer(bloque);
								bloque.setComer();
								if(turno==-1){
									fichasAzules--;
								}
								else{
									fichasRojas--;
								}
								if(fichasRojas==0){
									formatear();
									repaint();
									JOptionPane.showMessageDialog(Tablero.this, "Las fichas Azules ganan!!!","GANADOR",JOptionPane.PLAIN_MESSAGE);
								}
								if(fichasAzules==0 ){
									formatear();
									repaint();
									JOptionPane.showMessageDialog(Tablero.this, "Las fichas Rojas ganan!!!","GANADOR",JOptionPane.PLAIN_MESSAGE);                                                
								}
                                                                
								if(seguir(bloque)){
									formatear();
									seguir(bloque);
									otrasFichas=false;
								}
								else{
									turno=-(turno);
									panel.setColor(turno);
									otrasFichas=true;
								}
							}

							else{
								turno=-(turno);
								panel.setColor(turno);
							}
							if(otrasFichas){
								formatear(); 
							}    
						}

						else if(((Bloque) e.getSource()).getFicha()>=turno && turno==1 && otrasFichas){
                                                    if(sinMovimientos()==0){
                                                            JOptionPane.showMessageDialog(Tablero.this, "Las fichas Rojas ganan!!!","GANADOR",JOptionPane.PLAIN_MESSAGE);                                                
                                                    }
                                                    
                                                    formatear();
                                                    mover(bloque);
						}

						else if(((Bloque) e.getSource()).getFicha()<=turno && turno==-1 && otrasFichas){
                                                    if(sinMovimientos()==0){
                                                        JOptionPane.showMessageDialog(Tablero.this, "Las fichas Azules ganan!!!","GANADOR",JOptionPane.PLAIN_MESSAGE);
                                                    }
                                                    formatear();
                                                    mover(bloque);
						}
                                                
                                                
						repaint();
					}
				});
				if(x%2==0){
					t.setBounds(x*87,y*174+87,87,87);
				}
				else{
					t.setBounds(x*87,y*174,87,87);
				}
			}
		}
	}
	public void formatear(){
		for(int y=0;y<4;y++){
			for(int x=0;x<8;x++){
				tablero[x][y].unsetColor();
				tablero[x][y].setComer();
			}
		}
	}
	public void comer(Bloque bloque){
		if(bloque.getXB()<bloque.getXO()){
			if(bloque.getYB()<bloque.getYO()){
				if(bloque.getXO()%2==1)
					tablero[bloque.getXO()-1][bloque.getYO()-1].setFicha(0);
				else
					tablero[bloque.getXO()-1][bloque.getYO()].setFicha(0);
			}
			else{
				if(bloque.getXO()%2==1)
					tablero[bloque.getXO()-1][bloque.getYO()].setFicha(0);
				else
					tablero[bloque.getXO()-1][bloque.getYO()+1].setFicha(0);
			}
		}
		else{
			if(bloque.getYB()<bloque.getYO()){
				if(bloque.getXO()%2==1)
					tablero[bloque.getXO()+1][bloque.getYO()-1].setFicha(0);
				else
					tablero[bloque.getXO()+1][bloque.getYO()].setFicha(0);
			}
			else{
				if(bloque.getXO()%2==1)
					tablero[bloque.getXO()+1][bloque.getYO()].setFicha(0);
				else
					tablero[bloque.getXO()+1][bloque.getYO()+1].setFicha(0);
			}
		}


	}
	public int[] checarBloque(Bloque bloque){
		int x = bloque.getXB();
		int y = bloque.getYB();
		int[] temp = new int[4];

		if((x>0 && x<7)&&(y>0 && y<3)){
			if((x%2)==1){
				temp[0]=tablero[x-1][y-1].getFicha();
				temp[1]=tablero[x+1][y-1].getFicha();
				temp[2]=tablero[x-1][y].getFicha();
				temp[3]=tablero[x+1][y].getFicha();
			}
			if((x%2)==0){
				temp[0]=tablero[x-1][y].getFicha();
				temp[1]=tablero[x+1][y].getFicha();
				temp[2]=tablero[x-1][y+1].getFicha();
				temp[3]=tablero[x+1][y+1].getFicha();
			}
		}
		//checa si la fila es la fila 0
		else if(y==0){
			if((x%2)==1){
				if(x!=7){
					temp[0]=3;
					temp[1]=3;
					temp[2]=tablero[x-1][y].getFicha();
					temp[3]=tablero[x+1][y].getFicha();
				}
				else{
					temp[0]=3;
					temp[1]=3;
					temp[2]=tablero[x-1][y].getFicha();
					temp[3]=3;
				}
			}
			else{
				if(x!=0){
					temp[0]=tablero[x-1][y].getFicha();
					temp[1]=tablero[x+1][y].getFicha();
					temp[2]=tablero[x-1][y+1].getFicha();
					temp[3]=tablero[x+1][y+1].getFicha();
				}
				else{
					temp[0]=3;
					temp[1]=tablero[x+1][y].getFicha();
					temp[2]=3;
					temp[3]=tablero[x+1][y+1].getFicha();
				}
			}

		}
		else if(y==3){
			if((x%2)==0){
				if(x!=0){
					temp[0]=tablero[x-1][y].getFicha();
					temp[1]=tablero[x+1][y].getFicha();
					temp[2]=3;
					temp[3]=3;
				}
				else{
					temp[0]=3;
					temp[1]=tablero[x+1][y].getFicha();
					temp[2]=3;
					temp[3]=3;
				}
			}
			else{
				if(x!=7){
					temp[0]=tablero[x-1][y-1].getFicha();
					temp[1]=tablero[x+1][y-1].getFicha();
					temp[2]=tablero[x-1][y].getFicha();
					temp[3]=tablero[x+1][y].getFicha();
				}
				else{
					temp[0]=tablero[x-1][y-1].getFicha();
					temp[1]=3;
					temp[2]=tablero[x-1][y].getFicha();
					temp[3]=3;
				}
			}
		}

		//checa si la fila es la fila 3
		else if(y>0 && y<3){
			if(x==0){
				temp[0]=3;
				temp[1]=tablero[x+1][y].getFicha();
				temp[2]=3;
				temp[3]=tablero[x+1][y+1].getFicha();
			}
			else if(x==7){
				temp[0]=tablero[x-1][y-1].getFicha();
				temp[1]=3;
				temp[2]=tablero[x-1][y].getFicha();
				temp[3]=3;
			}
		}
		else{
			System.out.println("Error al checar");
			return null;
		}

		return temp;
	}

	public int mover(Bloque bloque){
		int[] temp = checarBloque(bloque);
		int ficha = bloque.getFicha();
		int x = bloque.getXB();
		int y = bloque.getYB();
		int UL = temp[0];
		int UR = temp[1];
		int DL = temp[2];
		int DR = temp[3];
                int mov=0;
		if(ficha == 1){ //azules
			if(UL == 0 && UR == 0){
				if((x%2)==0){
					tablero[x-1][y].setColor(bloque);
					tablero[x+1][y].setColor(bloque);
                                        mov+=2;
				}
				else {
					tablero[x-1][y-1].setColor(bloque);
					tablero[x+1][y-1].setColor(bloque);
                                        mov+=2;
				}
			}
			else if(UL == 0 && UR != 0){
				if((x%2)==0){
					tablero[x-1][y].setColor(bloque);
                                        mov+=1;
				}
				else {
					tablero[x-1][y-1].setColor(bloque);
                                        mov+=1;
				}
			}
			else if(UL != 0 && UR ==0){
				if((x%2)==0){
					tablero[x+1][y].setColor(bloque);
                                         mov+=1;
				}
				else {
					tablero[x+1][y-1].setColor(bloque);
                                         mov+=1;
				}
			}
			
		}
		else if(ficha==-1){ //rojas
			if(DL == 0 && DR == 0){
				if((x%2)==0){
					tablero[x-1][y+1].setColor(bloque);
					tablero[x+1][y+1].setColor(bloque);
                                         mov+=2;
				}
				else if((x%2)==1){
					tablero[x-1][y].setColor(bloque);
					tablero[x+1][y].setColor(bloque);
                                         mov+=2;
				}
			}
			else if(DL == 0 && DR != 0){
				if((x%2)==0){
					tablero[x-1][y+1].setColor(bloque);
                                         mov+=1;
				}
				else {
					tablero[x-1][y].setColor(bloque);
                                         mov+=1;
				}
			}
			else if(DL != 0 && DR ==0){
				if((x%2)==0){
					tablero[x+1][y+1].setColor(bloque);
                                        mov+=1;
				}
				else {
					tablero[x+1][y].setColor(bloque);
                                        mov+=1;
				}
			}
			
		}
		else{
			if(UL==0){
				if(x%2==1){
					tablero[x-1][y-1].setColor(bloque);
                                        mov+=1;
				}
				else{
					tablero[x-1][y].setColor(bloque);
                                        mov+=1;
				}

			}
			
			if(UR==0){
				if(x%2==1){
					tablero[x+1][y-1].setColor(bloque);
                                        mov+=1;
				}
				else{
					tablero[x+1][y].setColor(bloque);
                                        mov+=1;
				}
			}

			
			if(DL==0){
				if(x%2==1){
					tablero[x-1][y].setColor(bloque);
                                        mov+=1;
				}
				else{
					tablero[x-1][y+1].setColor(bloque);
                                        mov+=1;
				}
			}
			
			if(DR==0){
				if(x%2==1){
					tablero[x+1][y].setColor(bloque);
                                        mov+=1;
				}
				else{
					tablero[x+1][y+1].setColor(bloque);
                                        mov+=1;
				}
			}
		}
                return mov;
	}
	public void coronarse(Bloque bloque){
		int x = bloque.getXB();
		int y = bloque.getYB();
		int ficha = bloque.getFicha();
		if(ficha == 1 && (x%2)==1 && y == 0){
			bloque.setFicha(2);
		}
		if(ficha == -1 && (x%2)==0 && y == 3){
			bloque.setFicha(-2);
		}
	}
	public boolean seguir(Bloque bloque){
		int[] temp = checarBloque(bloque);
		int ficha = bloque.getFicha();
		int x = bloque.getXB();
		int y = bloque.getYB();
		int UL = temp[0];
		int UR = temp[1];
		int DL = temp[2];
		int DR = temp[3];
		int seguir= 0;
                comer[0]=null;
                comer[1]=null;
                comer[2]=null;
                comer[3]=null;
                        
		if(ficha == 1){
			if(UL<0 && UL<3){
				if(y>0){
					if(x>1){    
						if( tablero[x-2][y-1].getFicha()==0){
							tablero[x-2][y-1].setColor(bloque,true);
							seguir=1;
                                                        comer[0]=tablero[x-2][y-1];
						}
					}
				}
			}
			if(UR<0 && UR<3){
				if(y>0){
					if(x<6){  
						if( tablero[x+2][y-1].getFicha()==0){
							tablero[x+2][y-1].setColor(bloque,true);
							seguir=1;
                                                        comer[1]=tablero[x+2][y-1];
						}
					}
				}
			}

		}
		else if(ficha==-1){
			if(DL>0 && DL<3){
				if(y<3){
					if(x>1){  
						if( tablero[x-2][y+1].getFicha()==0){
							tablero[x-2][y+1].setColor(bloque,true);
							seguir=1;
                                                        comer[2]=tablero[x-2][y+1];
						}
					}
				}
			}

			if(DR>0 && DR<3){
				if(y<3){
					if(x<6){  
						if( tablero[x+2][y+1].getFicha()==0){
							tablero[x+2][y+1].setColor(bloque,true);
							seguir=1;
                                                        comer[3]=tablero[x+2][y+1];
						}
					}
				}
			}
		}	
		else{
			if(UL>0 && turno==-1 && UL<3){
				if(y>0){
					if(x>1){    
						if( tablero[x-2][y-1].getFicha()==0){
							tablero[x-2][y-1].setColor(bloque,true);
							seguir=1;
                                                        comer[0]=tablero[x-2][y-1];
						}
					}
				}
			}
			else if(UL<0 && turno==1 && UL<3){
				if(y>0){
					if(x>1){  
						if( tablero[x-2][y-1].getFicha()==0){
							tablero[x-2][y-1].setColor(bloque,true);
                                                        seguir=1;
							comer[0]=tablero[x-2][y-1];
						}
					}
				}
			}


			if(UR>0 && turno==-1 && UR<3){
				if(y>0){
					if(x<6){  
						if( tablero[x+2][y-1].getFicha()==0){
							tablero[x+2][y-1].setColor(bloque,true);
							seguir=1;
                                                        comer[1]=tablero[x+2][y-1];
						}
					}
				}
			}
			else if(UR<0 && turno==1 && UR<3){
				if(y>0){
					if(x<6){  
						if( tablero[x+2][y-1].getFicha()==0){
							tablero[x+2][y-1].setColor(bloque,true);
							seguir=1;
                                                        comer[1]=tablero[x+2][y-1];
						}
					}
				}
			}

			if(DL>0 && turno==-1 && DL<3){
				if(y<3){
					if(x>1){  
						if( tablero[x-2][y+1].getFicha()==0){
							tablero[x-2][y+1].setColor(bloque,true);
							seguir=1;
                                                        comer[2]=tablero[x-2][y+1];
						}
					}
				}
			}
			else if(DL<0 && turno==1 && DL<3){
				if(y<3){
					if(x>1){  
						if( tablero[x-2][y+1].getFicha()==0){
							tablero[x-2][y+1].setColor(bloque,true);
							seguir=1;
                                                        comer[2]=tablero[x-2][y+1];
						}
					}
				}
			}

			if(DR>0 && turno==-1 && DR<3){
				if(y<3){
					if(x<6){  
						if( tablero[x+2][y+1].getFicha()==0){
							tablero[x+2][y+1].setColor(bloque,true);
							seguir=1;
                                                        comer[3]=tablero[x+2][y+1];
						}
					}
				}
			}
			else if(DR<0 && turno==1 && DR<3){
				if(y<3){
					if(x<6){  
						if( tablero[x+2][y+1].getFicha()==0){
							tablero[x+2][y+1].setColor(bloque,true);
							seguir=1;
                                                        comer[3]=tablero[x+2][y+1];
						}
					}
				}
			}

		}
		return seguir==1;
	}
        public int sinMovimientos(){
            int temp=0;
            for(int y=0;y<4;y++){
                    for(int x=0;x<8;x++){
                        if((tablero[x][y].getFicha()*turno)>0){
                            if(mover(tablero[x][y])>0){
                                tablero[x][y].unsetColor();
                                tablero[x][y].setComer();
                                temp++;
                            }
                        } 
                    }
            }
            return temp;
        }
        public boolean tieneQueComer(){
            int temp=0;
            for(int y=0;y<4;y++){
                    for(int x=0;x<8;x++){
                        if((tablero[x][y].getFicha()*turno)>0){
                            
                            if(seguir(tablero[x][y])==true){
                                for(int i=0;i<4;i++){
                                    if(comer[i]!=null){    
                                        comer[i].setColor(tablero[x][y], true);
                                        temp=1;
                                    }
                                }
                            }
                        } 
                    }
            }
            return temp==1;
        }
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 696, 696);
	}
}
