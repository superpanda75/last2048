package last2048;

import javax.swing.*;



import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

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
		 private boolean Victoire;
		 private boolean Defaite;
		 
		public static void main(String[] args)
		{
			JFrame game = new JFrame();
		    game.setTitle("Dernier 2048 vant la fin du Monde");
		    game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    game.setSize(408, 500);
		    game.setResizable(true);

		    game.add(new Last2048());

		    game.setLocationRelativeTo(null);
		    game.setVisible(true);
		}
		
		/*
		 * cette fonction permet l'initialisation de la partie, elle sera appelée pour chaque nouvelle partie
		 * 
		 */
		public void iniPartie() {
		    
		    Victoire = false;
		    Defaite = false;
		    monScore = 0;
		    mesTuiles = new Tuile[4 * 4];
		    for (int i = 0; i < mesTuiles.length; i++) {
		      mesTuiles[i] = new Tuile();
		    }
		    insereInRdmTuile();
		    insereInRdmTuile();
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
		
		/**
		 * cette fonction determine si un mouvement est possible en parcourant les position x et y via for ( for () )
		 * 
		 * @return boolean
		 */
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
		private static void reajusteListe(List<Tuile> listeActuelle, int nbNew) {
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
		
		/**
		 * la fonction deplace ligne prend les Tuiles en parametre et verifie si celle-ci sont nulles
		 * avec la methode isZero() de la classe Tuile. 
		 * si elle ne sont pas vides, les tuile sont conservées dans une nouvelle liste qui est ensuite retournées.
		 * 
		 * @param exLigne
		 * @return
		 */
		  private Tuile[] deplaceLigne(Tuile[] exLigne) {
			    LinkedList<Tuile> movedLine = new LinkedList<Tuile>();
			    for (int indexLigne = 0; indexLigne < 4; indexLigne++) {
			      if (!exLigne[indexLigne].isZero()){
			    	  
			      
			        movedLine.addLast(exLigne[indexLigne]);
			      //cela est nécessaire pour ne pas perdre les tuiles existantes
			      }
			    }
			    
			    // Si la liste des Tuiles a deplacer == 0 alors on renvoie la ligne telle qu'elle.
			    if (movedLine.size() == 0) {
			      return exLigne;
			    // Sinon on créer la nouvelle Ligne en pensant a jouter des tuiles
			    } else {
			      Tuile[] nouvLigne = new Tuile[4];
			      reajusteListe(movedLine, 4);
			      for (int i = 0; i < 4; i++) {
			        nouvLigne[i] = movedLine.removeFirst();
			      }
			      return nouvLigne;
			    }
			  }
		  
		  /**
		   * Nous aurons besoin de comparer des tuiles pour savoir s'il faut réajuster la liste ou s'il faut inserer des valeurs.
		   * 
		   * @param liste_1
		   * @param liste_2
		   * @return boolean
		   */
		  private boolean compareTuiles(Tuile[] liste_1, Tuile[] liste_2) {
			    if (liste_1 == liste_2)
			    {
			      return true;
			    } 
			    else if (liste_1.length != liste_2.length) 
			    {
			      return false;
			    }

			    for (int index = 0; index < liste_1.length; index++) 
			    {
			      if (liste_1[index].valeur != liste_2[index].valeur) 
			      {
			        return false;
			      }
			    }
			    return true;
			  }
		  
		  
		  
		  
		  /**
		   * getLigne permet d'accéder à une ligne de notre liste de tuile
		   * @param index
		   * @return
		   */
		  private Tuile[] getLigne(int index) {
			    Tuile[] tuile = new Tuile[4];
			    for (int i = 0; i < 4; i++) {
			      tuile[i] = tuilePos(i, index);
			    }
			    return tuile;
			  }

		  /**
		   * Cette fonction permet d'écrire dans notre liste principale mesTuiles
		   * et donc peut inserer toute une ligne dans la liste grace à l'indix pour obtenir "l'id" de la tuile danx la liste de 1 a 16
		   * et notre valeur ne dépassera pas les milliers donc on fixe a 4 la taille de l'entier ! 
		   * 
		   * AH MOINS D ETRE TRES^10 BON xD !!
		   * 
		   * @param index
		   * @param MaListe
		   */
			  private void setLigne(int index, Tuile[] listeSource) {
			    System.arraycopy(listeSource, 0, mesTuiles, index * 4, 4);
			  }
			  
			  
			  /**
			   * multiplicateur est l'une des fonction principales du 2048
			   * elle gere la multiplication par 2 si les valeur de deux tuiles correspondent en creant une nouvelle 
			   * liste à partir de l'ancienne
			   * e
			   * 
			   * @param ligneActuelle
			   * @return
			   */
			  private Tuile[] multiplicateur(Tuile[] ligneActuelle) {
				    LinkedList<Tuile> nouvListe = new LinkedList<Tuile>();
				    // on parcours et on vérifie si 0 ou pas, si une tuile est égale a 0 on s'arrête.
				    
				    for (int index = 0; index < 4 && !ligneActuelle[index].isZero(); index++) 
				    {
				    	//on récupère la valeur
				      int valActuelle = ligneActuelle[index].valeur;
				      if (index < 3 && ligneActuelle[index].valeur == ligneActuelle[index + 1].valeur) {
				    	  //si les valeurs sont égale on fusionne les tuiles et on remplace l'ancienne valeur en commun par une seule valeur
				    	  //etant le produit de l'ancienne valeur * 2
				    	  //puis on update le score 
				        valActuelle = valActuelle * 2;
				        monScore = monScore + valActuelle;
				        int pourGagner = 2048;
				        if (valActuelle == pourGagner) {
				          Victoire = true;
				        }
				        index++;
				      }
				      nouvListe.add(new Tuile(valActuelle));
				    }
				    //si la nouvelle liste n'a pas eu d'insertion, on renvoie la liste telle qu'elle
				    if (nouvListe.size() == 0) {
				      return ligneActuelle;
				    } else {
				      reajusteListe(nouvListe, 4);
				      return nouvListe.toArray(new Tuile[4]);
				    }
				  }
			  
			  
			  /**
			   * La fonction orientationTuile permet d'obtenir des tuiles avec les nouvelles coordonnées en fonction de 
			   * l'angle d'orientation qu'on obtiendra via les touches.
			   * 
			   * @param angle
			   * @return Tuile[] nouvTuiles
			   */
			  private Tuile[] orientationTuile(int angle) {
				    Tuile[] nouvTuiles = new Tuile[4 * 4];
				    int posX = 3;
				    int posY = 3;
				    
				    //on verifie dans quelle direction nous devons aller en fonction de l'angle 
				    if (angle == 90) 
				    {
				      posY = 0;
				    } 
				    else if (angle == 270) 
				    {
				      posX = 0;
				    }

				    //NECESSAIRE DE PASSER EN RADIAN / COSINUS / SINUS SINON RESULTATS ERRONES
				    double radian = Math.toRadians(angle);
				    int cosinus = (int) Math.cos(radian);
				    int sinus = (int) Math.sin(radian);
				    
				    //puis on parcours le tableau via les positions des tuiles :
				    for (int x = 0; x < 4; x++) 
				    {
				      for (int y = 0; y < 4; y++) 
				      {
				        int newPosX = (x * cosinus) - (y * sinus) + posX;
				        int newPosY = (x * sinus) + (y * cosinus) + posY;
				        nouvTuiles[(newPosX) + (newPosY) * 4] = tuilePos(x, y);
				      }
				    }
				    return nouvTuiles;
				  }
		
			  
			  
			  /**
			   * permet de gerer les déplacement vers la gauche 
			   * et de savoir si l'on doit rajouter des tuiles.
			   * cette fonction sera appelée entre chaque rotation en fonction de chaque MOUVEMEEEEEEEEEEEEENNNNNNNTTTTTTTTTTT !!
			   */
			  public void deplacementGauche() {
				    boolean besoinNouvTuile = false;
				    for (int index = 0; index < 4; index++) 
				    {
				      Tuile[] ligne = getLigne(index);
				      Tuile[] insere = multiplicateur(deplaceLigne(ligne));				      
				      setLigne(index, insere);
				      //si besoin de tuile alors besoinNouvTuile true alors on inserera une tuile gràce a insereRdmTuile();
				      if (!besoinNouvTuile && !compareTuiles(ligne, insere))
				      {
				        besoinNouvTuile = true;
				      }
				    }

				    if (besoinNouvTuile) 
				    {
				      insereInRdmTuile();
				    }
				  }

				  /**
				   * gere le déplacement a droite 
				   **/
				  public void deplacementDroite() 
				  {
				    mesTuiles = orientationTuile(180);
				    deplacementGauche();
				    mesTuiles = orientationTuile(180);
				  }

				  /**
				   * gere le deplacement vers le haut 
				   */
				  public void deplacementHaut() 
				  {
				    mesTuiles = orientationTuile(270);
				    deplacementGauche();
				    mesTuiles = orientationTuile(90);
				  }

				  /**
				   * gere le deplacement vers le bas
				   */
				  public void deplacementBas() 
				  {
				    mesTuiles = orientationTuile(90);
				    deplacementGauche();
				    mesTuiles = orientationTuile(270);
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
			 setFocusable(true);
			    addKeyListener(new KeyAdapter() {
			      public void touche(KeyEvent touche){
						
						 if (touche.getKeyCode() == KeyEvent.VK_ENTER) {
					          iniPartie();
					        }
					        if (!deplacePossible()) {
					          Defaite = true;
					        }

					        if (!Victoire && !Defaite) 
					        {		          
					           if (
					        		   (touche.getKeyCode() == KeyEvent.VK_LEFT)  
					        		   || 
					        		   (touche.getKeyCode() == KeyEvent.VK_Q)
					        	)		        	   
					           {
					        	   deplacementGauche();
					           }
					           
					           else if (
					        		   (touche.getKeyCode() == KeyEvent.VK_RIGHT)  
					        		   || 
					        		   (touche.getKeyCode() == KeyEvent.VK_D)
					        		   )
					           {
					        	   deplacementDroite();
					           }
					        	   
					            else if (
					            		(touche.getKeyCode() == KeyEvent.VK_UP) 
					            		|| 
					        		   (touche.getKeyCode() == KeyEvent.VK_Z)
					        		   )
					           {
					        	   deplacementHaut();
					           }
					           
					            else if (
					            		(touche.getKeyCode() == KeyEvent.VK_DOWN) 
					            		|| 
					        		   (touche.getKeyCode() == KeyEvent.VK_S)
					        		   )
					           {
					        	   deplacementBas();
					           }
					          }
					        

					        if (!Victoire && !deplacePossible()) {
					          Defaite = true;
					        }

					        repaint();
					      }
			    });
			    iniPartie();
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
			    g.fillRect(0, 0, this.getSize().width, this.getSize().height);
			    for (int y = 0; y < 4; y++) {
			        for (int x = 0; x < 4; x++) {
			          dessineTuile(g, mesTuiles[x + y * 4], x, y);
			        }
			      }
			    
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
			    public Tuile(int nombre) {
			      valeur = nombre;
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
