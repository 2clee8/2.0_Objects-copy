//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image astroPic;
    public Image asteroidPic;
    public Image backgroundPic;


    //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	private Astronaut astro;
//todo: make a new object called astro2
    public Astronaut astro2;
    public Asteroid asteroid1;
    public Asteroid asteroid2;

   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();

      // randomness
        //random range for x
        ///range 0-9
        int randx = (int)(Math.random() * 10);

        ///range 1-10
        randx = (int)(Math.random() * 10) + 1;

        ///range 1-1000
        randx = (int)(Math.random()*1000) + 1;

        //random range for y
        ///range 1-700
        int randy = (int)(Math.random()*700) + 1;


       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png"); //load the picture
        asteroidPic = Toolkit.getDefaultToolkit().getImage("asteroid.png"); //load the picture
        backgroundPic = Toolkit.getDefaultToolkit().getImage("stars.jpg"); //load the picture
		astro = new Astronaut(200,300);
        astro.dx = -2;
        astro.dy = 3;

        astro2 = new Astronaut(randx,randy);
        astro2.dx = 3;
        astro2.dy = -2;
        astro2.height = 100;
        astro2.width = 100;

        asteroid1 = new Asteroid(randx,400);
//        asteroid1.dx = 3;
//        asteroid1.dy = -2;
        asteroid1.height = 100;
        asteroid1.width = 100;

        asteroid2 = new Asteroid(467,400);
//        asteroid2.dx = 3;
//        asteroid2.dy = -2;
        asteroid2.height = 100;
        asteroid2.width = 100;

        asteroid1.dx = -asteroid1.dx;
	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		astro.move();
        astro2.move();
        asteroid1.move();
        asteroid2.move();
   Crashing();
	}

    public void Crashing() {
        //if astros crash into each other
        if (astro.hitBox.intersects(astro2.hitBox)) {
            System.out.println("Crash!");
            astro.dx = -astro.dx;
            astro2.dx = -astro2.dx;
            astro.dy = -astro.dy;
            astro2.dy = -astro2.dy;
        }

        if (asteroid1.hitbox.intersects(asteroid2.hitbox) && asteroid2.isCrashing == false) {
            ;
            System.out.println("Asteroid collision");
            asteroid2.height = asteroid2.height + 10;
            asteroid2.isCrashing = true;

        }
        // ! flips it (so it's saying is it false if they're intersecting instead of is it true
        if (!asteroid1.hitbox.intersects(asteroid2.hitbox)) {
            asteroid2.isCrashing = false;
        }
    }
   //Pauses or sleeps the computer for the amount specified in milliseconds
     public void pause(int time){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
        }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
        //start
        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

      //draw the image of the astronaut
		g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);

        if(astro2.isAlive == true){
            g.drawImage(astroPic, astro2.xpos, astro2.ypos, astro2.width, astro2.height, null);
        }
        g.drawImage(asteroidPic, asteroid1.xpos, asteroid1.ypos, asteroid1.width, asteroid1.height, null);
        g.drawImage(asteroidPic, asteroid2.xpos, asteroid2.ypos, asteroid2.width, asteroid2.height, null);
        g.drawRect(astro.hitBox.x,astro.hitBox.y,astro.hitBox.width,astro.hitBox.height);
        g.drawRect(astro2.hitBox.x,astro2.hitBox.y,astro2.hitBox.width,astro2.hitBox.height);


        g.dispose();

		bufferStrategy.show();
	}
}