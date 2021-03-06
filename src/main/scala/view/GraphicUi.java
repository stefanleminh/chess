package view;

import controller.Controller;
import model.GameField;
import observer.Observer;
import scala.Tuple2;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;


public class GraphicUi extends JFrame implements Observer {

    public void update(){
        ROUND.setText("ROUND " + controller.round());
        if(controller.currentPlayer().equals(controller.playerA())){
            playerA.setBorder(BorderFactory.createBevelBorder(1));
            playerA.setBackground(new Color(0, 255, 68));
            playerB.setBorder(null);
            playerB.setBackground(null);
        }
        else {
            playerB.setBorder(BorderFactory.createBevelBorder(1));
            playerB.setBackground(new Color(0, 255, 68));
            playerA.setBorder(null);
            playerA.setBackground(null);
        }
        updateFigures();
    }
    private JLayeredPane gamefield;
    private Controller controller;
    private JLayeredPane lp;
    private GameField field;
    private ChessListener chesslistener;
    private JPanel[][] referenceBackup = new JPanel[8][8];
    private JLabel ROUND, playerA, playerB;

    private int WHITE = 0;
    private int BLACK = 1;

    private  JTextField txtPlayerA, txtPlayerB;

    public GraphicUi(Controller controller, GameField field){
        this.controller = controller;
        this.controller.add(this);
        this.setSize(590, 620);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.field = field;
        lp = new JLayeredPane();
        Cover cover = new Cover(new ImageIcon(getClass().getResource("images/chess.jpg")).getImage());
        JLabel header= new JLabel("Chess 0.0.0");
        header.setFont(new Font("ComicSans", Font.PLAIN, 50));
        header.setBounds(200, 120 ,300,50);

        txtPlayerA = new JTextField("Player A...");
        txtPlayerB = new JTextField("Player B...");
        txtPlayerA.setBounds(250,170,150,30);
        txtPlayerB.setBounds(250,210,150,30);

        JButton btnStart = new JButton("Start");
        btnStart.setBounds(250,300,100,50);

        btnStart.addActionListener(new ButtonListener(controller, this));
        controller.setRemoteButton(btnStart);

        lp.add(cover, JLayeredPane.DEFAULT_LAYER);
        lp.add(header, new Integer(JLayeredPane.DEFAULT_LAYER+1));
        lp.add(txtPlayerA, new Integer(JLayeredPane.DEFAULT_LAYER.intValue()+1));
        lp.add(txtPlayerB, new Integer(JLayeredPane.DEFAULT_LAYER.intValue()+1));
        lp.add(btnStart, new Integer(JLayeredPane.DEFAULT_LAYER.intValue()+1));

        JMenuBar menu = new JMenuBar();
        menu.add(new JMenu("Menu"));

        this.add(lp);
        this.setJMenuBar(menu);
        this.setResizable(false);
        this.setVisible(true);
    }

    public JLayeredPane getLp(){return lp;}
    public JLayeredPane getGamefieldLp(){return gamefield;}

    public String[] getTxtFieldContent(){
        return new String[]{txtPlayerA.getText(), txtPlayerB.getText()};
    }


    public  void draw(Container c){
        gamefield = new JLayeredPane();

        ROUND = new JLabel("ROUND " + controller.round());
        ROUND.setBounds(20, 40, 150, 30);
        ROUND.setFont(new Font("ComicScans", Font.BOLD, 30));
        playerA = new JLabel("Player: " + controller.playerA(), CENTER);
        playerA.setBounds(200, 40, 200, 30);
        playerA.setBorder(BorderFactory.createBevelBorder(1));
        playerA.setBackground(new Color(0, 255, 68));
        playerB = new JLabel("Player: " + controller.playerB(), CENTER);
        playerB.setBounds(200, 530, 200, 30);


        gamefield.add(ROUND);
        gamefield.add(playerA);
        gamefield.add(playerB);
        chesslistener = new ChessListener(controller, this);
        gamefield.setLayout(null);
        gamefield.setPreferredSize(new Dimension(600,600));
        build();
        c.add(gamefield);
    }

    public  void build(){
        int ct = 0;
        int size = 50;
        int pos = 100;
        for(int i = 0; i < field.field().length; ++i){
            for(int j = 0; j < field.field().length; ++j){

                Tile beige = new Tile(255, 235, 205);
                beige.setBounds(pos+ i*size,pos+j*size,size,size);
                beige.addMouseListener(chesslistener);

                Tile brown = new Tile(139, 69, 19);
                brown.setBounds(pos+i*size,pos+j*size,size,size);
                brown.addMouseListener(chesslistener);


                if(ct % 2 == 0) {
                    gamefield.add(beige, JLayeredPane.DEFAULT_LAYER);
                }
                else{
                    gamefield.add(brown, JLayeredPane.DEFAULT_LAYER);
                }
                String figure = field.field()[j][i].getClass().toString();
                if(figure.contains("Bauer")) {
                    BauerTile bauer;
                    if(j < 2)
                        bauer = new BauerTile(WHITE);
                    else
                        bauer = new BauerTile(BLACK);
                    bauer.setBounds(pos+i*size,pos+j*size,size,size);
                    bauer.addMouseListener(chesslistener);
                    gamefield.add(bauer, JLayeredPane.DEFAULT_LAYER.intValue());

                    referenceBackup[j][i] = bauer;

                }
                else if(figure.contains("Turm")) {
                    TurmTile turm;
                    if(j < 2)
                         turm = new TurmTile(WHITE);
                    else
                         turm = new TurmTile(BLACK);
                    turm.setBounds(pos+i*size,pos+j*size,size,size);
                    turm.addMouseListener(chesslistener);
                    gamefield.add(turm, JLayeredPane.DEFAULT_LAYER.intValue());

                    referenceBackup[j][i] = turm;


                }
                else if(figure.contains("Läufer")) {
                    LäuferTile läufer;
                    if(j < 2)
                        läufer = new LäuferTile(WHITE);
                    else
                        läufer = new LäuferTile(BLACK);
                    läufer.setBounds(pos+i*size,pos+j*size,size,size);
                    läufer.addMouseListener(chesslistener);
                    gamefield.add(läufer, JLayeredPane.DEFAULT_LAYER.intValue());

                    referenceBackup[j][i] = läufer;
                }
                else if(figure.contains("Offizier")) {
                    OffizierTile offizier;
                    if(j < 2)
                        offizier= new OffizierTile(WHITE);
                    else
                        offizier= new OffizierTile(BLACK);
                    offizier.setBounds(pos+i*size,pos+j*size,size,size);
                    offizier.addMouseListener(chesslistener);
                    gamefield.add(offizier, JLayeredPane.DEFAULT_LAYER.intValue());

                    referenceBackup[j][i] = offizier;
                }
                else if(figure.contains("König")) {
                    KönigTile könig;
                    if(j < 2)
                        könig = new KönigTile(WHITE);
                    else
                        könig = new KönigTile(BLACK);
                    könig.setBounds(pos+i*size,pos+j*size,size,size);
                    könig.addMouseListener(chesslistener);
                    gamefield.add(könig, JLayeredPane.DEFAULT_LAYER.intValue());

                    referenceBackup[j][i] = könig;
                }
                else if(figure.contains("Dame")) {
                    DameTile dame;
                    if(j < 2)
                        dame = new DameTile(WHITE);
                    else
                        dame = new DameTile(BLACK);
                    dame.setBounds(pos+i*size,pos+j*size,size,size);
                    dame.addMouseListener(chesslistener);
                    gamefield.add(dame, JLayeredPane.DEFAULT_LAYER.intValue());

                    referenceBackup[j][i] = dame;
                }


                if(j == 7)
                    ct += 2;
                else ++ct;

            }
        }
    }

    private void updateFigures(){

        int size = 50;
        Tuple2<Integer, Integer> source = new Tuple2<>((int)controller.source()._1(), (int)controller.source()._2());
        Tuple2<Integer, Integer> target = new Tuple2<>((int)controller.target()._1(), (int)controller.target()._2());

        /*
        for(int i = 0; i < 8; ++i){
            for(int j = 0; j < 8; ++j){

                System.out.println("" + i + ", " + j + ": " + referenceBackup[i][j]);
            }
        }
        */


        referenceBackup[source._1][source._2].setBounds(100+target._2*size, 100+target._1*size, size, size);

        //update reference backup table
        if(referenceBackup[target._1][target._2] != null){
            gamefield.remove(referenceBackup[target._1][target._2]); //remove figure jpanel
        }
        referenceBackup[target._1][target._2] =  referenceBackup[source._1][source._2];
        referenceBackup[source._1][source._2] = null;

    }

    public JPanel[][] getReferenceBackup() {
        return referenceBackup;
    }
}
