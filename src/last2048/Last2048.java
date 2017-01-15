package last2048;

import javax.swing.*;


import java.awt.*;
import java.util.*;
import java.util.List;

public class Last2048 extends JPanel
	{
		/**
		 * fond initial case
		 */
		 private static final Color FIRST_BACKGROUND = new Color(0xc0b1a5); 
		 private static final String FONT = "Comic Sans Ms";
		 private static final int TUILE_TAILLE = 80;
		 private static final int TUILE_ESPACEMENT = 20;
		 
		 private Tuile[] mesTuiles;
		 private int monScore;
		 
		public static void main(String[] args)
		{
			generWindow();
		}
		/*
		 * cette fonction permet l'initialisation de la partie, elle sera appelée pour chaque nouvelle partie
		 * 
		 */
		public void iniPartie() {
		    
		   // myWin = false;
		    //myLose = false;
		    monScore = 0;
		    mesTuiles = new Tuile[4 * 4];
		    for (int i = 0; i < mesTuiles.length; i++) {
		      mesTuiles[i] = new Tuile();
		    }
		    //addTuile();
		    //addTuile();
		  }
		
		/*
		 * MakeVoidList permet de génrer une liste vide dans laquelle nous insererons les 
		 * des objet Tuiles vides afin de connaitre l'espace disponible sur notre Liste à
		 * l'aide de lectureListe() ;
		 * 
		 */
		private List<Tuile> makeVoidList(){
			final List<Tuile> listeTuiles = new ArrayList<Tuile>(16);
			return listeTuiles;
		}
		
		/*
		 * lectureListe permet de comparer notre liste a une liste vide pour renvoyer
		 * une liste des toutes les tuiles ayant des valeurs == 0 verfiées par la fonction 
		 * isZero de notre class Tuile 
		 * 
		 */
		private List<Tuile> lectureListe() {
			List<Tuile> listeVide = makeVoidList();
		    for (Tuile testVide : mesTuiles) {
		      if (testVide.isZero()) {
		        listeVide.add(testVide);
		      }
		    }
		    return listeVide;
		  }
		
		/**
		 * Cette fonction permet de dire si notre liste est pleine à l'aide de lectureListe
		 * 
		 */
		private boolean estPleine() {
			if (lectureListe().size() == 0){
		    return true;
			}
			else {
				return false;
			}
		  }
		
		boolean deplacePossible() {
			//On vérifie d'abords s'il y a de la place sinon on renvoie directement false pour le game over
		    if (!estPleine()) {
		      return true;
		    }
		    //si n'est pas pleine on parcours le tableau de bas en haut pour savoir si des déplacement sont possible
		    //dès qu'une valeur est vide la fonction renvoie true et permet la continuité de la partie
		    for (int x = 0; x < 4; x++) {
		      for (int y = 0; y < 4; y++) {
		        Tuile aTester = tuilePos(x, y);
		        if (
		        		(x < 3 && aTester.valeur == tuilePos(x + 1, y).valeur)
		          || 
		          		(y < 3 && aTester.valeur == tuilePos(x, y + 1).valeur)
		          ) {
		          return true;
		        }
		      }
		    }
		    return false;
		  }
		
		/*
		 * Nous aurons probablement a ecraser certains éléments de notre liste alors il faudra en ajouter des nouvelles.
		 * La fonction reajusteListe() instancie n x notre classe Tuile pour les ajouter a notre liste de Tuile
		 * un nombre n de Tuiles
		 * 
		 * si besoin de 4 nouvelles tuiles : nbNew = 4
		 * 
		 * @param int nbNew
		 * @param List listeActuelle
		 */
		private static void reajusteListe(java.util.List<Tuile> listeActuelle, int nbNew) {
		    while (listeActuelle.size() != nbNew) {
		      listeActuelle.add(new Tuile());
		    }
		  }
		
		/**
		 * la fonction insereInRdmTuile va jouter la valeur a notre tuile.
		 * Les règles du 2048 impose pour une nouvelle tuile les conditions suivantes :
		 * X 90% de chance d'obtenir un 2 et 10% de chance d'obtenir un 4.
		 * X la cellule est choisie au hasard dans notre liste de Tuiles Vides par le calcul 
		 * 
		 * ----> rand(0.0 ; 1.0) *  nb Tuiles Vides % nb Tuiles Vides
		 * si rand -> 0.5 et nb Tuiles vide = 4 alors 
		 * 0.5 * 4 % 4 =
		 * = 2 % 4 
		 * = 2
		 * 
		 *  ce sera alors notre deuxieme tuile qui prendra la valeur 2 ou 4 ;
		 * 
		 */
		private void insereInRdmTuile() {
		    List<Tuile> list = lectureListe();
		    //si lecture liste n'est pas vide = si liste de tuiles vides n'est pas vide ALORS : 
		    if (!lectureListe().isEmpty()) {
		      int tuileRandom = (int) (Math.random() * list.size()) % list.size();
		      Tuile tuileChoisie = list.get(tuileRandom);
		      
		      if (Math.random() < 0.9){
		    	  tuileChoisie.valeur = 2;
		      }
		      else {
		    	  tuileChoisie.valeur = 4;		    	  
		      }
		      
		    }
		  }
		
		
		public static void generWindow(){
			JFrame game = new JFrame();
		    game.setTitle("2048 Game");
		    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    game.setSize(340, 400);
		    game.setResizable(true);
		    game.add(new Last2048());
		    game.setLocationRelativeTo(null);
		    game.setVisible(true);			
		}
		
	public Last2048() {
			repaint();
		}	
	
	
	 private Tuile tuilePos(int x, int y) {
		    return mesTuiles[x + y * 4];
		  }
	
	/**
	 * cette fontion permet de récupérer les coordonées  grâce à la boucle de dessineGrille();
	 * @param arg
	 * @return int
	 */
	public static int positionnementTuile(int arg) {
	    return arg * (TUILE_ESPACEMENT + TUILE_TAILLE) + TUILE_ESPACEMENT;
	  }
	
	/**
	 * Cette fonction gère les coordonées pour l'affichage
	 * @param g
	 */
	public void dessineGrille(Graphics g){
		 for (int y = 1; y <= 4; y++) {
		      for (int x = 1; x <= 4; x++) {
		        dessineTuile(g, mesTuiles[x + y * 4], x, y);
		      }
		 }
	}
	
	
	
	
		/*
		 * cette fonction execute l'affichage via la super classe .paint de java :D
		 * @param g 
		 */
		@Override
		public void paint(Graphics g){
			 super.paint(g);
			    g.setColor(FIRST_BACKGROUND);
			    dessineGrille(g);
		}
	
		/*
		 * Cette fonction permet d'ajuster la taille de la police car celle-ci pouvaient dépasser la largeur de la
		 * case dans nos premiers test si on passait aux centaines ou milliers
		 * @param tuile
		 *
		 */
		public static int getTailleFont(Tuile tuile){
			int tailleFont;
			if ( 0 < tuile.valeur && tuile.valeur < 100) {
				tailleFont = 36;
				}
		    else if ( 100 < tuile.valeur && tuile.valeur < 1000) {
		    	tailleFont = 32;
		    	}
		    else {
		    	tailleFont= 24;
		    	}
			return tailleFont;
		}
	
	
	
		/**
		 * DessineTuile permet de dessiner les tuiles, 
		 * @param affichItem
		 * @param tuile
		 * @param x
		 * @param y
		 */
		private void dessineTuile(Graphics affichItem, Tuile tuile, int x, int y) {
		    Graphics2D item = ((Graphics2D) affichItem);
		    /**
		     * On utilisera Graphics 2D pour pour plus de facilité à dessiner notre grille.
		     * Le moteur du projet viendra inserer la bonne valeur à la bonne position.
		     * source : www.OpenClassroom.fr
		     * 
		     * Le rendu réalisé par le contexte graphique peut être influencé par des indications. 
		     * Ces indications sont des associations entre des clés et des valeurs. 
		     * Par exemple, la clé KEY_RENDERING peut être associée aux valeurs :
		     *  VALUE_RENDER_DEFAULT, VALUE_RENDER_QUALITY ou VALUE_RENDER_SPEED 
		     *  pour indiquer que le rendu doit favoriser la qualité ou la vitesse. 
		     *  Les noms des clés et des valeurs sont définis dans la classe RenderingHints. 
		     *  La méthode setRenderingHint de Graphics2D permet de changer la valeur associée à une clé.
		     *  Un exemple typique d'utilisation est le suivant.
		     *  
		     *  source :
		     *  https://www.irif.fr/~carton/Enseignement/InterfacesGraphiques/MasterInfo/Cours/Swing/graphiques.html
		     * 
		     */
		    item.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    item.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		    
		    /*
		     * 
		     * on récupère la valeur de la tuile pour pouvoir choisir la bonne couleur grâce à 
		     * getBgBackground de la classe tuile.
		     * 
		     * puis grâce a positionnementTuile on récupère la coordonnée ou doit être dessinnée la nouvelle tuile.
		     */
		    
		    int coordX = positionnementTuile(x);
		    int coordY = positionnementTuile(y);
		    int valeur = tuile.valeur;
		    item.setColor(tuile.getBgColor());
		    item.fillRoundRect(coordX, coordY, TUILE_TAILLE, TUILE_TAILLE, 18, 18);
		    item.setColor(FIRST_BACKGROUND);	     
		    
		    //gestion de la taille de la police pour éviter les dépassements grâce a gettailleFont					
		    final int tailleFont = getTailleFont(tuile);
		    final Font font = new Font(FONT, Font.BOLD, tailleFont);
		    item.setFont(font);
		    
		    //ici nous nous interressons à notre texte dans les cases, on utilise Fontmetrics.
		    // Fontmetrics permet notre texte, le remettre aux bonne dimension si la polica a changé.
		    //number est la string de la valeur de la tuile
		    String number = String.valueOf(valeur);
		    final FontMetrics fontM = getFontMetrics(font);
		    final int hauteur = -(int) fontM.getLineMetrics(number, item).getBaselineOffsets()[2];
		    final int longueur = fontM.stringWidth(number);
		    //si valeur != 0 alors on affiche le nombre aux bonnes coordonnées
		    if (valeur != 0){
		      item.drawString(number, coordX + (TUILE_TAILLE - longueur) / 2, coordY + TUILE_TAILLE - (TUILE_TAILLE - hauteur) / 2 - 2);
		      };

		    item.setFont(new Font(FONT, Font.PLAIN, 23));
		   

		  }
		
		
		
		/**
		 * @author Haïthem
		 *
		 *La classe Tuile gère la valeur et la couleur contenue : 
		 */
		static class Tuile {
				int valeur;
				private Color font = new Color(000000);;

				//initialise une tuile vide
			    public Tuile() {
			      valeur = 0;
			    }
			    /*
			     * modifie la vlaveur d'une tuile
			     */
			    public Tuile(int value) {
			      valeur = value;
			    }
			    /*
			     * On verfifie via cette fonction si la tuile est vierge :
			     * si tuile == 0 alors la fonction renvoie true, false sinon.
			     */
			    public boolean isZero() {
			      if (valeur == 0){
			    	  return true;
			      }
			      else {
			    	  return false;
			      }
			    }

			    public Color getBgColor() {			    	
			    	if (valeur == 2 ) return  new Color(0xe6ffcc); 
			    	else if (valeur == 4 )  return  new Color(0xd9ffb3);  
			    	else if (valeur == 8 ) { return  new Color(0x80ff00); } 
			    	else if (valeur == 16 ) { return  new Color(0x4d9900); } 
			    	else if (valeur == 32 ) { return  new Color(0xffff80); } 
			    	else if (valeur == 64 ) { return  new Color(0xffff1a); } 
			    	else if (valeur == 128 ) { return  new Color(0xb3b300); } 
			    	else if (valeur == 256 ) { return  new Color(0xffb366); } 
			    	else if (valeur == 512 ) { return  new Color(0xff8000); } 
			    	else if (valeur == 1024 ) { return  new Color(0xff6666); } 
			    	else if (valeur == 2048 ) { return  new Color(0xff0000); } 	
			    	else return new Color(0xff0000); 
			    	 }
			    
			    public void setValeur(int x){
			    	valeur = x;
			    }
			  }
			}
